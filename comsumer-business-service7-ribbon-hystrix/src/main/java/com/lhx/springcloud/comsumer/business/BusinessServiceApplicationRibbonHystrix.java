package com.lhx.springcloud.comsumer.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
public class BusinessServiceApplicationRibbonHystrix {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthorization("admin", "111111").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplicationRibbonHystrix.class, args);
    }
}
