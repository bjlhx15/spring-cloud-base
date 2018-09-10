package com.lhx.springcloud.provider.business.controller;

import com.lhx.springcloud.base.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/get")
    public User get() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        for (int i = 0; i < 1000; i++) {
            logger.info("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        }
        return new User("李宏旭1", 29);
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return user;
    }
}
