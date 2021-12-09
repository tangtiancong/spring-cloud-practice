package com.tang.userserver.component;

import com.tang.userserver.exception.UserApplicationException;
import com.tang.userserver.util.ConstValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tangtiancong
 * @Date 2021/12/2:15:43
 */
@Component
public class JwtTokenUtil {
    @Value("${jwt.token.expired.time}")
    private long expired;
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.head}")
    private String tokenHead;
    Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+expired*1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ConstValue.JWT_USER_NAME, userDetails.getUsername());
        claims.put(ConstValue.TOKEN_CREATE_TIME, new Date());
        claims.put(ConstValue.JWT_SUB,"jwtToken");
        return generateToken(claims);
    }
    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            logger.info(claims.getSubject());
        } catch (Exception e) {
            logger.info("JWT格式验证失败:{}", token);
            return null;
        }
        return claims;
    }
    public boolean verifyExpired(long expiredTime){
        long current = System.currentTimeMillis();
        if(current > expiredTime){
           return false;
        }
        return true;
    }
}
