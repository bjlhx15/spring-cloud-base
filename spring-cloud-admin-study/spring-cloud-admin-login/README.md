## 一、概述
  基本的通过服务注册发现请求admin server注册监控
  admin-base-discovery-admin-server  监控服务端
  admin-base-discovery-eureka-server 服务注册
  admin-base-discovery-clien-one 客户端1

## 启动测试
在启动服务注册、客户端，服务端后，访问服务监控查看：http://localhost:8080/

参看地址：http://codecentric.github.io/spring-boot-admin/2.0.0/#_securing_spring_boot_admin_server

### admin-base-discovery-eureka-server 基本服务
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```
正常配置即可
```yaml
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
    preferIpAddress: true
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```
### admin-base-discovery-admin-server  监控服务端
pom
```xml
   <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--spring cloud admin server ui-->
        <!--内部包含了 server 以及 ui，以及eureka client  actuator等 -->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!--增加安全校验-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
   </dependencies>
```
增加配置类
```java
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");

        http.authorizeRequests()
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic().and()
                .csrf().disable();
        // @formatter:on
    }
}
```
配置文件【根据实际情况进行配置】
```yaml
server:
  port: 5000

spring:
  application:
    name: service-admin
  # 外面登录
  security:
    user:
      name: admin
      password: 1234
  #  boot:
  #    admin:
  #      client:
  #        url: http://localhost:5000
  #        #  方式一 使用SBA客户端提交凭据 
  #        instance:
  #          metadata:
  #            user.name: ${spring.security.user.name}
  #            user.password: ${spring.security.user.password}


eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
  # 方式二、使用Eureka提交凭据
    metadata-map:
      user.name: ${spring.security.user.name}
      user.password: ${spring.security.user.password}
```
###  admin-base-discovery-clien-one 客户端1
客户端无需特殊配置，此处是与eureka-server打交道