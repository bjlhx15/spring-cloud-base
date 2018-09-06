package com.lhx.springcloud.provider.business.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhx.springcloud.base.model.User;
import com.lhx.springcloud.provider.business.po.UserStudyVo;
import com.lhx.springcloud.provider.business.po.auto.UserStudy;
import com.lhx.springcloud.provider.business.service.IUserStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserStudyService service;
    @GetMapping("/get")
    public User get(){
        System.err.println("服务提供方1提供服务");
        return new User("李宏旭",29);
    }
    @PostMapping("/save")
    public User save(User user){
        return user;
    }


    @PostMapping("/create")
    public int create(@RequestBody UserStudy record) throws Exception {
        return service.insert(record);
    }
    @GetMapping("/findOne")
    public UserStudy findOne(int id) throws Exception {
        return service.findOne(id);
    }

    @PostMapping("/getList")
    @ResponseBody
    public PageInfo getList(@RequestBody  UserStudyVo bean) throws Exception {
        PageHelper.startPage(1,2);
        List<UserStudy> list = service.getList(bean.getRecord());
        PageInfo p = new PageInfo(list);
        return p;
    }
}
