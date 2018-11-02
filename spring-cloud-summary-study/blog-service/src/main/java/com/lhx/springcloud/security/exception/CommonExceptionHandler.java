package com.lhx.springcloud.security.exception;

import com.lhx.springcloud.common.dto.RespDTO;
import com.lhx.springcloud.common.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:17
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<RespDTO> handleException(Exception e) {
        RespDTO resp = new RespDTO();
        CommonException taiChiException = (CommonException) e;
        resp.code = taiChiException.getCode();
        resp.error = e.getMessage();

        return new ResponseEntity(resp, HttpStatus.OK);
    }

}
