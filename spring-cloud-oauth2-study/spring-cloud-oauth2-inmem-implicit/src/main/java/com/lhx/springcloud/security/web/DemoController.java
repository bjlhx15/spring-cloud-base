package com.lhx.springcloud.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/22 下午5:45
 */
@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/blog/{id}")
    public String getBlogById(@PathVariable long id) {
        return "this is blog "+id;
    }

    @GetMapping("/callback")
    public String callback() {
        return "this is callback ";
    }


}
