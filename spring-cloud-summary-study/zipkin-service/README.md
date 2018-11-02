# zipkin-service
      Zipkin 和 Config 结构类似，分为服务端 Server，客户端 Client，客户端就是各个微服务应用。
      [spring-boot2.0，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包] 

#### 方式一、zipkin下载使用【推荐】
  在 Spring Boot 2.0 版本之后，官方已不推荐自己搭建定制了，而是直接提供了编译好的 jar 包。  
  详情可以查看官网：https://zipkin.io/pages/quickstart.html 
*   终端方式：
```text
curl -sSL https://zipkin.io/quickstart.sh | bash -s
启动命令:java -jar zipkin.jar
```
*   docker方式
```text
docker run -d -p 9411:9411 openzipkin/zipkin
```
以上两种方式任一方式启动后，访问 http://localhost:9411，可以看到服务端已经搭建成功  

