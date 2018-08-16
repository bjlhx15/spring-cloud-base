package com.lhx.springcloud.comsumer.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BusinessService1Application {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthorization("admin", "111111").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BusinessService1Application.class, args);
    }
}
