package com.tang.userserver.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author tangtiancong
 * @Date 2021/11/18:15:48
 */
public class UsualUtil {
    public static boolean isEmpty(Object obj){
        if(obj == null){
            return true;
        }
        if(obj instanceof String && String.valueOf(obj).isEmpty()){
            return true;
        }
        if(obj instanceof List && ((List)obj).size()<1){
            return true;
        }
        if(obj instanceof Map && ((Map)obj).isEmpty()){
            return true;
        }
        return false;
    }
}
