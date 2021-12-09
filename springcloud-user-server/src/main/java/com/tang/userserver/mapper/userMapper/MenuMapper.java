package com.tang.userserver.mapper.userMapper;

import com.tang.userserver.Dao.UserDao.MenuInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author tangtiancong
 * @Date 2021/11/18:16:15
 */
@Repository
public interface MenuMapper {
     List<MenuInfo> getMenuInfo(String level);
}
