# spring-cloud-oauth2-inmem-authorization-code
    授权码方式获取token
        获取code
        铜鼓code获取token
    Spring Cloud Security OAuth2 是 Spring 对 OAuth2 的开源实现，优点是能与Spring Cloud技术栈无缝集成，如果全部使用默认配置，
    开发者只需要添加注解就能完成 OAuth2 授权服务的搭建。
    授权码模式（authorization code）是功能最完整、流程最严密的授权模式。它的特点就是通过客户端的后台服务器，与"服务提供商"的认证服务器进行互动。
原理地址：https://tools.ietf.org/html/rfc6749#section-4.1
## 1、过程
```text
     +----------+
     | Resource |
     |   Owner  |
     |          |
     +----------+
          ^
          |
         (B)
     +----|-----+          Client Identifier      +---------------+
     |         -+----(A)-- & Redirection URI ---->|               |
     |  User-   |                                 | Authorization |
     |  Agent  -+----(B)-- User authenticates --->|     Server    |
     |          |                                 |               |
     |         -+----(C)-- Authorization Code ---<|               |
     +-|----|---+                                 +---------------+
       |    |                                         ^      v
      (A)  (C)                                        |      |
       |    |                                         |      |
       ^    v                                         |      |
     +---------+                                      |      |
     |         |>---(D)-- Authorization Code ---------'      |
     |  Client |          & Redirection URI                  |
     |         |                                             |
     |         |<---(E)----- Access Token -------------------'
     +---------+       (w/ Optional Refresh Token)
```
* （A）用户访问客户端，后者将前者导向认证服务器。  
* （B）用户选择是否给予客户端授权。  
* （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。  
* （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。  
* （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。  

A步骤中，客户端申请认证的URI，包含以下参数：
*  response_type：表示授权类型，必选项，此处的值固定为"code"
*  client_id：表示客户端的ID，必选项
*  redirect_uri：表示重定向URI，可选项
*  scope：表示申请的权限范围，可选项
*  state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。

C步骤中，服务器回应客户端的URI，包含以下参数：
*  code：表示授权码，必选项。该码的有效期应该很短，通常设为10分钟，客户端只能使用该码一次，否则会被授权服务器拒绝。该码与客户端ID和重定向URI，是一一对应关系。
*  state：如果客户端的请求中包含这个参数，认证服务器的回应也必须一模一样包含这个参数。

D步骤中，客户端向认证服务器申请令牌的HTTP请求，包含以下参数：
*  grant_type：表示使用的授权模式，必选项，此处的值固定为"authorization_code"。
*  code：表示上一步获得的授权码，必选项。
*  redirect_uri：表示重定向URI，必选项，且必须与A步骤中的该参数值保持一致。
*  client_id：表示客户端ID，必选项。

E步骤中，认证服务器发送的HTTP回复，包含以下参数：
*  access_token：表示访问令牌，必选项。
*  token_type：表示令牌类型，该值大小写不敏感，必选项，可以是bearer类型或mac类型。
*  expires_in：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
*  refresh_token：表示更新令牌，用来获取下一次的访问令牌，可选项。
*  scope：表示权限范围，如果与客户端申请的范围一致，此项可省略。

## 3、使用
##### 1、获取code
浏览器访问：
http://localhost:8080/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
此时浏览器会让你输入用户名密码，这是因为 Spring Security 在默认情况下会对所有URL添加Basic Auth认证。
默认的用户名为user, 密码是随机生成的，在控制台日志中可以看到。也可以在配置文件中配置。如上：admin 123456
此时会导向：https://www.baidu.com/?code=XI7hgp  生成code
##### 2、通过code获取token
方式一、
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=authorization_code&code=XI7hgp&redirect_uri=http://www.baidu.com' "http://client:secret@localhost:8080/oauth/token"

方式二、postman工具等【注意替换code】
post http://localhost:8080/oauth/token?grant_type=authorization_code&code=XI7hgp&redirect_uri=http://www.baidu.com
请求类型：Content-Type：application/x-www-form-urlencoded【默认】
请求头：Authorization 值：Basic Y2xpZW50OnNlY3JldA==  【用户密码client  secret】
    
##### 3、使用token访问资源
http://localhost:8080/api/blog/1?access_token=85655b4e-6858-4c0c-90b4-ef6b51afcf36

##### 4、check-token
方式二、使用postman等工具
post访问：http://localhost:8080/oauth/check_token?token=85655b4e-6858-4c0c-90b4-ef6b51afcf36
配置请求头：添加Authorization请求头，用户名：client，密码：secret

##### 4、刷新token
方式二、使用postman等工具
post访问：http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=0cdb7b83-28a6-40fd-b661-0a8612fae052
配置请求头：添加Authorization请求头，用户名：client，密码：secret