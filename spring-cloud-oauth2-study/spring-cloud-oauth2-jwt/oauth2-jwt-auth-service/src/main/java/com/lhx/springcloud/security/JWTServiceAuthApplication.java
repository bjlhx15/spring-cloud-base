package com.lhx.springcloud.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JWTServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(JWTServiceAuthApplication.class, args);
    }

}
