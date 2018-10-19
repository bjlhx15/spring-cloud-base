package com.lhx.springcloud.sleuth.filter;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.context.log4j2.ThreadContextCurrentTraceContext;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/14 下午4:19
 */
public class LoggerFilter implements GlobalFilter, Ordered {
    @Autowired
    private Tracer tracer;
    Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Autowired
    Reporter reporter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.error("测试");

        Span textssss = tracer.currentSpan().start();
        System.err.println(reporter);
        System.err.println(tracer);
//        logger.warn(reporter);
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    System.out.println("请求执行");
                    textssss.finish();
                })
        );
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
