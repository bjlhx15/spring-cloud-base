package com.lhx.springcloud.provider.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
public class BusinessServiceApplication7901 {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BusinessServiceApplication7901.class, args);

        System.out.println(context.getBean(DataSource.class));
    }
}
