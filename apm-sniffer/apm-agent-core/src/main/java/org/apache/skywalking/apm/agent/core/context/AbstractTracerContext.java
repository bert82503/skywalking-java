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

import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;

/**
 * The <code>AbstractTracerContext</code> represents the tracer context manager.
 * 追踪上下文管理者，跨越进程边界，传递到下级span的状态。
 */
public interface AbstractTracerContext {
    // 跨进程传播
    // Carrier：传递跨进程数据的搬运工，负责将追踪状态从一个进程"carries"（携带，传递）到另一个进程
    /**
     * Prepare for the cross-process propagation. How to initialize the carrier, depends on the implementation.
     * 跨进程传播
     * 注入操作，向Carrier增加跨进程通信数据。
     *
     * @param carrier to carry the context for crossing process.
     */
    void inject(ContextCarrier carrier);

    /**
     * Build the reference between this segment and a cross-process segment. How to build, depends on the
     * implementation.
     * 提取操作，从Carrier中获取跨进程通信数据。
     *
     * @param carrier carried the context from a cross-process segment.
     */
    void extract(ContextCarrier carrier);

    // 跨线程传播
    /**
     * Capture a snapshot for cross-thread propagation. It's a similar concept with ActiveSpan.Continuation in
     * OpenTracing-java How to build, depends on the implementation.
     * 捕获用于跨线程传播的快照。
     *
     * @return the {@link ContextSnapshot} , which includes the reference context.
     */
    ContextSnapshot capture();

    /**
     * Build the reference between this segment and a cross-thread segment. How to build, depends on the
     * implementation.
     * 在这个追踪片段和跨线程段之间构建引用。
     *
     * @param snapshot from {@link #capture()} in the parent thread.
     */
    void continued(ContextSnapshot snapshot);

    // 三层身份标识
    /**
     * Get the global trace id, if needEnhance. How to build, depends on the implementation.
     * 全局的追踪身份标识
     *
     * @return the string represents the id.
     */
    String getReadablePrimaryTraceId();

    /**
     * Get the current segment id, if needEnhance. How to build, depends on the implementation.
     * 当前追踪片段身份标识
     *
     * @return the string represents the id.
     */
    String getSegmentId();

    /**
     * Get the active span id, if needEnhance. How to build, depends on the implementation.
     * 活跃的跨度身份标识
     *
     * @return the string represents the id.
     */
    int getSpanId();

    // 跨度
    /**
     * Create an entry span
     * 入口跨度
     *
     * @param operationName most likely a service name
     * @return the span represents an entry point of this segment.
     */
    AbstractSpan createEntrySpan(String operationName);

    /**
     * Create a local span
     * 本地跨度
     *
     * @param operationName most likely a local method signature, or business name.
     * @return the span represents a local logic block.
     */
    AbstractSpan createLocalSpan(String operationName);

    /**
     * Create an exit span
     * 出口跨度
     *
     * @param operationName most likely a service name of remote
     * @param remotePeer    the network id(ip:port, hostname:port or ip1:port1,ip2,port, etc.). Remote peer could be set
     *                      later, but must be before injecting.
     * @return the span represent an exit point of this segment.
     */
    AbstractSpan createExitSpan(String operationName, String remotePeer);

    /**
     * 活跃跨度
     * @return the active span of current tracing context(stack)
     */
    AbstractSpan activeSpan();

    /**
     * Finish the given span, and the given span should be the active span of current tracing context(stack)
     *
     * @param span to finish
     * @return true when context should be clear.
     */
    boolean stopSpan(AbstractSpan span);

    /**
     * Notify this context, current span is going to be finished async in another thread.
     * 通知这个上下文，当前跨度将在另一个线程中异步完成。
     *
     * @return The current context
     */
    AbstractTracerContext awaitFinishAsync();

    /**
     * The given span could be stopped officially.
     *
     * @param span to be stopped.
     */
    void asyncStop(AsyncSpan span);

    /**
     * Get current correlation context
     * 获取当前的关联上下文
     */
    CorrelationContext getCorrelationContext();

    /**
     * Get current primary endpoint name
     * 获取当前的主终端名称
     */
    String getPrimaryEndpointName();
}
