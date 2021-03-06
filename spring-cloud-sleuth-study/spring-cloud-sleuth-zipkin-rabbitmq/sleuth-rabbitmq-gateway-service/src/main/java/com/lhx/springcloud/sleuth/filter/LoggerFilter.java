package com.lhx.springcloud.sleuth.filter;

import brave.Tracer;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/14 下午4:19
 */
public class LoggerFilter implements GlobalFilter, Ordered {
    @Autowired
    private Tracer tracer;
    Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.error("测试");

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    System.out.println("请求执行");
                })
        );
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
