package com.lhx.springcloud.sleuth;

import com.lhx.springcloud.sleuth.filter.LoggerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/12 下午6:20
 */

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    @Bean
    public LoggerFilter createLoggerFilter(){
        return new LoggerFilter();
    }
}
