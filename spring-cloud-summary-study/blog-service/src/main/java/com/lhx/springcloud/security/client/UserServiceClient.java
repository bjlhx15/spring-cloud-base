package com.lhx.springcloud.security.client;

import com.lhx.springcloud.common.dto.RespDTO;
import com.lhx.springcloud.security.client.hystrix.UserServiceHystrix;
import com.lhx.springcloud.security.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:29
 */
@FeignClient(value = "user-service",fallback = UserServiceHystrix.class )
public interface UserServiceClient {

    @PostMapping(value = "/user/{username}")
    RespDTO<User> getUser(@RequestHeader(value = "Authorization") String token, @PathVariable("username") String username);
}
