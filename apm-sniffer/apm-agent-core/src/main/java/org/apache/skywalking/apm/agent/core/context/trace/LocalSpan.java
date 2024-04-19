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

import org.apache.skywalking.apm.agent.core.context.TracingContext;

/**
 * The <code>LocalSpan</code> represents a normal tracing point, such as a local method.
 * 本地跨度，表示普通追踪点，例如本地方法。
 */
public class LocalSpan extends AbstractTracingSpan {

    public LocalSpan(int spanId, int parentSpanId, String operationName, TracingContext owner) {
        super(spanId, parentSpanId, operationName, owner);
    }

    @Override
    public boolean isEntry() {
        // 不是入口跨度
        return false;
    }

    @Override
    public boolean isExit() {
        // 不是出口跨度
        return false;
    }

    @Override
    public AbstractSpan setPeer(String remotePeer) {
        // 本请求的目标地址是当前进程
        return this;
    }
}
