package com.lhx.springcloud.comsumer.business.controller;

import com.lhx.springcloud.base.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/testUser")
public class TestUserController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.userServicePath}")
    private String userServicePath;
    @GetMapping("/get")
    @HystrixCommand(fallbackMethod = "getFallback")
    public User get(){
        return restTemplate.getForObject(userServicePath+"/get", User.class);
    }
    public User getFallback() {
        User user = new User();
        user.setAge(0);
        user.setName("fallback");
        return user;
    }
}
