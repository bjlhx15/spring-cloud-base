# spring-cloud-oauth2-jwt
## 一、概述
三个工程
    erueka-server：服务中心
    auth-service：负责授权，授权需要用户提供客户端的clientID和clientPassword，以及授权用户username和password。
        准备好以上信息后，auth-service返回jwt，该jwt包含了用户的基本信息和权限点信息，并且通过RSA加密。
    user-service：资源服务，已被保护，需要相应权限。
        user-service服务得到用户请求的jwt后，先通过公钥解密jwt，得到该jwt对应的用户信息和用户权限，在判断该用户是否有权限访问该资源。
  其中，在user-service服务的登录API接口（登录API接口不受保护）中，当用户名密码雁阵正确之后，通过远程调用向auth-service获取jwt.
    并返回jwt给用户。用户获取jwt后每次请求都要在请求头中传递该JWT，从而资源服务能够根据JWT类进行权限验证
```text

        +---------+                    
        |  浏览器  |                      
        |         |                    
        +---------+                    
           |  A                        
    1.登录  |  | 4.返回用户信息+jwtToken                         
uname pwd  |  |                        
           |  |
           V  |                        
        +---------+  2.clienid+uname+pwd    +---------+                               
        |  user-  |------------------------>|  auth-  |   
        | service |<------------------------| service |   
        +---------+  3.信息确认无误，返回JWT   +---------+   
             |                                   |        
             |            +---------+            |                                    
             |            | eureka- |            |        
             +----------->| server  |<-----------+         
                          +---------+                                                
                                                               
```               

### 1、创建三个服务
分别启动、eureka-server、auth-service、user-service
#### 直接访问数据接口
get http://localhost:9090/foo
```json
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
```
#### 注册用户
post http://localhost:9090/user/registry?username=lihongxu&password=123456
### 2、登录获取token
post http://localhost:9090/user/login?username=lihongxu&password=123456
注意程序内部设置请求头：Authorization ：Basic c2VydmljZS1oaToxMjM0NTY= 其中【Basic客户端凭证 service-hi：123456】
### 3、请求数据接口
get http://localhost:9090/foo
设置请求头：Authorization:Bearer {jwtToken}
    注意资源需要同时在资源系统以及授权系统设置资源ID