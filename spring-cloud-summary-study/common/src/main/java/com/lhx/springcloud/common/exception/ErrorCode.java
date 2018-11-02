package com.lhx.springcloud.common.exception;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午4:56
 */
public enum ErrorCode {

    OK(2000, ""),
    FAIL(-1, "操作失败"),
    RPC_ERROR(-2,"远程调度失败"),
    USER_NOT_FOUND(10000,"用户不存在"),
    USER_PASSWORD_ERROR(10001,"密码错误"),
    GET_TOKEN_FAIL(10002,"获取token失败"),
    TOKEN_IS_NOT_MATCH_USER(10003,"请使用自己的token进行接口请求"),

    BLOG_IS_NOT_EXIST(20001,"该博客不存在")
    ;
    private int code;
    private String msg;


    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ErrorCode codeOf(int code) {
        for (ErrorCode state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        return null;
    }
}
