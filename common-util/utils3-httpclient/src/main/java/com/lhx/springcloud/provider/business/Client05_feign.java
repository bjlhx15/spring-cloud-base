package com.lhx.springcloud.provider.business;

import com.lhx.springcloud.base.model.User;
import com.lhx.springcloud.provider.business.service.UserFeignService;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client05feign")
public class Client05_feign {
    @Autowired
    private UserFeignService userFeignService;

    @GetMapping("/testgetForObject")
    public String testgetForObject() {
        User user = userFeignService.get();
        return user.toString();
    }


}
