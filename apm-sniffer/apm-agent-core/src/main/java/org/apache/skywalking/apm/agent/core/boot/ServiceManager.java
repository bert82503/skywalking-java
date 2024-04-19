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

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import org.apache.skywalking.apm.agent.core.logging.api.ILog;
import org.apache.skywalking.apm.agent.core.logging.api.LogManager;
import org.apache.skywalking.apm.agent.core.plugin.loader.AgentClassLoader;

/**
 * The <code>ServiceManager</code> bases on {@link ServiceLoader}, load all {@link BootService} implementations.
 * 服务管理者，基于服务加载者，加载所有启动服务实现。
 */
public enum ServiceManager {
    /**
     * 单例
     */
    INSTANCE;

    private static final ILog LOGGER = LogManager.getLogger(ServiceManager.class);
    /**
     * 所有启动的服务的映射表
     */
    private Map<Class<? extends BootService>, BootService> bootedServices = Collections.emptyMap();

    public void boot() {
        // 加载所有启动服务
        bootedServices = loadAllServices();

        // 准备阶段
        prepare();
        // 启动阶段
        startup();
        // 完成阶段
        onComplete();
    }

    /**
     * 关闭阶段
     */
    public void shutdown() {
        bootedServices.values().stream().sorted(Comparator.comparingInt(BootService::priority).reversed()).forEach(service -> {
            try {
                // 关闭启动服务
                service.shutdown();
            } catch (Throwable e) {
                LOGGER.error(e, "ServiceManager try to shutdown [{}] fail.", service.getClass().getName());
            }
        });
    }

    /**
     * 加载所有启动服务
     */
    private Map<Class<? extends BootService>, BootService> loadAllServices() {
        Map<Class<? extends BootService>, BootService> bootedServices = new LinkedHashMap<>();
        List<BootService> allServices = new LinkedList<>();
        // 加载所有启动服务
        load(allServices);
        // 默认/覆盖的实现者机制
        for (final BootService bootService : allServices) {
            Class<? extends BootService> bootServiceClass = bootService.getClass();
            // 默认的实现者
            boolean isDefaultImplementor = bootServiceClass.isAnnotationPresent(DefaultImplementor.class);
            if (isDefaultImplementor) {
                if (!bootedServices.containsKey(bootServiceClass)) {
                    bootedServices.put(bootServiceClass, bootService);
                } else {
                    //ignore the default service
                }
            } else {
                // 覆盖的实现者
                OverrideImplementor overrideImplementor = bootServiceClass.getAnnotation(OverrideImplementor.class);
                if (overrideImplementor == null) {
                    if (!bootedServices.containsKey(bootServiceClass)) {
                        bootedServices.put(bootServiceClass, bootService);
                    } else {
                        throw new ServiceConflictException("Duplicate service define for :" + bootServiceClass);
                    }
                } else {
                    Class<? extends BootService> targetService = overrideImplementor.value();
                    if (bootedServices.containsKey(targetService)) {
                        boolean presentDefault = bootedServices.get(targetService)
                                                               .getClass()
                                                               .isAnnotationPresent(DefaultImplementor.class);
                        if (presentDefault) {
                            bootedServices.put(targetService, bootService);
                        } else {
                            throw new ServiceConflictException(
                                "Service " + bootServiceClass + " overrides conflict, " + "exist more than one service want to override :" + targetService);
                        }
                    } else {
                        bootedServices.put(targetService, bootService);
                    }
                }
            }

        }
        return bootedServices;
    }

    /**
     * 准备阶段
     */
    private void prepare() {
        bootedServices.values().stream().sorted(Comparator.comparingInt(BootService::priority)).forEach(service -> {
            try {
                // 启动服务的准备阶段
                service.prepare();
            } catch (Throwable e) {
                LOGGER.error(e, "ServiceManager try to pre-start [{}] fail.", service.getClass().getName());
            }
        });
    }

    /**
     * 启动阶段
     */
    private void startup() {
        bootedServices.values().stream().sorted(Comparator.comparingInt(BootService::priority)).forEach(service -> {
            try {
                // 启动服务的启动阶段
                service.boot();
            } catch (Throwable e) {
                LOGGER.error(e, "ServiceManager try to start [{}] fail.", service.getClass().getName());
            }
        });
    }

    /**
     * 完成阶段
     */
    private void onComplete() {
        for (BootService service : bootedServices.values()) {
            try {
                // 启动服务的完成阶段
                service.onComplete();
            } catch (Throwable e) {
                LOGGER.error(e, "Service [{}] AfterBoot process fails.", service.getClass().getName());
            }
        }
    }

    /**
     * Find a {@link BootService} implementation, which is already started.
     * 查找已启动的 BootService 实现。
     *
     * @param serviceClass class name.
     * @param <T>          {@link BootService} implementation class.
     * @return {@link BootService} instance
     */
    public <T extends BootService> T findService(Class<T> serviceClass) {
        return (T) bootedServices.get(serviceClass);
    }

    /**
     * 加载所有启动服务
     */
    void load(List<BootService> allServices) {
        // 启动服务的服务加载者
        ServiceLoader<BootService> bootServiceServiceLoader = ServiceLoader.load(BootService.class, AgentClassLoader.getDefault());
        for (final BootService bootService : bootServiceServiceLoader) {
            allServices.add(bootService);
        }
    }
}
