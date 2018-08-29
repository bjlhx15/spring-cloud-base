# spring-cloud-base
# 基础项目
## 通用实体项目
*    base-model
## 服务注册中心
*    discovery-eureka-single：单节点 无认证
*    discovery-eureka-single-security：单节点 有认证
*    discovery-eureka-ha1、discovery-eureka-ha2：多节点 无认证
*    discovery-eureka-ha-security1、discovery-eureka-ha-security2：多节点 有认证【后续使用】
## 服务提供方
*    provider-business-service1、provider-business-service1：多节点 有认证【后续使用】 
*    provider-business-service1-without-eureka、    
      provider-business-service2-without-eureka:  有认证，没有Eureka注册中心
## 服务消费方
*    comsumer-business-service1-org：有认证 RestTemplate方式调用
*    comsumer-business-service1-ribbon：有认证 RestTemplate方式调用，并且使用ribbon进行负载均衡基础使用
*    comsumer-business-service3-ribbon-without-eureka、  
      provider-business-service1-without-eureka、    
      provider-business-service2-without-eureka  
    有认证 没有使用注册中心 RestTemplate方式调用，并且使用ribbon进行负载均衡基础使用，  
    注意此时负载均衡不能加载RestTemplete上
