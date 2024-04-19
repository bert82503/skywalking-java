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
 *
 */

package org.apache.skywalking.apm.agent.core.context.trace;

import org.apache.skywalking.apm.agent.core.conf.Constants;
import org.apache.skywalking.apm.agent.core.context.TracingContext;
import org.apache.skywalking.apm.network.trace.component.Component;

/**
 * The <code>EntrySpan</code> represents a service provider point, such as Tomcat server entrance.
 * 入口跨度，表示服务提供者点，例如Tomcat服务器入口。
 * <p>
 * It is a start point of {@link TraceSegment}, even in a complex application, there maybe have multi-layer entry point,
 * the <code>EntrySpan</code> only represents the first one.
 * 它是追踪片段的起点，即使在复杂的应用程序中，也可能有多层入口点，EntrySpan只代表第一个入口点。
 * <p>
 * But with the last <code>EntrySpan</code>'s tags and logs, which have more details about a service provider.
 * <p>
 * Such as: Tomcat Embed - Dubbox The <code>EntrySpan</code> represents the Dubbox span.
 */
public class EntrySpan extends StackBasedTracingSpan {

    /**
     * 当前最大深度
     */
    private int currentMaxDepth;

    public EntrySpan(int spanId, int parentSpanId, String operationName, TracingContext owner) {
        super(spanId, parentSpanId, operationName, owner);
        this.currentMaxDepth = 0;
    }

    /**
     * Set the {@link #startTime}, when the first start, which means the first service provided.
     */
    @Override
    public EntrySpan start() {
        if ((currentMaxDepth = ++stackDepth) == 1) {
            // 第一个入口点
            super.start();
        }
        // 重新开始时，清理
        clearWhenRestart();
        return this;
    }

    @Override
    public EntrySpan tag(String key, String value) {
        // 当前跨度
        if (stackDepth == currentMaxDepth || isInAsyncMode) {
            super.tag(key, value);
        }
        return this;
    }

    @Override
    public AbstractTracingSpan setLayer(SpanLayer layer) {
        // 当前跨度
        if (stackDepth == currentMaxDepth || isInAsyncMode) {
            return super.setLayer(layer);
        } else {
            return this;
        }
    }

    @Override
    public AbstractTracingSpan setComponent(Component component) {
        // 当前跨度
        if (stackDepth == currentMaxDepth || isInAsyncMode) {
            return super.setComponent(component);
        } else {
            return this;
        }
    }

    @Override
    public AbstractTracingSpan setOperationName(String operationName) {
        // 当前跨度
        if (stackDepth == currentMaxDepth || isInAsyncMode) {
            return super.setOperationName(operationName);
        } else {
            return this;
        }
    }

    @Override
    public EntrySpan log(Throwable t) {
        super.log(t);
        return this;
    }

    @Override
    public boolean isEntry() {
        // 是入口跨度
        return true;
    }

    @Override
    public boolean isExit() {
        // 不是出口跨度
        return false;
    }

    private void clearWhenRestart() {
        this.componentId = Constants.NULL_VALUE;
        this.layer = null;
        this.logs = null;
        this.tags = null;
    }
}
