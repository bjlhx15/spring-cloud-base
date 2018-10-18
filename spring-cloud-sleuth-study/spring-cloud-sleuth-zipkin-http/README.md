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
  