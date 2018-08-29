package com.lhx.springcloud.comsumer.business.controller;

import com.lhx.springcloud.base.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/testUser")
public class TestUserController {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplate restTemplateNoLoad;
    @Autowired
    private LoadBalancerClient loadBalancer;

    /**
     * 方式一、使用ribbon客户端负载，restTemplateNoLoad使用没有负载的
     * @return
     */
    @GetMapping("/get")
    public User get(){
        ServiceInstance instance = loadBalancer.choose("provider-business-service");
        URI storesUri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
        return restTemplateNoLoad.getForObject(storesUri+"/user/get", User.class);
    }
    /**
     * 方式二、不可行，使用ribbon客户端负载，restTemplate带有负载的，此时的restTemplate使用ribbon的负载器需要提供serviceid
     * 但是 因为没有在eureka上注册所以没有serviceID
     * @return
     */
    @GetMapping("/get2")
    public User get2(){
        return restTemplate.getForObject("http://provider-business-service-without-eureka/user/get", User.class);
    }
}
