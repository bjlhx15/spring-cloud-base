package com.lhx.springcloud.comsumer.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test")
    public Map.Entry<String,String> test(){
        return new AbstractMap.SimpleEntry("test","test");
    }
}
