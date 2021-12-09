package com.tang.userserver.exception;


/**
 * 自定义异常类
 * @Author tangtiancong
 * @Date 2021/11/23:14:41
 */
public class UserApplicationException extends  RuntimeException{
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserApplicationException(String code){
        this.code = code;
    }
    public UserApplicationException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
