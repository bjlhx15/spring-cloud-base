package com.lhx.springcloud.comsumer.business;

import com.lhx.springcloud.base.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserHystrixFallback implements UserServiceFeignClient {
    @Override
    public User get() {
        User u=new User();
        u.setAge(33);
        u.setName("fallback");
        return u;
    }
}
