package com.lhx.springcloud.sleuth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/15 上午9:33
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hi")
    public Mono<String> hi(String aa) throws InterruptedException {
        Thread.sleep(100);
        return Mono.just("ok"+aa);
    }
    @GetMapping("/hi2")
    public Mono<String> hi2() throws InterruptedException {
        throw new RuntimeException("mingming sha");
        //return Mono.just("ok");
    }
}
