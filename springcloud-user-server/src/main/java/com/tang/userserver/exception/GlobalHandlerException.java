package com.tang.userserver.exception;

import com.tang.userserver.util.ConstValue;
import com.tang.userserver.util.ResultUtil;
import com.tang.userserver.util.UsualUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局自定义异常处理
 * @Author tangtiancong
 * @Date 2021/11/23:14:38
 */
@ControllerAdvice
@ResponseBody
public class GlobalHandlerException {

    @Autowired
    private MessageSource messageSource;
    /**
     * 自定义异常处理,继承优先级需低于Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(UserApplicationException.class)
    public ResultUtil userApplicationExceptionResult(UserApplicationException ex){
        String msg = ex.getMsg();
        if(UsualUtil.isEmpty(msg)){
            msg= messageSource.getMessage(ex.getCode(), null,ConstValue.UNKNOW_MSG,null);
        }
        return  ResultUtil.failResult(ex.getCode(),msg);
    }

    /**
     * Exception异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultUtil expectionResult(Exception ex){
        return  ResultUtil.failResult(ConstValue.EXCEPTION_CODE,ex.getMessage());
    }
}
