# spring-cloud-summary-study
    综合项目
## 1.1、模块简介    
* eureka-server：注册中心  
    维护可一份服务注册表，每个服务都可以获取服务注册表，获取的服务注册表可用于Ribbon的负载均衡，也可以用于网关路由。
* config-server：配置中心      
    配置中心，所有服务的配置文件有config-server统一管理，config-server可以从远程git仓库读取，也可以从本地仓库读取。
    如果将配置文件放在远程仓库，配合spring cloud bus，可以在不人工重启服务的情况下，进行全局服务配置刷新。
* uaa-service：授权中心uaa服务  
    集成了spring cloud oauth2，服务统一授权，并返回token。其他的应用服务，如user-service何blog-service作为资源服务，他们的api接口是受保护的。需要token校验，并鉴权后才能访问。
* monitoring-service：turbine聚合监控服务熔断器  
* zipkin-service：链路追踪服务  【使用jar】
    是spring sleuth的组件，他可以查看每个请求在微服务系统的链路关系。
* admin-service：聚合监控服务微服务  
    是一个集成了spring boot admin server，提供了非常强大的服务监控能力，可以查看向eureka-server注册的服务的健康状态、日志、注册时间线等。
* gateway-service：路由网关服务  
    网关服务，使用的是spring cloud gateway组件，智能路由、负载均衡等。网关服务是一个边界服务，对外统一暴露api接口。其他服务api接口只提供给内部调用
    不能提供给外界直接调用，便于实现统一鉴权，安全验证等功能。
* log-service：日志服务  
    应用服务通过rabbitmq向log-service发送业务日志，后续可以使用ELK。
* user-service：资源服务【用户服务】  
* blog-service：资源服务【博客服务】  
* common：工具类
* sql：sql文件
* logs：日志
* respo：配置文件
## 1.2、技术点
spring cloud netflix
* eureka：服务注册和发现
* hystrix：熔断器
* hystrix dashboard：熔断器仪表盘，用于监控熔断器的状况
* turbine：聚合多个Hystrix Dashboard
* Ribbon：负载均衡器
其他spring cloud组件
* spring cloud config：分布式配置中心
* spring cloud oauth2：包括spring oauth2和spring boot security，为微服务提供一整套的安全解决方案
* feign：声明式服务调用
* spring cloud sleuth：集成zipkin，用于服务链路追踪
* spring boot admin：聚合监控微服务状况
* spring cloud gateway：服务网关，用于服务智能路由，负载均衡等
* spring data jpa：数据库采用mysql，实体对象持久化jpa
* swagger2：api接口文档组件
restful api：接口采用restful风格
rabbitmq：消息服务，用于发送日志消息






