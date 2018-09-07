package com.lhx.springcloud.provider.business;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client04nutz")
public class Client04_nutz {
    private String url = "http://localhost:7901/user";

    @GetMapping("/testgetForObject")
    public String testgetForObject() {
        Response response = Http.get(url + "/get");
        return response.getContent();
    }


}
