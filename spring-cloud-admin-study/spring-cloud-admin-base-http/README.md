## 一、概述
  基本的通过http请求admin server注册监控
  admin-base-http-admin-server  监控服务端
  admin-base-http-clien-one 客户端1
## 二、配置简介
### 2.1、admin-base-http-admin-server 服务端
1、pom
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--spring cloud admin server ui-->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.0.2</version>
        </dependency>
    </dependencies>
```
2、配置文件
```yaml
server:
  port: 8080
```
3、启动类配置
```java
@SpringBootApplication
@EnableAdminServer
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
```

### 2.2、admin-base-http-client-one 客户端
1、pom
```xml
    <dependencies>
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-client</artifactId>
            <version>2.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```
2、配置文件
```yaml
server:
  port: 8762
spring:
  application:
    name: user-service
spring.boot.admin.client.url: "http://localhost:8080"
management.endpoints.web.exposure.include: "*"
```
3、增加权限配置类【暂时禁用了安全性】
```java
@Configuration
public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()  
            .and().csrf().disable();
    }
}
```
4、启动类 无需配置

#### 2.2、启动测试
在启动客户端，服务端后，访问服务监控查看：http://localhost:8080/
