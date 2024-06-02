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

package org.apache.skywalking.apm.agent.core.context.tag;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The span tags are supported by sky-walking engine. As default, all tags will be stored, but these one have
 * particular meanings.
 * <p>
 * 跨度标签常量池
 */
public final class Tags {
    /**
     * 标签关联映射
     */
    private static final Map<String, StringTag> TAG_PROTOTYPES = new ConcurrentHashMap<>();

    private Tags() {
    }

    /**
     * URL records the url of the incoming request.
     * 入栈请求的URL
     */
    public static final StringTag URL = new StringTag(1, "url");

    /**
     * STATUS_CODE records the http status code of the response.
     * 响应的HTTP状态码
     */
    public static final IntegerTag HTTP_RESPONSE_STATUS_CODE = new IntegerTag(2, "http.status_code", true);

    /**
     * DB_TYPE records database type, such as sql, cassandra and so on.
     * 数据库类型
     */
    public static final StringTag DB_TYPE = new StringTag(3, "db.type");

    /**
     * DB_INSTANCE records database instance name.
     * 数据库实例名称
     */
    public static final StringTag DB_INSTANCE = new StringTag(4, "db.instance");

    /**
     * DB_STATEMENT records the sql statement of the database access.
     * 数据库访问的SQL语句
     */
    public static final StringTag DB_STATEMENT = new StringTag(5, "db.statement");

    /**
     * DB_BIND_VARIABLES records the bind variables of sql statement.
     * SQL语句的绑定变量
     */
    public static final StringTag DB_BIND_VARIABLES = new StringTag(6, "db.bind_vars");

    /**
     * MQ_QUEUE records the queue name of message-middleware.
     * 消息中间件的队列名称
     */
    public static final StringTag MQ_QUEUE = new StringTag(7, "mq.queue");

    /**
     * MQ_BROKER records the broker address of message-middleware.
     * 消息中间件的代理服务器地址
     */
    public static final StringTag MQ_BROKER = new StringTag(8, "mq.broker");

    /**
     * MQ_TOPIC records the topic name of message-middleware.
     * 消息中间件的主题名称
     */
    public static final StringTag MQ_TOPIC = new StringTag(9, "mq.topic");

    /**
     * MQ_STATUS records the send/consume message status of message-middleware.
     * 消息中间件的发送/消费的消息状态
     */
    public static final StringTag MQ_STATUS = new StringTag(16, "mq_status");

    /**
     * MyBatis的映射器
     */
    public static final StringTag MYBATIS_MAPPER = new StringTag(17, "mybatis.mapper");

    /**
     * The latency of transmission. When there are more than one downstream parent/segment-ref(s), multiple tags will be
     * recorded, such as a batch consumption in MQ.
     * 传输的延迟
     */
    public static final StringTag TRANSMISSION_LATENCY = new StringTag(15, "transmission.latency", false);

    /**
     * RESPONSE_CODE records the code string of the response. This is different from status code, which is
     * used to record http response code.
     * RPC响应的状态码
     */
    public static final StringTag RPC_RESPONSE_STATUS_CODE = new StringTag(18, "rpc.status_code", true);

    /**
     * HTTP标签
     */
    public static final class HTTP {
        /**
         * HTTP请求方法
         */
        public static final StringTag METHOD = new StringTag(10, "http.method");

        /**
         * HTTP请求入参
         */
        public static final StringTag PARAMS = new StringTag(11, "http.params", true);

        /**
         * HTTP请求体
         */
        public static final StringTag BODY = new StringTag(13, "http.body");

        /**
         * HTTP请求头
         */
        public static final StringTag HEADERS = new StringTag(14, "http.headers");
    }

    /**
     * 逻辑端点
     */
    public static final StringTag LOGIC_ENDPOINT = new StringTag(12, "x-le");
    /**
     * CACHE_TYPE records cache type, such as jedis
     * 缓存类型
     */
    public static final StringTag CACHE_TYPE = new StringTag(15, "cache.type");

    /**
     * CACHE_OP represent a command is used for "write" or "read"
     * It's better that adding this tag to span , so OAP would analysis write/read metric accurately
     * Reference org.apache.skywalking.apm.plugin.jedis.v4.AbstractConnectionInterceptor#parseOperation
     * BTW "op" means Operation
     * 缓存操作
     */
    public static final StringTag CACHE_OP = new StringTag(16, "cache.op");

    /**
     * CACHE_TYPE records the cache command
     * 缓存命令
     */
    public static final StringTag CACHE_CMD = new StringTag(17, "cache.cmd");

    /**
     * CACHE_TYPE records the cache key
     * 缓存的键
     */
    public static final StringTag CACHE_KEY = new StringTag(18, "cache.key");

    /**
     * CACHE_INSTANCE records the cache instance
     * 缓存实例
     */
    public static final StringTag CACHE_INSTANCE = new StringTag(20, "cache.instance");

    /**
     * 逻辑跨度
     * 本地跨度当作逻辑端点
     */
    public static final String VAL_LOCAL_SPAN_AS_LOGIC_ENDPOINT = "{\"logic-span\":true}";

    /**
     * SQL参数
     */
    public static final StringTag SQL_PARAMETERS = new StringTag(19, "db.sql.parameters");

    /**
     * LOCK_NAME records the lock name such as redisson lock name
     * 锁名称
     */
    public static final StringTag LOCK_NAME = new StringTag(21, "lock.name");

    /**
     * LEASE_TIME represents the maximum time to hold the lock after it's acquisition
     * in redisson plugin,it's unit is ms
     * 租用时间表示持有锁的最长时间
     */
    public static final StringTag LEASE_TIME = new StringTag(22, "lease.time");

    /**
     * THREAD_ID records the thread id
     * 线程身份ID
     */
    public static final StringTag THREAD_ID = new StringTag(23, "thread.id");

    /**
     * Creates a {@code StringTag} with the given key and cache it, if it's created before, simply return it without
     * creating a new one.
     *
     * @param key the {@code key} of the tag
     * @return the {@code StringTag}
     */
    public static AbstractTag<String> ofKey(final String key) {
        return TAG_PROTOTYPES.computeIfAbsent(key, StringTag::new);
    }
}
