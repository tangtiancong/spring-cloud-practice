package com.tang.userserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author tangtiancong
 * @Date 2021/11/23:13:56
 */
public class ResultUtil<T> {
    private static Logger logger = LoggerFactory.getLogger(ResultUtil.class);
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultUtil(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultUtil(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * 返回成功
     * @param data
     * @return
     */
    public static <T> ResultUtil<T> successResult(T data){
        ResultUtil resultUtil = new  ResultUtil(ConstValue.SUCCESS_CODE,ConstValue.SUCCESS_MSG,data);
        logger.info("返回报文："+resultUtil.toString());
        return resultUtil;
    }
    /**
     * 返回失败
     * @param code
     * @param msg
     * @return
     */
    public static ResultUtil failResult(String code, String msg){
        ResultUtil resultUtil =  new ResultUtil(code,msg);
        logger.info("返回报文："+resultUtil.toString());
        return resultUtil;
    }

    @Override
    public String toString() {
        return "ResultUtil{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
