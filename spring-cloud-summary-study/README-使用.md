# spring-cloud-summary-study
    综合项目
## 1.1、依次启动
* eureka-server、config-server
    启动main方法
查看：http://localhost:8761/
* zipkin jar
    下载jar包，本地启动。默认使用http方式
```text
java -jar zipkin-server-2.11.7-exec.jar
```     
查看：http://localhost:9411/zipkin/
再启动其他服务
    
