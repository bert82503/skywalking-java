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

package org.apache.skywalking.apm.network.trace.component;

/**
 * The supported list of skywalking java sniffer.
 * 官方组件列表，空中漫步嗅探器支持的组件列表
 */
public class ComponentsDefine {

    /**
     * 容器
     */
    public static final OfficialComponent TOMCAT = new OfficialComponent(1, "Tomcat");

    public static final OfficialComponent HTTPCLIENT = new OfficialComponent(2, "HttpClient");

    /**
     * rpc，远程过程调用，跨进程通信
     */
    public static final OfficialComponent DUBBO = new OfficialComponent(3, "Dubbo");

    public static final OfficialComponent MOTAN = new OfficialComponent(8, "Motan");

    public static final OfficialComponent RESIN = new OfficialComponent(10, "Resin");

    public static final OfficialComponent FEIGN = new OfficialComponent(11, "Feign");

    /**
     * http客户端
     */
    public static final OfficialComponent OKHTTP = new OfficialComponent(12, "OKHttp");

    public static final OfficialComponent SPRING_REST_TEMPLATE = new OfficialComponent(13, "SpringRestTemplate");

    public static final OfficialComponent SPRING_MVC_ANNOTATION = new OfficialComponent(14, "SpringMVC");

    public static final OfficialComponent STRUTS2 = new OfficialComponent(15, "Struts2");

    public static final OfficialComponent NUTZ_MVC_ANNOTATION = new OfficialComponent(16, "NutzMVC");

    public static final OfficialComponent NUTZ_HTTP = new OfficialComponent(17, "NutzHttp");

    public static final OfficialComponent JETTY_CLIENT = new OfficialComponent(18, "JettyClient");

    public static final OfficialComponent JETTY_SERVER = new OfficialComponent(19, "JettyServer");

    public static final OfficialComponent SHARDING_JDBC = new OfficialComponent(21, "ShardingJDBC");

    /**
     * rpc
     */
    public static final OfficialComponent GRPC = new OfficialComponent(23, "GRPC");

    /**
     * 定时任务
     */
    public static final OfficialComponent ELASTIC_JOB = new OfficialComponent(24, "ElasticJob");

    /**
     * http异步客户端
     */
    public static final OfficialComponent HTTP_ASYNC_CLIENT = new OfficialComponent(26, "httpasyncclient");

    public static final OfficialComponent SERVICECOMB = new OfficialComponent(28, "ServiceComb");

    public static final OfficialComponent HYSTRIX = new OfficialComponent(29, "Hystrix");

    /**
     * redis客户端
     */
    public static final OfficialComponent JEDIS = new OfficialComponent(30, "Jedis");

    public static final OfficialComponent H2_JDBC_DRIVER = new OfficialComponent(32, "h2-jdbc-driver");

    /**
     * MySQL数据库
     */
    public static final OfficialComponent MYSQL_JDBC_DRIVER = new OfficialComponent(33, "mysql-connector-java");

    public static final OfficialComponent OJDBC = new OfficialComponent(34, "ojdbc");

    public static final OfficialComponent SPYMEMCACHED = new OfficialComponent(35, "Spymemcached");

    public static final OfficialComponent XMEMCACHED = new OfficialComponent(36, "Xmemcached");

    public static final OfficialComponent POSTGRESQL_DRIVER = new OfficialComponent(37, "postgresql-jdbc-driver");

    /**
     * MQ
     */
    public static final OfficialComponent ROCKET_MQ_PRODUCER = new OfficialComponent(38, "rocketMQ-producer");

    public static final OfficialComponent ROCKET_MQ_CONSUMER = new OfficialComponent(39, "rocketMQ-consumer");

    /**
     * MQ
     */
    public static final OfficialComponent KAFKA_PRODUCER = new OfficialComponent(40, "kafka-producer");

    public static final OfficialComponent KAFKA_CONSUMER = new OfficialComponent(41, "kafka-consumer");

    public static final OfficialComponent MONGO_DRIVER = new OfficialComponent(42, "mongodb-driver");

    public static final OfficialComponent SOFARPC = new OfficialComponent(43, "SOFARPC");

    public static final OfficialComponent ACTIVEMQ_PRODUCER = new OfficialComponent(45, "activemq-producer");

    public static final OfficialComponent ACTIVEMQ_CONSUMER = new OfficialComponent(46, "activemq-consumer");

    public static final OfficialComponent TRANSPORT_CLIENT = new OfficialComponent(48, "transport-client");

    public static final OfficialComponent RABBITMQ_PRODUCER = new OfficialComponent(52, "rabbitmq-producer");

    public static final OfficialComponent RABBITMQ_CONSUMER = new OfficialComponent(53, "rabbitmq-consumer");

    /**
     * 跨数据源同步
     */
    public static final OfficialComponent CANAL = new OfficialComponent(54, "Canal");

    /**
     * JSON
     */
    public static final OfficialComponent GSON = new OfficialComponent(55, "Gson");

    /**
     * redis客户端
     */
    public static final OfficialComponent REDISSON = new OfficialComponent(56, "Redisson");

    /**
     * redis客户端
     */
    public static final OfficialComponent LETTUCE = new OfficialComponent(57, "Lettuce");

    public static final OfficialComponent ZOOKEEPER = new OfficialComponent(58, "Zookeeper");

    public static final OfficialComponent VERTX = new OfficialComponent(59, "Vert.x");

    /**
     * 分库分表
     */
    public static final OfficialComponent SHARDING_SPHERE = new OfficialComponent(60, "ShardingSphere");

    public static final OfficialComponent SPRING_CLOUD_GATEWAY = new OfficialComponent(61, "spring-cloud-gateway");

    public static final OfficialComponent RESTEASY = new OfficialComponent(62, "RESTEasy");

    public static final OfficialComponent SOLRJ = new OfficialComponent(63, "solrj");

    public static final OfficialComponent SPRING_ASYNC = new OfficialComponent(65, "SpringAsync");

    public static final OfficialComponent JDK_HTTP = new OfficialComponent(66, "JdkHttp");

    /**
     * 反应式编程
     */
    public static final OfficialComponent SPRING_WEBFLUX = new OfficialComponent(67, "spring-webflux");

    public static final OfficialComponent PLAY = new OfficialComponent(68, "Play");

    public static final OfficialComponent CASSANDRA_JAVA_DRIVER = new OfficialComponent(69, "cassandra-java-driver");

    public static final OfficialComponent LIGHT_4J = new OfficialComponent(71, "Light4J");

    /**
     * MQ
     */
    public static final OfficialComponent PULSAR_PRODUCER = new OfficialComponent(73, "pulsar-producer");

    public static final OfficialComponent PULSAR_CONSUMER = new OfficialComponent(74, "pulsar-consumer");

    public static final OfficialComponent EHCACHE = new OfficialComponent(75, "Ehcache");

    public static final OfficialComponent SOCKET_IO = new OfficialComponent(76, "SocketIO");

    /**
     * ES，搜索引擎
     */
    public static final OfficialComponent REST_HIGH_LEVEL_CLIENT = new OfficialComponent(77, "rest-high-level-client");

    public static final OfficialComponent SPRING_TX = new OfficialComponent(78, "spring-tx");

    public static final OfficialComponent ARMERIA = new OfficialComponent(79, "Armeria");

    /**
     * JDK线程/线程池
     */
    public static final OfficialComponent JDK_THREADING = new OfficialComponent(80, "JdkThreading");

    public static final OfficialComponent KT_COROUTINE = new OfficialComponent(81, "KotlinCoroutine");

    public static final OfficialComponent AVRO_SERVER = new OfficialComponent(82, "AvroServer");

    public static final OfficialComponent AVRO_CLIENT = new OfficialComponent(83, "AvroClient");

    public static final OfficialComponent UNDERTOW = new OfficialComponent(84, "Undertow");

    public static final OfficialComponent FINAGLE = new OfficialComponent(85, "Finagle");

    public static final OfficialComponent MARIADB_JDBC = new OfficialComponent(87, "mariadb-jdbc");

    public static final OfficialComponent QUASAR = new OfficialComponent(88, "quasar");

    /**
     * 时序数据库
     */
    public static final OfficialComponent INFLUXDB_JAVA = new OfficialComponent(90, "influxdb-java");

    public static final OfficialComponent BRPC_JAVA = new OfficialComponent(91, "brpc-java");

    /**
     * SQL表达式查询
     */
    public static final OfficialComponent GRAPHQL = new OfficialComponent(92, "GraphQL");

    public static final OfficialComponent SPRING_ANNOTATION = new OfficialComponent(93, "spring-annotation");

    /**
     * 大数据
     */
    public static final OfficialComponent HBASE = new OfficialComponent(94, "HBase");

    public static final OfficialComponent SPRING_KAFKA_CONSUMER = new OfficialComponent(95, "spring-kafka-consumer");

    public static final OfficialComponent SPRING_SCHEDULED = new OfficialComponent(96, "SpringScheduled");

    public static final OfficialComponent QUARTZ_SCHEDULER = new OfficialComponent(97, "quartz-scheduler");

    /**
     * 定时任务
     */
    public static final OfficialComponent XXL_JOB = new OfficialComponent(98, "xxl-job");

    public static final OfficialComponent SPRING_WEBCLIENT = new OfficialComponent(99, "spring-webflux-webclient");

    public static final OfficialComponent THRIFT_SERVER = new OfficialComponent(100, "thrift-server");

    public static final OfficialComponent THRIFT_CLIENT = new OfficialComponent(101, "thrift-client");

    /**
     * http客户端
     */
    public static final OfficialComponent ASYNC_HTTP_CLIENT = new OfficialComponent(102, "AsyncHttpClient");

    public static final OfficialComponent DBCP = new OfficialComponent(103, "dbcp");

    public static final OfficialComponent MSSQL_JDBC_DRIVER = new OfficialComponent(104, "mssql-jdbc-driver");

    public static final OfficialComponent APACHE_CXF = new OfficialComponent(105, "Apache-CXF");

    public static final OfficialComponent DOLPHIN_SCHEDULER = new OfficialComponent(106, "dolphinscheduler");

    public static final OfficialComponent JSON_RPC = new OfficialComponent(107, "JsonRpc");

    /**
     * 事务
     */
    public static final OfficialComponent SEATA = new OfficialComponent(108, "Seata");

    /**
     * SQL扩展增强，跨数据库
     */
    public static final OfficialComponent MYBATIS = new OfficialComponent(109, "MyBatis");

    /**
     * 关系图数据库
     */
    public static final OfficialComponent NEO4J = new OfficialComponent(112, "Neo4j");

    /**
     * 流量哨兵，限流、降级
     */
    public static final OfficialComponent SENTINEL = new OfficialComponent(113, "Sentinel");

    /**
     * 本地缓存
     */
    public static final OfficialComponent GUAVA_CACHE = new OfficialComponent(114, "GuavaCache");

    /**
     * 数据库数据源
     */
    public static final OfficialComponent ALIBABA_DRUID = new OfficialComponent(115, "AlibabaDruid");

    /**
     * 数据库数据源
     */
    public static final OfficialComponent HIKARI_CP = new OfficialComponent(116, "HikariCP");

    public static final OfficialComponent FASTJSON = new OfficialComponent(117, "Fastjson");

    /**
     * JSON
     */
    public static final OfficialComponent JACKSON = new OfficialComponent(118, "Jackson");

    /**
     * 大数据
     */
    public static final OfficialComponent CLICKHOUSE_JDBC_DRIVER = new OfficialComponent(119, "ClickHouse-jdbc-driver");

    public static final OfficialComponent APACHE_KYLIN_JDBC_DRIVER = new OfficialComponent(121, "apache-kylin-jdbc-driver");

    /**
     * 事件总线
     */
    public static final OfficialComponent GUAVA_EVENT_BUS = new OfficialComponent(123, "GuavaEventBus");

    /**
     * 网关
     */
    public static final OfficialComponent APACHE_SHENYU = new OfficialComponent(127, "Apache-ShenYu");

    /**
     * http客户端
     */
    public static final OfficialComponent HUTOOL = new OfficialComponent(128, "Hutool");

    public static final OfficialComponent MICRONAUT = new OfficialComponent(131, "Micronaut");

    public static final OfficialComponent NATS = new OfficialComponent(132, "Nats");

    public static final OfficialComponent IMPALA_JDBC_DRIVER = new OfficialComponent(133, "Impala-jdbc-driver");

    /**
     * 千分尺，指标标准化
     */
    public static final OfficialComponent MICROMETER = new OfficialComponent(141, "Micrometer");

    public static final OfficialComponent JERSEY = new OfficialComponent(146, "Jersey");

    public static final OfficialComponent GRIZZLY = new OfficialComponent(147, "Grizzly");

    public static final OfficialComponent WEBSPHERE = new OfficialComponent(148, "WebSphere");

    public static final OfficialComponent AEROSPIKE = new OfficialComponent(149, "Aerospike");

    /**
     * 配置中心和注册中心
     */
    public static final OfficialComponent NACOS = new OfficialComponent(150, "Nacos");

    /**
     * http客户端
     */
    public static final OfficialComponent NETTY_HTTP = new OfficialComponent(151, "Netty-http");

}
