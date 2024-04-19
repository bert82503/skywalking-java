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

package org.apache.skywalking.apm.agent.core.context;

import lombok.Getter;
import org.apache.skywalking.apm.agent.core.context.ids.DistributedTraceId;
import org.apache.skywalking.apm.agent.core.profile.ProfileStatusContext;

/**
 * The <code>ContextSnapshot</code> is a snapshot for current context. The snapshot carries the info for building
 * reference between two segments in two thread, but have a causal relationship.
 * 捕获用于跨线程传播的快照，在这个追踪片段和跨线程段之间构建引用。
 */
@Getter
public class ContextSnapshot {
    /**
     * 追踪ID
     */
    private DistributedTraceId traceId;
    /**
     * 父追踪片段ID
     */
    private String traceSegmentId;
    /**
     * 父跨度ID
     * 父片段中的跨度ID
     */
    private int spanId;
    /**
     * 父服务的端点
     */
    private String parentEndpoint;

    /**
     * 关联上下文
     */
    private CorrelationContext correlationContext;
    /**
     * 扩展上下文
     */
    private ExtensionContext extensionContext;
    private ProfileStatusContext profileStatusContext;

    ContextSnapshot(String traceSegmentId,
                    int spanId,
                    DistributedTraceId primaryTraceId,
                    String parentEndpoint,
                    CorrelationContext correlationContext,
                    ExtensionContext extensionContext,
                    ProfileStatusContext profileStatusContext) {
        this.traceSegmentId = traceSegmentId;
        this.spanId = spanId;
        this.traceId = primaryTraceId;
        this.parentEndpoint = parentEndpoint;
        this.correlationContext = correlationContext.clone();
        this.extensionContext = extensionContext.clone();
        this.profileStatusContext = profileStatusContext.clone();
    }

    public boolean isFromCurrent() {
        return traceSegmentId != null && traceSegmentId.equals(ContextManager.capture().getTraceSegmentId());
    }

    /**
     * 获取关联上下文
     */
    public CorrelationContext getCorrelationContext() {
        return correlationContext;
    }

    public boolean isValid() {
        return traceSegmentId != null && spanId > -1 && traceId != null;
    }
}
