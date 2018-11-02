# spring-cloud-oauth2-inmem-password
    密码模式（Resource Owner Password Credentials Grant）中，用户向客户端提供自己的用户名和密码。客户端使用这些信息，向"服务商提供商"索要授权。
    在这种模式中，用户必须把自己的密码给客户端，但是客户端不得储存密码。这通常用在用户对客户端高度信任的情况下，比如客户端是操作系统的一部分，
    或者由一个著名公司出品。而认证服务器只有在其他授权模式无法执行的情况下，才能考虑使用这种模式。
支持password模式要配置AuthenticationManager  
原文地址：https://tools.ietf.org/html/rfc6749#section-4.3
## 1、过程
```text
     +----------+
     | Resource |
     |  Owner   |
     |          |
     +----------+
          v
          |    Resource Owner
         (A) Password Credentials
          |
          v
     +---------+                                  +---------------+
     |         |>--(B)---- Resource Owner ------->|               |
     |         |         Password Credentials     | Authorization |
     | Client  |                                  |     Server    |
     |         |<--(C)---- Access Token ---------<|               |
     |         |    (w/ Optional Refresh Token)   |               |
     +---------+                                  +---------------+
```
* （A）用户向客户端提供用户名和密码。
* （B）客户端将用户名和密码发给认证服务器，向后者请求令牌。
* （C）认证服务器确认无误后，向客户端提供访问令牌。
## 2、步骤说明
B步骤中，客户端发出的HTTP请求，包含以下参数：
*  grant_type：表示授权类型，此处的值固定为"password"，必选项。
*  username：表示用户名，必选项。
*  password：表示用户的密码，必选项。
*  scope：表示权限范围，可选项。

C步骤中，认证服务器向客户端发送访问令牌

整个过程中，客户端不得保存用户的密码。

## 1、授权流程
##### 获取token
post 访问：
http://localhost:8080/oauth/token?grant_type=password&username=admin&password=123456&client_id=demoApp&client_secret=112233
请求类型：Content-Type：application/x-www-form-urlencoded【默认】
    
