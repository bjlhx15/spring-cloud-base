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
*   comsumer-business-service4-feign     有认证 feign方式调用
*   comsumer-business-service5-feign-hystrix     有认证 feign方式调用 增加熔断机制
*   comsumer-business-service6-feign-hystrix-factory    有认证 ribbon 方式调用 增加熔断机制 


## service-db 数据库操作项目
### service-mysql-mybatis-c3p0  
1.mybatis generate plugin 逆向工程   
&nbsp;&nbsp;1.1.添加mybatis generate plugin插件  
    ```
        <build>
            <plugins>
                <plugin>
                    <!--
                    用maven mybatis插件
                    在plugin里面添加依赖包得引用
                    -->
                    <groupId>org.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-maven-plugin</artifactId>
                    <version>1.3.5</version>
                    <!-- mybatis用于生成代码的配置文件 -->
                    <configuration>
                        <!-- 配置文件路径　-->
                        <configurationFile>src/main/resources/generator/generatorConfig.xml</configurationFile>
                        <verbose>true</verbose>
                        <overwrite>true</overwrite>
                    </configuration>
                    <dependencies>
                        <dependency>
                            <groupId>log4j</groupId>
                            <artifactId>log4j</artifactId>
                            <version>1.2.17</version>
                        </dependency>
                        <dependency>
                            <groupId>org.mybatis</groupId>
                            <artifactId>mybatis</artifactId>
                            <version>3.4.5</version>
                        </dependency>
                        <dependency>
                            <groupId>mysql</groupId>
                            <artifactId>mysql-connector-java</artifactId>
                            <version>5.1.41</version>
                        </dependency>
                    </dependencies>
                </plugin>
            </plugins>
        </build>
    ```
&nbsp;&nbsp;1.2.添加generatorConfig文件  
&nbsp;&nbsp;1.3.运行 mybatis-generator:generate 即可  
2.mysql mybatis c3p0 的pom配置
```$xslt
        <!-- 添加数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- 添加对 mybatis 的依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>
        <!-- 添加对 JDBC 的支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!-- 添加 c3p0 数据源 -->
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.1.2</version>
        </dependency>
```
3.application.xml配置
```$xslt
# 设置数据库相关
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springboot
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.max-idle=10
spring.datasource.max-wait=10000
spring.datasource.min-idle=5
spring.datasource.initial-size=5

# 设置 c3p0 数据源
spring.datasource.type=com.mchange.v2.c3p0.ComboPooledDataSource

# 方式一、设置 MyBatis mapper 的别名所在的包
mybatis.type-aliases-package=com.lhx.springcloud.provider.business.mapper
# 设置 mapper 接口对应 XMl 配置文件的路径
mybatis.mapper-locations=classpath:mappers/auto/*.xml
```
4.启动类配置
```$xslt
@MapperScan("com.lhx.springcloud.provider.business.mapper")//将项目中对应的mapper类的路径加进来
```