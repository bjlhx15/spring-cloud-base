启动 rabbitmq
    http://localhost:15672/#/    
启动erueka-erver
    http://localhost:8761/
启动config-server
启动两个config-client实例
    http://localhost:8762/foo
    http://localhost:8764/foo
修改git配置
    更新配置中心：http://localhost:8769/bus/refresh    
    请求刷新的页面由原来1.5.x的localhost:8888/bus/refresh
    变成 post ：http://localhost:8888/actuator/bus-refresh
    注意：config-server和config-client的配置都得加上
    
     management:
      endpoints:
        web:
          exposure:
            include: bus-refresh