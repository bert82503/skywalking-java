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

package org.apache.skywalking.apm.agent.core.plugin;

import org.apache.skywalking.apm.agent.core.plugin.loader.AgentClassLoader;
import org.apache.skywalking.apm.agent.core.plugin.loader.InstrumentationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * The plugin can be inserted into the kernel by implementing this spi return PluginDefine list.
 * 动态的插件加载者
 */

public enum DynamicPluginLoader {

    /**
     * 单例
     */
    INSTANCE;

    public List<AbstractClassEnhancePluginDefine> load(AgentClassLoader classLoader) {
        List<AbstractClassEnhancePluginDefine> all = new ArrayList<AbstractClassEnhancePluginDefine>();
        // 插装加载器的服务加载者
        ServiceLoader<InstrumentationLoader> instrumentationLoaderServiceLoader = ServiceLoader.load(InstrumentationLoader.class, classLoader);
        for (InstrumentationLoader instrumentationLoader : instrumentationLoaderServiceLoader) {
            List<AbstractClassEnhancePluginDefine> plugins = instrumentationLoader.load(classLoader);
            if (plugins != null && !plugins.isEmpty()) {
                all.addAll(plugins);
            }
        }
        return all;
    }
}
