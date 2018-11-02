package com.lhx.springcloud.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/31 下午5:33
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<String> handleException(Exception e) {

        return new ResponseEntity(e.getMessage(), HttpStatus.OK);
    }
}
