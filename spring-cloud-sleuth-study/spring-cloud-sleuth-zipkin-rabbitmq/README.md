# spring-cloud-sleuth-zipkin
    spring cloud 链路追踪基础项目  
    针对分布式微服务项目，采用的分布式链路追踪解决方案.springcloud主要采用sleuth+zipkin方式。        
## 项目结构
*   sleuth-erueka-server 服务注册中心
*   sleuth-zipkin-server  zipkin链路服务
*   sleuth-gatewway-service  网关服务中心
*   sleuth-user-service  客户服务

### 服务注册中心
*    sleuth-erueka-server  
    服务注册中心，基本的erueka项目
###  zipkin链路服务
*   sleuth-zipkin-server  
    zipkin链路服务，增加的pom
```$xslt
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
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
```
// # zipkin server2.0版本 需要添加以下,否则会报错
management:  
  metrics:  
    web:  
      server:  
        auto-time-requests: false  
报错信息：Prometheus requires that all meters with the same name have the same set of tag keys.        
### 网关服务中心
*    sleuth-gatewway-service  
    网关路由转发。此处使用springlcloud-gateway创建  
pom  
```$xslt
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
```    
方式一、配置application.yml-服务发现组件结合
```$xslt
  cloud:
    gateway: 
      discovery:      #方式一、是否与服务发现组件进行结合，通过 serviceId(必须设置成大写) 转发到具体的服务实例。默认为false，设为true便开启通过服务中心的自动根据 serviceId 创建路由的功能。
        locator:      #路由访问方式：http://Gateway_HOST:Gateway_PORT/大写的serviceId/**，其中微服务应用名默认大写访问。
          enabled: true
```
方式二、配置application.yml-route配置  
  http请求为lb://前缀 + 服务id；ws请求为lb:ws://前缀 + 服务id；表示将请求负载到哪一个服务上
```$xslt
spring
    cloud:
      routes: #方式二 使用lb负载均衡加载
      - id: USERSERVICE
        uri: lb://USER-SERVICE:8762
        predicates:
          - Path=/userapi/**
        filters:
          - StripPrefix=1
```
方式三、配置application.yml-route配置  
  直接使用 域名地址
```$xslt
      routes:  #方式三 直接使用 域名地址
      - id: USERSERVICE
        uri: http://localhost:8762
        predicates:
          - Path=/userapi/**
        filters:
          - StripPrefix=1
```
### 客户服务
*    sleuth-erueka-server  
    示例服务
    
## 二、启动调用
1、启动EurekaServer  
    可以访问：http://localhost:8761,查看服务注册情况  
2、启动ZipkinServer  
    可以访问：http://localhost:9411，查看链路追踪信息  
3、启动GatewayService  
    访问：http://localhost:8080/userapi/user/hi,进行测试  
4、启动客户服务     