# spring-cloud-summary-study
    综合项目
    admin-service:[聚合监控微服务的]blog-service、gateway-service、
        log-service、uaa-service、user-service
        需要配置pom以及
    config-server：【服务配置的】除eureka-server，其他都可以使用管理配置
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
* admin-service
    查看地址：http://localhost:9998/
    用户密码：amdin  123456
* monitor-service
    查看地址：http://10.0.74.170:8766/hystrix      
* log-service
* gateway-service
* uaa-service
* user-service,blog-service

## 1.2、演示过程
1、用户注册           
curl -X POST --header 'Content-Type: application/json' --header 'Accpet: application/json' \
-d '{"password":123456,"username":"lihongxu"}' "http://localhost:5000/userapi/user/registry"   

2、使用注册的用户获取jwt
```test
curl -X POST --header 'Content-Type: application/json' --header 'Accpet: application/json' \
 'http://localhost:5000/userapi/user/login?username=lihongxu&password=123456'
``` 

3、使用curl调用根据用户名获取用户的api接口
```test
curl -X POST --header 'Content-Type: application/json' --header 'Accpet: application/json' \
 --header 'Authorization: Bearer {token}' 
 'http://localhost:5000/userapi/user/lihongxu'
``` 
直接访问是没有授权的，因为该接口需要"ROLE_USER"的权限，而"lihongxu"这个用户没有这个权限，会返回没有权限
需要在数据库中，添加权限
```sql
insert into user_role values('1','1')
```
