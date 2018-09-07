package com.lhx.springcloud.provider.business.service;

import com.lhx.springcloud.base.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(url = "http://localhost:7901/user",name="user")
public interface UserFeignService {
    @RequestMapping(value="/get",method= RequestMethod.GET)
    User get();
}
