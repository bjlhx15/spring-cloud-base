package com.lhx.springcloud.comsumer.business;

import com.lhx.springcloud.base.model.User;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserHystrixFallbackFactory implements FallbackFactory<UserServiceFeignClient> {

    @Override
    public UserServiceFeignClient create(Throwable throwable) {
        return new UserServiceFeignClient() {
            @Override
            public User get() {
                User u=new User();
                u.setAge(33);
                u.setName("fallback");
                return u;
            }
        };
    }
}
