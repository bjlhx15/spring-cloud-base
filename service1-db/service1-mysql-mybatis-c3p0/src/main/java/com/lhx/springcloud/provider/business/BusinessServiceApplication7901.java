package com.lhx.springcloud.provider.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lhx.springcloud.provider.business.mapper")//将项目中对应的mapper类的路径加进来
public class BusinessServiceApplication7901 {
    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication7901.class, args);
    }
}
