package com.lhx.springcloud.security.service;

import com.alibaba.fastjson.JSON;
import com.lhx.springcloud.security.config.RabbitConfig;
import com.lhx.springcloud.security.domain.SysLog;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午4:34
 */
@Service
public class LoggerService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void log(SysLog sysLog){
        rabbitTemplate.convertAndSend(RabbitConfig.queueName, JSON.toJSONString(sysLog));
    }
}
