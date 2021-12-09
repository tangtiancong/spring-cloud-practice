package com.tang.userserver.mapper.userMapper;

import com.tang.userserver.Dao.UserDao.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @Author tangtiancong
 * @Date 2021/11/18:15:14
 */
@Repository
public interface UserMapper{
     UserInfo getUser(String username);
     Integer setUser(UserInfo userInfo);
}
