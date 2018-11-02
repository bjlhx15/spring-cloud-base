package com.lhx.springcloud.security.client.hystrix;

import com.lhx.springcloud.security.client.AuthServiceClient;
import com.lhx.springcloud.security.domain.JWT;
import org.springframework.stereotype.Component;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/31 下午5:39
 */
@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        System.err.println("AuthServiceHystrix执行了");
        return null;
    }
}
