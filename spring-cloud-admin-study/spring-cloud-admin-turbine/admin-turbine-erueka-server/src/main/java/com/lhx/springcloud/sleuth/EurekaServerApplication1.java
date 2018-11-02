package com.lhx.springcloud.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication1 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication1.class, args);
    }
}
