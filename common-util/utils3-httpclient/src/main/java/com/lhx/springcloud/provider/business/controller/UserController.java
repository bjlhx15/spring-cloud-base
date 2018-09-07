package com.lhx.springcloud.provider.business.controller;

import com.lhx.springcloud.base.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/get")
    public User get(){
        return new User("李宏旭1",29);
    }

    @PostMapping("/create")
    public User create(@RequestBody User user){
        return user;
    }
}
