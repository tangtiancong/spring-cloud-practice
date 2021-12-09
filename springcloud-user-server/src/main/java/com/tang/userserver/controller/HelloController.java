package com.tang.userserver.controller;

import com.tang.userserver.Dao.UserDao.UserInfo;
import com.tang.userserver.exception.UserApplicationException;
import com.tang.userserver.mapper.userMapper.UserMapper;
import com.tang.userserver.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tangtiancong
 * @Date 2021/11/10:9:37
 */
@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${test.error}")
    private String test;

    /**
     * 无需登录即可访问
     * @return
     * @throws UserApplicationException
     */
    @RequestMapping("/common/hello")
    public ResultUtil sayHello() throws UserApplicationException {
        //获取认证过的用户信息
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        String name = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return ResultUtil.successResult("HELLO,WELCOME!");
    }

    /**
     * 需登录用户访问
     * @return
     * @throws UserApplicationException
     */
    @RequestMapping("/test/hello")
    public ResultUtil loginHello() throws UserApplicationException {
        //获取认证过的用户信息
        logger.info("测试："+test);
        Map resultMap = new HashMap();
        SecurityContext context =  SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        resultMap.put("name",authentication.getName());
        return ResultUtil.successResult("HELLO,test!");
    }
    @RequestMapping("/admin/hello")
    public ResultUtil adminTest() throws UserApplicationException {
        Map resultMap = new HashMap();
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        logger.info("测试："+authentication.getName() +"---"+authentication.getAuthorities());
        return ResultUtil.successResult("HELLO,admin!");
    }
}
