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

/**
 * 跨度分层
 */
public enum SpanLayer {
    /**
     * 数据库
     */
    DB(1),
    /**
     * rpc框架
     */
    RPC_FRAMEWORK(2),
    /**
     * http
     */
    HTTP(3),
    /**
     * 消息队列
     */
    MQ(4),
    /**
     * 缓存
     */
    CACHE(5),
    ;

    /**
     * 分层编码
     */
    private final int code;

    SpanLayer(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // 设置分层

    public static void asDB(AbstractSpan span) {
        span.setLayer(SpanLayer.DB);
    }

    public static void asCache(AbstractSpan span) {
        span.setLayer(SpanLayer.CACHE);
    }

    public static void asRPCFramework(AbstractSpan span) {
        span.setLayer(SpanLayer.RPC_FRAMEWORK);
    }

    public static void asHttp(AbstractSpan span) {
        span.setLayer(SpanLayer.HTTP);
    }

    public static void asMQ(AbstractSpan span) {
        span.setLayer(SpanLayer.MQ);
    }
}
