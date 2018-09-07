package com.lhx.springcloud.provider.business;

import com.lhx.springcloud.base.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class Client02_RestTemplete {
    @Autowired
    private RestTemplate restTemplate;
    private String url = "http://localhost:7901/user";

    @GetMapping("/testgetForObject")
    public String testgetForObject() {
        //1、getForObject
        String forObject = restTemplate.getForObject(url + "/get", String.class);
        System.out.println(forObject);
        User user = restTemplate.getForObject(url + "/get", User.class);
        System.out.println(user);
        //2、getForEntity
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/get", String.class);
        System.out.println(forEntity.getBody());

        ResponseEntity<User> forEntity1 = restTemplate.getForEntity(url + "/get", User.class);
        System.out.println(forEntity1.getBody());
        return forObject;
    }

    @PostMapping("/testpostForObject")
    public User testpostForObject(@RequestBody User user) {
        //1、postForObject
        String forObject = restTemplate.postForObject(url + "/create", user, String.class);
        System.out.println(forObject);
        User user2 = restTemplate.postForObject(url + "/create", user, User.class);
        System.out.println(user2);

        //2、postForEntity
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url + "/create", user, String.class);
        System.out.println(forEntity.getBody());
        ResponseEntity<User> forEntity1 = restTemplate.postForEntity(url + "/create", user, User.class);
        System.out.println(forEntity1.getBody());
        return user;
    }

    /**
     * 设置HTTP Header信息
     *
     * @return
     */
    @GetMapping("/testgetForHeader")
    public void testgetForHeader(User user) throws Exception {
        String uri = "";
        // 1-Content-Type
        RequestEntity<User> requestEntity1 = RequestEntity
                .post(new URI(uri))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
        // 2-Accept
        RequestEntity<User> requestEntity2 = RequestEntity
                .post(new URI(uri))
                .accept(MediaType.APPLICATION_JSON)
                .body(user);
        // 3-Other
        String base64Credentials = "";
        RequestEntity<User> requestEntity3 = RequestEntity
                .post(new URI(uri))
                .header("Authorization", "Basic " + base64Credentials)
                .body(user);
    }
}
