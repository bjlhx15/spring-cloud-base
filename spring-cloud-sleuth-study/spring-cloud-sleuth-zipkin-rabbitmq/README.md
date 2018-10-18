## 一、概述
  spring-cloud-sleuth-zipkin-rabbitmq 链路追踪基础项目  
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
#### 1、rabbitmq docker 
* 下载 
```text
    docker search rabbitmq:management  下载含有管理端的rabbitmq
    docker pull macintoshplus/rabbitmq-management
    docker images
```
* 安装
```text
docker run -d -p 5671:5671 -p 5672:5672 -p 15672:15672 -p 15671:15671 -p 25672:25672 --name rabbitmq macintoshplus/rabbitmq-management
```
查看访问web 管理端了 http://宿主机IP:15672，默认创建了一个 guest 用户，密码也是 guest    
#### 2、zipkin下载使用【jar】
  在 Spring Boot 2.0 版本之后，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包。  
  详情可以查看官网：https://zipkin.io/pages/quickstart.html 
*   终端方式：
```text
curl -sSL https://zipkin.io/quickstart.sh | bash -s  
启动方式：默认http方式：java -jar zipkin.jar  
启动方式：rabbitmq方式：java -jar zipkin-server-2.11.7-exec.jar --zipkin.collector.rabbitmq.addresses=127.0.0.1
```
*   docker方式
```text
docker run -d -p 9411:9411 openzipkin/zipkin
```
以上两种方式任一方式启动后，访问 http://localhost:9411，可以看到服务端已经搭建成功 
此时查看rabbitmq的管理界面：http://127.0.0.2:15672 
RabbitMQ的Queues 里已经创建了 一个 zipkin 的队列，说明 ZipServer 集成 RabbitMQ 成功了。

###  2.2、客户服务、网关集成 消息队列
#### 集成消息对接
```xml
    <dependencys>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
        </dependency>
    </dependencys>
```
或者 对接配置可以使用
```xml
    <dependencys>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit</artifactId>
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
spring-cloud-sleuth-stream 已弃用，不应再使用
  