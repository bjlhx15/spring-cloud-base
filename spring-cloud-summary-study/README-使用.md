# spring-cloud-summary-study
    综合项目
    admin-service:blog-service、gateway-service、
        log-service、uaa-service、user-service
        需要配置pom以及
    config-server：除eureka-server，其他都可以使用管理配置
        使用者需要配置：
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    publisher-confirms: true
    virtual-host: /
management.endpoint.bus-refresh.enabled: true
management.endpoints.web.exposure.include: bus-refresh
```
    eureka-server：除eureka-server，其他都可以使用管理服务
    
## 1.1、依次启动
* eureka-server、config-server
    启动main方法  
  查看：http://localhost:8761/
* rabbitmq  
查看：http://localhost:15672
* zipkin jar
    下载jar包，本地启动。默认使用http方式  
  查看：http://localhost:9411/zipkin/
```text
java -jar zipkin-server-2.11.7-exec.jar
```     
再启动其他服务
    
