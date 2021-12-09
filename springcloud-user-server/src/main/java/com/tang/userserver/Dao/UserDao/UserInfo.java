package com.tang.userserver.Dao.UserDao;

import java.io.Serializable;

/**
 * @Author tangtiancong
 * @Date 2021/11/18:15:16
 */
public class UserInfo implements Serializable {
    private String username;
    private String password;
    private String userType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
