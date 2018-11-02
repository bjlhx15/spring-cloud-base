# spring-cloud-oauth2-jdbc
## 一、概述
    使用数据库方式

### 1、创建三个服务
分别启动、eureka-server、auth-service、client-service
#### 1、1、注册用户
post http://localhost:8762/user/registry?username=lihongxu&password=112233
### 2、获取token
post http://localhost:5000/uaa/oauth/token?grant_type=password&username=miya&password=123456
注意设置请求头：Authorization ：Basic c2VydmljZS1oaToxMjM0NTY= 其中【Basic客户端凭证 service-hi：123456】
此时会得到token
### 3、请求数据接口
get http://localhost:8762/hi?access_token=c07894a1-37d7-4d4d-8883-843ea89fa33b

### 4、访问需要授权数据
```text
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    @RequestMapping("/hello")
    public String hello (){
        return "hello you!";
    }
```
get http://localhost:8762/hello?access_token=9cb59efb-582a-44d1-89fe-f0394fb59b23
```json
{
    "error": "access_denied",
    "error_description": "不允许访问"
}
```  
设置了ROLE_ADMIN权限 但是没有配置此权限，数据库中增加
```sql
insert into 'role' values('1','ROLE_USER'),('2','ROLE_ADMIN');
insert into 'user_role' values('7','2');
```  
重新获取token 后执行
get http://localhost:8762/hello?access_token=9cb59efb-582a-44d1-89fe-f0394fb59b23