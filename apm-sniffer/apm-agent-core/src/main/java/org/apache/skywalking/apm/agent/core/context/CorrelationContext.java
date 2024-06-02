/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.skywalking.apm.agent.core.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.skywalking.apm.agent.core.base64.Base64;
import org.apache.skywalking.apm.agent.core.conf.Config;
import org.apache.skywalking.apm.agent.core.context.tag.StringTag;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.util.StringUtil;

/**
 * Correlation context, use to propagation user custom data.
 * 关联上下文，用于传播/传递用户自定义数据。
 */
public class CorrelationContext {

    /**
     * 用户自定义数据
     */
    private final Map<String, String> data;

    /**
     * 自动标签的键列表
     * 希望将自定义的参数自动记录到tag中
     */
    private static final List<String> AUTO_TAG_KEYS;

    static {
        if (StringUtil.isNotEmpty(Config.Correlation.AUTO_TAG_KEYS)) {
            AUTO_TAG_KEYS = Arrays.asList(Config.Correlation.AUTO_TAG_KEYS.split(","));
        } else {
            AUTO_TAG_KEYS = new ArrayList<>();
        }
    }

    public CorrelationContext() {
        this.data = new ConcurrentHashMap<>(Config.Correlation.ELEMENT_MAX_NUMBER);
    }

    /**
     * Add or override the context.
     * 设置或覆盖用户自定义数据
     *
     * @param key   to add or locate the existing context
     * @param value as new value
     * @return old one if exist.
     */
    public Optional<String> put(String key, String value) {
        // key must not null
        // 键，不能为null
        if (key == null) {
            return Optional.empty();
        }

        // remove and return previous value when value is empty
        // 如果值为空，则移除键并返回先前的值
        if (StringUtil.isEmpty(value)) {
            return Optional.ofNullable(data.remove(key));
        }

        // check value length
        // 校验值的长度
        if (value.length() > Config.Correlation.VALUE_MAX_LENGTH) {
            return Optional.empty();
        }

        // already contain key
        // 已经包含设置的键，则替换值
        if (data.containsKey(key)) {
            final String previousValue = data.put(key, value);
            return Optional.of(previousValue);
        }

        // check keys count
        // 检查键的数量
        if (data.size() >= Config.Correlation.ELEMENT_MAX_NUMBER) {
            return Optional.empty();
        }
        // 自动标签的键列表
        if (AUTO_TAG_KEYS.contains(key) && ContextManager.isActive()) {
            // 为当前活动的跨度，打标签(跨度标签)
            ContextManager.activeSpan().tag(new StringTag(key), value);
        }
        // setting
        // 设置用户自定义数据到关联上下文中
        data.put(key, value);
        return Optional.empty();
    }

    /**
     * 获取用户自定义数据
     * @param key to find the context
     * @return value if exist.
     */
    public Optional<String> get(String key) {
        if (key == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(data.get(key));
    }

    /**
     * Serialize this {@link CorrelationContext} to a {@link String}
     * 序列化
     *
     * @return the serialization string.
     */
    String serialize() {
        if (data.isEmpty()) {
            return "";
        }

        return data.entrySet().stream()
                   .map(entry -> Base64.encode(entry.getKey()) + ":" + Base64.encode(entry.getValue()))
                   .collect(Collectors.joining(","));
    }

    /**
     * Deserialize data from {@link String}
     * 反序列化
     */
    void deserialize(String value) {
        if (StringUtil.isEmpty(value)) {
            return;
        }

        for (String perData : value.split(",")) {
            // Only data with limited count of elements can be added
            if (data.size() >= Config.Correlation.ELEMENT_MAX_NUMBER) {
                break;
            }
            final String[] parts = perData.split(":");
            if (parts.length != 2) {
                continue;
            }
            data.put(Base64.decode2UTFString(parts[0]), Base64.decode2UTFString(parts[1]));
        }
    }

    /**
     * Prepare for the cross-process propagation. Inject the {@link #data} into {@link
     * ContextCarrier#getCorrelationContext()}
     * 跨进程传播
     */
    void inject(ContextCarrier carrier) {
        // 注入操作，向Carrier增加跨进程通信数据。
        carrier.getCorrelationContext().data.putAll(this.data);
    }

    /**
     * Extra the {@link ContextCarrier#getCorrelationContext()} into this context.
     */
    void extract(ContextCarrier carrier) {
        // 提取操作，从Carrier中获取跨进程通信数据。
        final Map<String, String> carrierCorrelationContext = carrier.getCorrelationContext().data;
        for (Map.Entry<String, String> entry : carrierCorrelationContext.entrySet()) {
            // Only data with limited count of elements can be added
            if (data.size() >= Config.Correlation.ELEMENT_MAX_NUMBER) {
                break;
            }

            this.data.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Process the active span
     * 处理活动跨度
     *
     * 1. Inject the tags with auto-tag flag into the span
     * 将带有自动标记标签的标记注入到跨度中
     */
    void handle(AbstractSpan span) {
        AUTO_TAG_KEYS.forEach(key -> this.get(key).ifPresent(val -> span.tag(new StringTag(key), val)));
    }

    /**
     * Clone the context data, work for capture to cross-thread.
     * 克隆上下文数据，进行跨线程捕获。
     */
    @Override
    public CorrelationContext clone() {
        final CorrelationContext context = new CorrelationContext();
        context.data.putAll(this.data);
        return context;
    }

    /**
     * Continue the correlation context in another thread.
     * 在另一个线程中继续关联上下文。
     *
     * @param snapshot holds the context.
     */
    void continued(ContextSnapshot snapshot) {
        this.data.putAll(snapshot.getCorrelationContext().data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CorrelationContext that = (CorrelationContext) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
