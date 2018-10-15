## 一、概述
  spring-cloud-sleuth-zipkin 链路追踪基础项目  
  针对分布式微服务项目，采用的分布式链路追踪解决方案.springcloud主要采用sleuth+zipkin方式。  
  一个HTTP请求会调用多个不同的微服务来处理返回最后的结果，在这个调用过程中，可能会因为某个服务出现网络延迟过高或发送错误导致请求失败，
  这个时候，对请求调用的监控就显得尤为重要了。Spring Cloud Sleuth 提供了分布式服务链路监控的解决方案。        
    
  Zipkin 和 Config 结构类似，分为服务端 Server，客户端 Client，客户端就是各个微服务应用。
## 二、项目结构
*   sleuth-erueka-server 服务注册中心
*   sleuth-zipkin-server  zipkin链路服务端 [spring-boot2.0，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包]
*   sleuth-gatewway-service  网关服务中心
*   sleuth-user-service  客户服务

### 2.1、服务注册中心
*    sleuth-erueka-server  
    服务注册中心，基本的erueka项目
###  2.2、zipkin链路服务端
#### 方式一、 官方版本直接使用【推荐】
  在 Spring Boot 2.0 版本之后，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包。  
  详情可以查看官网：https://zipkin.io/pages/quickstart.html 
*   终端方式：
```text
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```
*   docker方式
```text
docker run -d -p 9411:9411 openzipkin/zipkin
```
以上两种方式任一方式启动后，访问 http://localhost:9411，可以看到服务端已经搭建成功  
#### 方式二、自己搭建【不推荐】
*   sleuth-zipkin-server  
    zipkin链路服务，增加的pom
```xml
    <dependencies>
        <dependency>
            <groupId>io.zipkin.java</groupId>
            <artifactId>zipkin-server</artifactId>
            <version>2.10.4</version>
        </dependency>
        <dependency>
            <groupId>io.zipkin.java</groupId>
            <artifactId>zipkin-autoconfigure-ui</artifactId>
            <version>2.10.4</version>
        </dependency>
    </dependencies>
```
```yaml
# zipkin server2.0版本 需要添加以下,否则会报错
management:  
  metrics:  
    web:  
      server:  
        auto-time-requests: false  
# 报错信息：Prometheus requires that all meters with the same name have the same set of tag keys.
```        
#### 客户端使用
* pom
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```
* 配置文件
```yaml
spring:
  zipkin:
    base-url: http://192.168.174.128:9411/
```
### 2.3、网关服务中心
*    sleuth-gatewway-service  
    网关路由转发。此处使用springlcloud-gateway创建  
* pom  
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-sleuth</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
    </dependencies>
```    
#### 方式一、配置application.yml-服务发现组件结合
```yaml
  cloud:
    gateway: 
      discovery:      #方式一、是否与服务发现组件进行结合，通过 serviceId(必须设置成大写) 转发到具体的服务实例。默认为false，设为true便开启通过服务中心的自动根据 serviceId 创建路由的功能。
        locator:      #路由访问方式：http://Gateway_HOST:Gateway_PORT/大写的serviceId/**，其中微服务应用名默认大写访问。
          enabled: true
```
#### 方式二、配置application.yml-route配置  
  http请求为lb://前缀 + 服务id；ws请求为lb:ws://前缀 + 服务id；表示将请求负载到哪一个服务上
```yaml
  spring:
    cloud:
      routes: #方式二 使用lb负载均衡加载
      - id: USERSERVICE
        uri: lb://USER-SERVICE:8762
        predicates:
          - Path=/userapi/**
        filters:
          - StripPrefix=1
```
#### 方式三、配置application.yml-route配置  
  直接使用 域名地址
```yaml
      routes:  #方式三 直接使用 域名地址
      - id: USERSERVICE
        uri: http://localhost:8762
        predicates:
          - Path=/userapi/**
        filters:
          - StripPrefix=1
```
### 2.3、客户服务
*    sleuth-erueka-server  
    示例服务
    
## 三、启动调用
1、启动EurekaServer  
    可以访问：http://localhost:8761,查看服务注册情况  
2、启动ZipkinServer  
    可以访问：http://localhost:9411，查看链路追踪信息  
3、启动GatewayService  
    访问：http://localhost:8080/userapi/user/hi,进行测试  
4、启动客户服务  

## 四、分析
*    查看控制台的日志输出，
        [user-service,450165b378a38236,92377ff04d8a9cfb,false] 的日志信息，
    而这些元素正是实现分布式服务跟踪的重要组成部分，每个值的含义如下：  
```text
第一个值：service1，它记录了应用的名称
第二个值：450165b378a38236，是 Spring Cloud Sleuth 生成的一个 ID，称为 Trace ID，它用来标识一条请求链路。一条请求链路中包含一个 Trace ID，多个 Span ID。
第三个值：92377ff04d8a9cfb，是 Spring Cloud Sleuth 生成的另外一个 ID，称为 Span ID，它表示一个基本的工作单元，比如发送一个 HTTP 请求。
第四个值：false，它表示是否要将该信息输出到 Zipkin Server 中来收集和展示。
```
上面四个值中的 Trace ID 和 Span ID 是 Spring Cloud Sleuth 实现分布式服务跟踪的核心。
在一次请求中，会保持并传递同一个 Trance ID，从而将整个fenbu分布于不同微服务进程中的请求跟踪信息串联起来。
*   访问 Zipkin Server 端，http://192.168.174.128:9411/  
    发现服务名下并没有看到我们的应用
    原因：因为 Spring Cloud Sleuth 采用了抽样收集的方式来为跟踪信息打上收集标记，也就是上面看到的第四个值。
    为什么要使用抽样收集？理论上应该是收集的跟踪信息越多越好，可以更好的反映出系统的实际运行情况，
    但是在高并发的分布式系统运行时，大量请求调用会产生海量的跟踪日志信息，如果过多的收集，
    会对系统性能造成一定的影响，所以 Spring Cloud Sleuth 采用了抽样收集的方式。
      
    Sleuth 默认采样算法的实现是 Reservoir sampling，具体的实现类是 PercentageBasedSampler，
    默认的采样比例为: 0.1，即 10%。可以通过 spring.sleuth.sampler.probability 来设置，
    所设置的值介于 0 到 1 之间，1 则表示全部采集
```yaml
spring:
  application:
    name: user-service
  zipkin:
    base-url: http://192.168.174.128:9411/ 
  sleuth:
    sampler:
      probability: 1.0
```
此时再次查看即可查看全部链路信息
      
     
     
     