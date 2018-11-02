# spring-cloud-oauth2-inmem-password
    简化模式（implicit grant type）该模式直接在浏览器中向认证服务器申请令牌，无需经过client端的服务器，跳过了"授权码"这个步骤，
    所有步骤在浏览器中完成，直接在回调url中传递令牌。
    
    适合直接在前端应用获取token的应用，步骤跟authorization code类似，只不过少了授权码
地址：https://tools.ietf.org/html/rfc6749#section-4.2
## 1、过程
```text
     +----------+
     | Resource |
     |  Owner   |
     |          |
     +----------+
          ^
          |
         (B)
     +----|-----+          Client Identifier     +---------------+
     |         -+----(A)-- & Redirection URI --->|               |
     |  User-   |                                | Authorization |
     |  Agent  -|----(B)-- User authenticates -->|     Server    |
     |          |                                |               |
     |          |<---(C)--- Redirection URI ----<|               |
     |          |          with Access Token     +---------------+
     |          |            in Fragment
     |          |                                +---------------+
     |          |----(D)--- Redirection URI ---->|   Web-Hosted  |
     |          |          without Fragment      |     Client    |
     |          |                                |    Resource   |
     |     (F)  |<---(E)------- Script ---------<|               |
     |          |                                +---------------+
     +-|--------+
       |    |
      (A)  (G) Access Token
       |    |
       ^    v
     +---------+
     |         |
     |  Client |
     |         |
     +---------+
```    
*（A）客户端将用户导向认证服务器。
*（B）用户决定是否给于客户端授权。
*（C）假设用户给予授权，认证服务器将用户导向客户端指定的"重定向URI"，并在URI的Hash部分包含了访问令牌。
*（D）浏览器向资源服务器发出请求，其中不包括上一步收到的Hash值。
*（E）资源服务器返回一个网页，其中包含的代码可以获取Hash值中的令牌。
*（F）浏览器执行上一步获得的脚本，提取出令牌。
*（G）浏览器将令牌发给客户端。  
## 2、步骤说明
A步骤中，客户端发出的HTTP请求，包含以下参数：
*  response_type：表示授权类型，此处的值固定为"token"，必选项。
*  client_id：表示客户端的ID，必选项。
*  redirect_uri：表示重定向的URI，可选项。
*  scope：表示权限范围，可选项。
*  state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。

C步骤中，认证服务器回应客户端的URI，包含以下参数：
*  access_token：表示访问令牌，必选项。
*  token_type：表示令牌类型，该值大小写不敏感，必选项。
*  expires_in：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
*  scope：表示权限范围，如果与客户端申请的范围一致，此项可省略。
*  state：如果客户端的请求中包含这个参数，认证服务器的回应也必须一模一样包含这个参数。  


## 3、启动项目
访问数据：http://localhost:8080/api/blog/1
没有访问权限
```json
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
```
##### 3.1、获取token以及回调
1、浏览器get 访问：
http://localhost:8080/oauth/authorize?response_type=token&client_id=demoApp&redirect_uri=http://localhost:8080/api/callback
请求类型：Content-Type：application/x-www-form-urlencoded【默认】  
2、会提示输入security的用户名密码：及admin：123456  
3、成功后会授权  
4、响应地址为上述请求的redirect_uri  
http://localhost:8080/api/callback#access_token=6f39edd4-0f91-4ad9-83ee-9203ca8bf8ba&token_type=bearer&expires_in=1001&scope=all
注意access_token前是#此时修改?即可使用如：  
http://localhost:8080/api/blog/1?access_token=6f39edd4-0f91-4ad9-83ee-9203ca8bf8ba&token_type=bearer&expires_in=1001&scope=all

