# spring-cloud-admin-study
    微服务监控学习项目
    spring boot admin用于管理和监控一个或者多个spring boot程序。
    spring boot admin分为Server端和Client端。client端可以通过http向Server端注册。
    也可以结合Spring cloud的服务注册组件Eureka进行注册。
    Spring boot Admin 提供了用 AngularJs编写的UI界面，用于管理和监控。
    其中监控内容包括spring boot的监控组件的actuator的各个http节点，也支持turbine、Jmx、loglevel等。  
    
参看地址：http://codecentric.github.io/spring-boot-admin/2.0.2/  
## spring-cloud-admin-base-http 基于http方式传递管理和监控项目
*    服务管理和监控，客户端直接使用http方式连接，传递监控信息。
## spring-cloud-admin-base-discovery  基于discovery服务发现方式传递管理和监控项目
