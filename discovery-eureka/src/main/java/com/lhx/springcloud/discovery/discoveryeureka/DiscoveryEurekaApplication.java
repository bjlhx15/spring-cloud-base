package com.lhx.springcloud.discovery.discoveryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryEurekaApplication.class, args);
    }
}
