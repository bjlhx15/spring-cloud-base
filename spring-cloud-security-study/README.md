# spring-cloud-security-study
## 一、概述
    程序安全防护学习项目
    Spring security采用安全层概念，使每一层尽可能安全。
    Spring security可以在Controller层、Service层、DAO层等以加注解的方式来保护应用程序。
    提供了细粒度的权限控制，可以精细到每一个API接口、每一个业务的方法，或者每一个操作数据库的DAO层的方法。
## 二、使用原因    
    依赖低、耦合低、易于集成
    shiro一般使用在单体服务
    security良好支持微服务  
## 三、安全模块
    HTTP BASIC authentication headers (一个基于IEFT  RFC 的标准)    
    HTTP Digest authentication headers (一个基于IEFT  RFC 的标准)    
    HTTP X.509 client certificate exchange  (一个基于IEFT RFC 的标准)    
    LDAP (一个非常常见的跨平台认证需要做法,特别是在大环境)    
    Form-based authentication (基于表单的验证)    
    OpenID authentication (OpenID验证)   
    基于预先建立的请求头的验证    
    JA-SIG Central Authentication Service  (CAS,开源单点登录系统)
    远程方法调用(RMI)和HttpInvoker(Spring远程协议)的认证
    自动"记住我"的身份验证
    匿名验证（允许每一次未经身份验证的调用）
    Run-as身份验证（每一次调用都需要提供身份标识）
    java认证和授权服务
    javaEE容器认证
    kerberos
    ……
## 四、Spring boot Security和Spring security关系
   Spring security的关键依赖
```xml
  <dependencies>
    <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-config</artifactId>
      <version>5.0.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-web</artifactId>
      <version>5.0.7.RELEASE</version>
    </dependency>
  </dependencies>
```        
   Spring boot security只是对Spring security的封装并且增加aop，以及starter
    
    
