package com.lhx.springcloud.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/22 下午5:45
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "ok";
    }
}
