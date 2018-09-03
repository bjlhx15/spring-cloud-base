package com.lhx.springcloud.comsumer.business;

import com.lhx.springcloud.base.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="provider-business-service",configuration = ConfigurationBasicAuth.class,fallback = UserHystrixFallback.class)
public interface UserServiceFeignClient {
    @RequestMapping(method = RequestMethod.GET,value = "/user/get")
    User get();
}
