package com.lhx.springcloud.comsumer.business.controller;

import com.lhx.springcloud.base.model.User;
import com.lhx.springcloud.comsumer.business.UserServiceFeignClient;
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
    private UserServiceFeignClient userServiceFeignClient;

    @GetMapping("/get")
    public User get() {
        return userServiceFeignClient.get();
    }
}
