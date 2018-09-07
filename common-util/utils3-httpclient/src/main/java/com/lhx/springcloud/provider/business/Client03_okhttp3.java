package com.lhx.springcloud.provider.business;

import com.lhx.springcloud.base.model.User;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("client03okhttp3")
public class Client03_okhttp3 {
    private String url = "http://localhost:7901/user";

    @GetMapping("/testgetForObject")
    public String testgetForObject() {
        String s = OkHttpUtil.get(url + "/get", null);
        return s;
    }


}
