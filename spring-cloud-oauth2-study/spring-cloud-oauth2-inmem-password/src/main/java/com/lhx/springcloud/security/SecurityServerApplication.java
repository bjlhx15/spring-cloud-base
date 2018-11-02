package com.lhx.springcloud.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
public class SecurityServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }
}
