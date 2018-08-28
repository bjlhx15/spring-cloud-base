package com.lhx.springcloud.provider.business.controller;

import com.lhx.springcloud.base.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/get")
    public User get(){
        System.err.println("服务提供方1提供服务");
        return new User("李宏旭",29);
    }
    @PostMapping("/save")
    public User save(User user){
        return user;
    }
}
