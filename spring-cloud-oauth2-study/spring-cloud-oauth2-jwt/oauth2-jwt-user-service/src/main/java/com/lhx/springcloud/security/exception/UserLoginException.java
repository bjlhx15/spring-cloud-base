package com.lhx.springcloud.security.exception;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/31 下午5:33
 */
public class UserLoginException extends RuntimeException{

    public UserLoginException(String message) {
        super(message);
    }

}