package com.lhx.springcloud.security.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.lhx.springcloud.security.domain.SysLog;
import com.lhx.springcloud.security.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:42
 */
@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    SysLogService sysLogService;
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        SysLog sysLog=  JSON.parseObject(message,SysLog.class);
        sysLogService.saveLogger(sysLog);
        latch.countDown();
    }


}
