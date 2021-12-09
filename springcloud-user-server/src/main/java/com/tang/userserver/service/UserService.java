package com.tang.userserver.service;

import com.tang.userserver.Dao.UserDao.UserInfo;
import com.tang.userserver.exception.UserApplicationException;
import com.tang.userserver.mapper.userMapper.UserMapper;
import com.tang.userserver.util.UsualUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author tangtiancong
 * @Date 2021/12/2:17:51
 */
@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        UserInfo userInfo = userMapper.getUser(username);
        if(UsualUtil.isEmpty(userInfo)){
            throw new UserApplicationException("username.is.null");
        }
        //目前数据库密码为明文
        List<GrantedAuthority> authorityList= new ArrayList<GrantedAuthority>();
        //给用户添加用户等级对应的权限
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+userInfo.getUserType()));
        logger.info("用户："+userInfo.getUsername()+"权限等级："+userInfo.getUserType());
        return new User(username,userInfo.getPassword(),authorityList);
    }
}
