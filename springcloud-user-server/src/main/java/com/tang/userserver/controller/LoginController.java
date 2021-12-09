package com.tang.userserver.controller;

import com.tang.userserver.Dao.UserDao.UserInfo;
import com.tang.userserver.exception.UserApplicationException;
import com.tang.userserver.mapper.userMapper.UserMapper;
import com.tang.userserver.component.JwtTokenUtil;
import com.tang.userserver.util.ResultUtil;
import com.tang.userserver.util.UsualUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author tangtiancong
 * @Date 2021/11/18:15:24
 */
@RestController
@RequestMapping("/user")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${jwt.token.head}")
    private String tokenHead;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserDetailsService userDetailsService;
    /**
     * 登陆交易 核心类UserServiceImpl实现UserDetailsService
     * @return
     */
    @RequestMapping("/login")
    public ResultUtil login(@RequestBody Map<String,String> req){
        Map resultMap = new HashMap();
        UserDetails userDetails =userDetailsService.loadUserByUsername(req.get("username"));
        this.logger.info("上送密码信息:"+req.get("password"));
        this.logger.info("数据库密码信息:"+userDetails.getPassword());
        String pwd = req.get("password");
        this.logger.info("上送密码加密信息:"+pwd);
        //校验密码
        if(!passwordEncoder.matches(pwd,userDetails.getPassword())){
            throw new UserApplicationException("user.password.error");
        }
        //校验成功加入用户信息到上下文
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        resultMap.put("token",token);
        resultMap.put("tokenHead",tokenHead);
        return ResultUtil.successResult(resultMap);
    }

    @RequestMapping("/register")
    public ResultUtil register(@RequestBody Map<String,String> req){
        Map resultMap = new HashMap();
        //获取用户信息
        String username = req.get("username");
        UserInfo userInfo = userMapper.getUser(username);
        //用户名是否已经存在
        if(!UsualUtil.isEmpty(userInfo)){
            throw new UserApplicationException("username.have.already.registered");
        }
        String password = passwordEncoder.encode(req.get("password"));
        String usertype = req.get("usertype");
        userInfo = new UserInfo();
        userInfo.setPassword(password);
        userInfo.setUsername(username);
        userInfo.setUserType(usertype);
        userMapper.setUser(userInfo);
        return ResultUtil.successResult(resultMap);
    }

    /**
     * springsecurity认证失败处理。核心类AbstractAuthenticationProcessingFilter
     * @param request
     * @return
     */
    @RequestMapping("/common/error")
    public ResultUtil loginError(HttpServletRequest request) {
        AuthenticationException exception =
                (AuthenticationException)request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        throw new UserApplicationException("user.error",exception.getMessage());
    }
}
