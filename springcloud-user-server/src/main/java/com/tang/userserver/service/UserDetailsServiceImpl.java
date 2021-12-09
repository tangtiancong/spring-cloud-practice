package com.tang.userserver.service;
import com.tang.userserver.Dao.UserDao.MenuInfo;
import com.tang.userserver.Dao.UserDao.UserInfo;
import com.tang.userserver.exception.UserApplicationException;
import com.tang.userserver.mapper.userMapper.UserMapper;
import com.tang.userserver.util.UsualUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现springSecurity框架自行验密（需放开@Service）
 * @Author tangtiancong
 * @Date 2021/11/15:14:59
 */
//@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //获取用户信息
        UserInfo userInfo = userMapper.getUser(username);
        if(UsualUtil.isEmpty(userInfo)){
            throw new UsernameNotFoundException("username.is.null",new Throwable("试试"));
        }
        List <GrantedAuthority> authorityList= new ArrayList<GrantedAuthority>();
        //给用户添加用户等级对应的权限
        authorityList.add(new SimpleGrantedAuthority(userInfo.getUserType()));
        //实验hasRole方法
        authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        List<MenuInfo> menuList =new ArrayList<MenuInfo>();
        //返回User交由UsernamePasswordAuthenticationFilter进行验证
        return new User(username,passwordEncoder.encode(userInfo.getPassword()),authorityList);
    }
}
