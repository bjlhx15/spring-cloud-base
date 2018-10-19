## 一、概述
  spring-cloud-sleuth-zipkin-http 链路追踪基础项目  
  针对分布式微服务项目，采用的分布式链路追踪解决方案.springcloud主要采用sleuth+zipkin方式。  
  一个HTTP请求会调用多个不同的微服务来处理返回最后的结果，在这个调用过程中，可能会因为某个服务出现网络延迟过高或发送错误导致请求失败，
  这个时候，对请求调用的监控就显得尤为重要了。Spring Cloud Sleuth 提供了分布式服务链路监控的解决方案。        
    
  Zipkin 和 Config 结构类似，分为服务端 Server，客户端 Client，客户端就是各个微服务应用。
  [spring-boot2.0，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包]
## 二、项目结构
*   sleuth-erueka-server 服务注册中心
*   sleuth-gatewway-service  网关服务中心
*   sleuth-user-service  客户服务

### 2.1、前提背景
#### 方式一、zipkin下载使用【推荐】
  在 Spring Boot 2.0 版本之后，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包。  
  详情可以查看官网：https://zipkin.io/pages/quickstart.html 
*   终端方式：
```text
curl -sSL https://zipkin.io/quickstart.sh | bash -s
启动命令:java -jar zipkin.jar
```
*   docker方式
```text
docker run -d -p 9411:9411 openzipkin/zipkin
```
以上两种方式任一方式启动后，访问 http://localhost:9411，可以看到服务端已经搭建成功  
#### 方式二、自己搭建【不推荐】

###  2.2、客户服务、网关集成 zipkin
#### 集成消息对接
```xml
    <dependencys>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
    </dependencys>
```
#### 配置
```yaml
spring:
  application:
    name: xx-service
  zipkin:
    # http 方式
    base-url: http://127.0.0.1:9411/
    sender:
      type: WEB
  #这里把抽样百分比设置为1，即信息全部采集 注意旧版本：spring.sleuth.sampler.percentage=0.1
  sleuth:
    sampler:
      probability: 1.0
```
参看文档：https://cloud.spring.io/spring-cloud-static/spring-cloud-sleuth/2.0.0.RC1/single/spring-cloud-sleuth.html
  
  
## 三、存储配置
    默认情况下，Zipkin Server 会将跟踪信息存储在内存中，每次重启 Zipkin Server 都会使之前收集的跟踪信息丢失，并且当有大量跟踪信息时，
    内存存储也会造成性能瓶颈，所以通常我们都需要将跟踪信息存储到外部组件中，如 Mysql。

    由于 Spring Boot 2.0 之后 Zipkin 不再推荐我们来自定义 Server 端了，那么如何把 Zipkin Server 修改为 Mysql 存储功能呢？
    答案还是和集成 RabbitMQ 一样，在启动 zipkin.jar 的时候，配置相关参数
### 3.1、zipkin server配置
zipkin.jar 的 配置文件内容可以查看 GitHub 上官方的提供
https://github.com/openzipkin/zipkin/blob/master/zipkin-server/src/main/resources/zipkin-server-shared.yml
具体配置如下
```yaml
zipkin:
  self-tracing:
    # Set to true to enable self-tracing.
    enabled: ${SELF_TRACING_ENABLED:false}
    # percentage to self-traces to retain
    sample-rate: ${SELF_TRACING_SAMPLE_RATE:1.0}
    # Timeout in seconds to flush self-tracing data to storage.
    message-timeout: ${SELF_TRACING_FLUSH_INTERVAL:1}
  collector:
    # percentage to traces to retain
    sample-rate: ${COLLECTOR_SAMPLE_RATE:1.0}
    http:
      # Set to false to disable creation of spans via HTTP collector API
      enabled: ${HTTP_COLLECTOR_ENABLED:true}
    kafka:
      # Kafka bootstrap broker list, comma-separated host:port values. Setting this activates the
      # Kafka 0.10+ collector.
      bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:}
      # Name of topic to poll for spans
      topic: ${KAFKA_TOPIC:zipkin}
      # Consumer group this process is consuming on behalf of.
      group-id: ${KAFKA_GROUP_ID:zipkin}
      # Count of consumer threads consuming the topic
      streams: ${KAFKA_STREAMS:1}
    rabbitmq:
      # RabbitMQ server address list (comma-separated list of host:port)
      addresses: ${RABBIT_ADDRESSES:}
      concurrency: ${RABBIT_CONCURRENCY:1}
      # TCP connection timeout in milliseconds
      connection-timeout: ${RABBIT_CONNECTION_TIMEOUT:60000}
      password: ${RABBIT_PASSWORD:guest}
      queue: ${RABBIT_QUEUE:zipkin}
      username: ${RABBIT_USER:guest}
      virtual-host: ${RABBIT_VIRTUAL_HOST:/}
      useSsl: ${RABBIT_USE_SSL:false}
      uri: ${RABBIT_URI:}
  query:
    enabled: ${QUERY_ENABLED:true}
    # 1 day in millis
    lookback: ${QUERY_LOOKBACK:86400000}
    # The Cache-Control max-age (seconds) for /api/v2/services and /api/v2/spans
    names-max-age: 300
    # CORS allowed-origins.
    allowed-origins: "*"

  storage:
    strict-trace-id: ${STRICT_TRACE_ID:true}
    search-enabled: ${SEARCH_ENABLED:true}
    type: ${STORAGE_TYPE:mem}
    mem:
      # Maximum number of spans to keep in memory.  When exceeded, oldest traces (and their spans) will be purged.
      # A safe estimate is 1K of memory per span (each span with 2 annotations + 1 binary annotation), plus
      # 100 MB for a safety buffer.  You'll need to verify in your own environment.
      # Experimentally, it works with: max-spans of 500000 with JRE argument -Xmx600m.
      max-spans: 500000
    cassandra:
      # Comma separated list of host addresses part of Cassandra cluster. Ports default to 9042 but you can also specify a custom port with 'host:port'.
      contact-points: ${CASSANDRA_CONTACT_POINTS:localhost}
      # Name of the datacenter that will be considered "local" for latency load balancing. When unset, load-balancing is round-robin.
      local-dc: ${CASSANDRA_LOCAL_DC:}
      # Will throw an exception on startup if authentication fails.
      username: ${CASSANDRA_USERNAME:}
      password: ${CASSANDRA_PASSWORD:}
      keyspace: ${CASSANDRA_KEYSPACE:zipkin}
      # Max pooled connections per datacenter-local host.
      max-connections: ${CASSANDRA_MAX_CONNECTIONS:8}
      # Ensuring that schema exists, if enabled tries to execute script /zipkin-cassandra-core/resources/cassandra-schema-cql3.txt.
      ensure-schema: ${CASSANDRA_ENSURE_SCHEMA:true}
      # 7 days in seconds
      span-ttl: ${CASSANDRA_SPAN_TTL:604800}
      # 3 days in seconds
      index-ttl: ${CASSANDRA_INDEX_TTL:259200}
      # the maximum trace index metadata entries to cache
      index-cache-max: ${CASSANDRA_INDEX_CACHE_MAX:100000}
      # how long to cache index metadata about a trace. 1 minute in seconds
      index-cache-ttl: ${CASSANDRA_INDEX_CACHE_TTL:60}
      # how many more index rows to fetch than the user-supplied query limit
      index-fetch-multiplier: ${CASSANDRA_INDEX_FETCH_MULTIPLIER:3}
      # Using ssl for connection, rely on Keystore
      use-ssl: ${CASSANDRA_USE_SSL:false}
    cassandra3:
      # Comma separated list of host addresses part of Cassandra cluster. Ports default to 9042 but you can also specify a custom port with 'host:port'.
      contact-points: ${CASSANDRA_CONTACT_POINTS:localhost}
      # Name of the datacenter that will be considered "local" for latency load balancing. When unset, load-balancing is round-robin.
      local-dc: ${CASSANDRA_LOCAL_DC:}
      # Will throw an exception on startup if authentication fails.
      username: ${CASSANDRA_USERNAME:}
      password: ${CASSANDRA_PASSWORD:}
      keyspace: ${CASSANDRA_KEYSPACE:zipkin2}
      # Max pooled connections per datacenter-local host.
      max-connections: ${CASSANDRA_MAX_CONNECTIONS:8}
      # Ensuring that schema exists, if enabled tries to execute script /zipkin2-schema.cql
      ensure-schema: ${CASSANDRA_ENSURE_SCHEMA:true}
      # how many more index rows to fetch than the user-supplied query limit
      index-fetch-multiplier: ${CASSANDRA_INDEX_FETCH_MULTIPLIER:3}
      # Using ssl for connection, rely on Keystore
      use-ssl: ${CASSANDRA_USE_SSL:false}
    elasticsearch:
      # host is left unset intentionally, to defer the decision
      hosts: ${ES_HOSTS:}
      pipeline: ${ES_PIPELINE:}
      max-requests: ${ES_MAX_REQUESTS:64}
      timeout: ${ES_TIMEOUT:10000}
      index: ${ES_INDEX:zipkin}
      date-separator: ${ES_DATE_SEPARATOR:-}
      index-shards: ${ES_INDEX_SHARDS:5}
      index-replicas: ${ES_INDEX_REPLICAS:1}
      username: ${ES_USERNAME:}
      password: ${ES_PASSWORD:}
      http-logging: ${ES_HTTP_LOGGING:}
      legacy-reads-enabled: ${ES_LEGACY_READS_ENABLED:true}
    mysql:
      jdbc-url: ${MYSQL_JDBC_URL:}
      host: ${MYSQL_HOST:localhost}
      port: ${MYSQL_TCP_PORT:3306}
      username: ${MYSQL_USER:}
      password: ${MYSQL_PASS:}
      db: ${MYSQL_DB:zipkin}
      max-active: ${MYSQL_MAX_CONNECTIONS:10}
      use-ssl: ${MYSQL_USE_SSL:false}
  ui:
    enabled: ${QUERY_ENABLED:true}
    ## Values below here are mapped to ZipkinUiProperties, served as /config.json
    # Default limit for Find Traces
    query-limit: 10
    # The value here becomes a label in the top-right corner
    environment:
    # Default duration to look back when finding traces.
    # Affects the "Start time" element in the UI. 1 hour in millis
    default-lookback: 3600000
    # When false, disables the "find a trace" screen
    search-enabled: ${SEARCH_ENABLED:true}
    # Which sites this Zipkin UI covers. Regex syntax. (e.g. http:\/\/example.com\/.*)
    # Multiple sites can be specified, e.g.
    # - .*example1.com
    # - .*example2.com
    # Default is "match all websites"
    instrumented: .*
    # URL placed into the <base> tag in the HTML
    base-path: /zipkin

server:
  port: ${QUERY_PORT:9411}
  use-forward-headers: true
  compression:
    enabled: true
    # compresses any response over min-response-size (default is 2KiB)
    # Includes dynamic json content and large static assets from zipkin-ui
    mime-types: application/json,application/javascript,text/css,image/svg

spring:
  jmx:
     # reduce startup time by excluding unexposed JMX service
     enabled: false
  mvc:
    favicon:
      # zipkin has its own favicon
      enabled: false
  autoconfigure:
    exclude:
      # otherwise we might initialize even when not needed (ex when storage type is cassandra)
      - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
info:
  zipkin:
    version: "@project.version@"

logging:
  pattern:
    level: "%clr(%5p) %clr([%X{traceId}/%X{spanId}]){yellow}"
  level:
    # Silence Invalid method name: '__can__finagle__trace__v3__'
    com.facebook.swift.service.ThriftServiceProcessor: 'OFF'
#     # investigate /api/v2/dependencies
#     zipkin2.internal.DependencyLinker: 'DEBUG'
#     # log cassandra queries (DEBUG is without values)
#     com.datastax.driver.core.QueryLogger: 'TRACE'
#     # log cassandra trace propagation
#     com.datastax.driver.core.Message: 'TRACE'
#     # log reason behind http collector dropped messages
#     zipkin2.server.ZipkinHttpCollector: 'DEBUG'
#     zipkin2.collector.kafka.KafkaCollector: 'DEBUG'
#     zipkin2.collector.kafka08.KafkaCollector: 'DEBUG'
#     zipkin2.collector.rabbitmq.RabbitMQCollector: 'DEBUG'
#     zipkin2.collector.scribe.ScribeCollector: 'DEBUG'

management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: always
# Disabling auto time http requests since it is added in Undertow HttpHandler in Zipkin autoconfigure
# Prometheus module. In Zipkin we use different naming for the http requests duration
  metrics:
    web:
      server:
        auto-time-requests: false
```

### 3.2 mysql 存储
1、创建数据库、表信息  
既然数据存储改为 Mysql，那么肯定要建立相关的表，建表的脚本文件 mysql.sql 在 GitHub 官方地址能查到，链接如下：
https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql
2、配置说明
从配置文件可以看出，storage 默认采用的是内存存储，所以需要要设置 zipkin.storage.type=mysql，
除此之外还要设置 zipkin.storage.mysql.host、port、username、password、db 等
3、配置启动
* http方式接收数据 mysql存储
```text
java -jar zipkin.jar --zipkin.storage.type=mysql 
--zipkin.storage.mysql.host=localhost --zipkin.storage.mysql.port=3306 --zipkin.storage.mysql.username=root 
--zipkin.storage.mysql.password=mysql --zipkin.storage.mysql.db=zipkin
```
* rabbit方式接收数据 mysql存储
```text
java -jar zipkin.jar --zipkin.collector.rabbitmq.addresses=localhost --zipkin.storage.type=mysql 
--zipkin.storage.mysql.host=localhost --zipkin.storage.mysql.port=3306 --zipkin.storage.mysql.username=root 
--zipkin.storage.mysql.password=mysql --zipkin.storage.mysql.db=zipkin
```

### 3.3 elastsearch 存储
1、配置启动
* http方式接收数据 elastsearch存储,查看上文配置即可
```text
java -jar zipkin.jar --zipkin.storage.type=elastsearch  ...... 
```
2、结合kibana
步骤一、安装完成kibana后启动，kibana默认会向本地端口9200的elastsearch读取数据，kibana默认的端口为5601.此时访问：http://localhost:5601/  
步骤二、点击management按钮，点击 add new，添加一个index，配置为zipkin-*.  
此时可以查看数据。

