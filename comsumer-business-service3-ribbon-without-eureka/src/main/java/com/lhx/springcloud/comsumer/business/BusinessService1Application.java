package com.lhx.springcloud.comsumer.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BusinessService1Application {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthorization("admin", "111111").build();
    }
    @Primary
    @Bean
    public RestTemplate restTemplateNoLoadBalanced(RestTemplateBuilder builder) {
        return builder.basicAuthorization("admin", "111111").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BusinessService1Application.class, args);
    }
}
