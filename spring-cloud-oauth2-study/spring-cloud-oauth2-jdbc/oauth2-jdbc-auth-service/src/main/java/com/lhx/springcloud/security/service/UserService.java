package com.lhx.springcloud.security.service;


import com.lhx.springcloud.security.domain.User;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/25 下午5:21
 */
public interface UserService {
    void create(User user);
}