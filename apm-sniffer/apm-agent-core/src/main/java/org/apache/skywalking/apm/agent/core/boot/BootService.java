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

package org.apache.skywalking.apm.agent.core.boot;

/**
 * The <code>BootService</code> is an interface to all remote, which need to boot when plugin mechanism begins to work.
 * {@link #boot()} will be called when <code>BootService</code> start up.
 * 启动服务，当插件机制开始工作时，需要启动。
 * {@link #boot()} 将在 BootService 启动时调用。
 */
public interface BootService {
    /**
     * 准备阶段
     * @throws Throwable 异常
     */
    void prepare() throws Throwable;

    /**
     * 启动阶段
     * @throws Throwable 异常
     */
    void boot() throws Throwable;

    /**
     * 完成阶段
     * @throws Throwable 异常
     */
    void onComplete() throws Throwable;

    /**
     * 关闭阶段
     * @throws Throwable 异常
     */
    void shutdown() throws Throwable;

    /**
     * {@code BootService}s with higher priorities will be started earlier, and shut down later than those {@code BootService}s with lower priorities.
     * 与优先级较低的 BootServices 相比，具有较高优先级的 BootServices 将更早启动，更晚关闭。
     *
     * @return the priority of this {@code BootService}.
     */
    default int priority() {
        return 0;
    }
}
