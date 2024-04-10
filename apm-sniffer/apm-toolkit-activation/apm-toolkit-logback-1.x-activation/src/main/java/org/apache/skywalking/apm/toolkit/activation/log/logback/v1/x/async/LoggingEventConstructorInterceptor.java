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

package org.apache.skywalking.apm.toolkit.activation.log.logback.v1.x.async;

import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.util.LogbackMDCAdapter;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceConstructorInterceptor;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

/**
 * LoggingEvent implements ILoggingEvent, which is a message in the blockingQueue of the AsyncAppenderBase.class. The
 * LoggingEvent is enhanced to carry the tid in the synchronization thread using `dynamicField`.
 */

public class LoggingEventConstructorInterceptor implements InstanceConstructorInterceptor {

    private static final String TRACE_ID = "trace_id";
    private static final String TID = "tid";

    @Override
    public void onConstruct(EnhancedInstance objInst, Object[] allArguments) {
        String globalTraceId = ContextManager.getGlobalTraceId();
        if (isEmpty(globalTraceId)) {
            return;
        }

        if (objInst instanceof LoggingEvent) {
            LoggingEvent loggingEvent = (LoggingEvent) objInst;
            // can not call getter method, because it will be initialized as empty map, after that it can not be set.
            loggingEvent.setMDCPropertyMap(new HashMap<>(16));

            Map<String, String> mdcPropertyMap = loggingEvent.getMDCPropertyMap();

            // merge user mdc
            MDCAdapter mdc = MDC.getMDCAdapter();
            if (mdc instanceof LogbackMDCAdapter) {
                Map<String, String> propertyMap = ((LogbackMDCAdapter) mdc).getPropertyMap();
                if (propertyMap != null) {
                    mdcPropertyMap.putAll(propertyMap);
                }
            } else {
                Map<String, String> copyOfContextMap = mdc.getCopyOfContextMap();
                if (copyOfContextMap != null) {
                    mdcPropertyMap.putAll(copyOfContextMap);
                }
            }

            // trace id
            if (!mdcPropertyMap.containsKey(TRACE_ID)) {
                mdcPropertyMap.put(TRACE_ID, globalTraceId);
            }
            if (!mdcPropertyMap.containsKey(TID)) {
                mdcPropertyMap.put(TID, globalTraceId);
            }
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || "N/A".equals(str);
    }
}
