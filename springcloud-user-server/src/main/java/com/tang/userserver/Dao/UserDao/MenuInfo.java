package com.tang.userserver.Dao.UserDao;

import java.io.Serializable;

/**
 * @Author tangtiancong
 * @Date 2021/11/18:15:31
 */
public class MenuInfo implements Serializable {
    /**
     * 菜单id
     */
    private String menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单父id
     */
    private String menuParentId;
    /**
     * 菜单等级
     */
    private String menuLevel;
}
