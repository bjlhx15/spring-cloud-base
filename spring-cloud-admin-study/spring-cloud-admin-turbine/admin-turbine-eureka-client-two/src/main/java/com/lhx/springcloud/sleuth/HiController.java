package com.lhx.springcloud.sleuth;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/20 下午2:36
 */
@RestController
public class HiController {
    @GetMapping("/hi")
    @HystrixCommand(fallbackMethod ="hiError")
    public String home(@RequestParam String name){
        return "hi"+name;
    }
    public String hiError(){
        return "hystrix show error";
    }
}
