package com.lhx.springcloud.security.client.hystrix;

import com.lhx.springcloud.common.dto.RespDTO;
import com.lhx.springcloud.security.client.UserServiceClient;
import com.lhx.springcloud.security.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:30
 */
@Component
public class UserServiceHystrix implements UserServiceClient {

    @Override
    public RespDTO<User> getUser(String token, String username) {
        System.out.println(token);
        System.out.println(username);
        return null;
    }
}
