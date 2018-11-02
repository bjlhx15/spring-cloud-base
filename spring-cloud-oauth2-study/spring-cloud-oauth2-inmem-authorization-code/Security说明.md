## WebSecurityConfigurerAdapter
@EnableWebSecurity注解以及WebSecurityConfigurerAdapter一起配合提供基于web的security。继承了WebSecurityConfigurerAdapter之后，再加上几行代码，我们就能实现以下的功能：
  
    要求用户在进入你的应用的任何URL之前都进行验证
    创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户
    启用HTTP Basic和基于表单的验证
    Spring Security将会自动生成一个登陆页面和登出成功页面
```java
@Configuration
@EnableWebSecurity
public class  HelloWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protectedvoid  registerAuthentication(AuthenticationManagerBuilder auth) {
    auth.inMemoryAuthentication()
    .withUser("user").password("password").roles("USER");
    }
}
```
##AuthorizationServerConfigurerAdapter
ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
AuthorizationServerSecurityConfigurer：用来配置令牌端点(Token Endpoint)的安全约束.
AuthorizationServerEndpointsConfigurer：用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。



