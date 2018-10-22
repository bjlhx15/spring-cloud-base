package com.lhx.springcloud.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/15 上午9:31
 */
@SpringBootApplication
@EnableTurbine
public class TurbineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineServiceApplication.class, args);
    }
}
