# spring-cloud-oauth2-inmem
    Spring Cloud Security OAuth2 是 Spring 对 OAuth2 的开源实现，优点是能与Spring Cloud技术栈无缝集成，如果全部使用默认配置，
    开发者只需要添加注解就能完成 OAuth2 授权服务的搭建。
1、添加依赖
```xml
<dependencys>
    <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
    </dependency>
    <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-oauth2</artifactId>
     </dependency>
</dependencys>
```    
2、添加注解和配置
```java
@SpringBootApplication
@EnableAuthorizationServer
public class AlanOAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlanOAuthApplication.class, args);
    }
}
```   
// 提供/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error

 
分配 client_id, client_secret才行。Spring Security OAuth2的配置方法是编写@Configuration类继承AuthorizationServerConfigurerAdapter，
然后重写void configure(ClientDetailsServiceConfigurer clients)方法，如：
```java
@Configuration
public class SecurityConfig extends AuthorizationServerConfigurerAdapter {
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in-memory存储
                .withClient("client") // client_id
                .secret("secret") // client_secret
                .authorizedGrantTypes("authorization_code") // 该client允许的授权类型
                .scopes("app"); // 允许的授权范围
    }
}
```
3、授权流程
访问：localhost:8080/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
此时浏览器会让你输入用户名密码，这是因为 Spring Security 在默认情况下会对所有URL添加Basic Auth认证。
默认的用户名为user, 密码是随机生成的，在控制台日志中可以看到。也可以在配置文件中配置
    
