package com.lhx.springcloud.summary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SummaryEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SummaryEurekaServerApplication.class, args);
    }
}
