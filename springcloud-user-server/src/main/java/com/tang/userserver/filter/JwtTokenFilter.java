package com.tang.userserver.filter;

import com.tang.userserver.component.JwtTokenUtil;
import com.tang.userserver.configuration.SecurityIgnoreUrls;
import com.tang.userserver.exception.UserApplicationException;
import com.tang.userserver.util.ConstValue;
import com.tang.userserver.util.UsualUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author tangtiancong
 * @Date 2021/12/2:15:54
 */
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.token.head}")
    private String tokenHead;
    Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityIgnoreUrls securityIgnoreUrls;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        String tokenInfo = request.getHeader("token");
        for (String s:securityIgnoreUrls.getUrls()){
            if(s.contains("**")){
                s=s.substring(0,s.indexOf("**"));
                if(url.substring(0,s.length()).equals(s)) {
                    tokenInfo="";
                }
            }else{
                if(url.equals(s)) {
                    tokenInfo="";
                }
            }
        }
        //请求包含token且以tokenHead开头
        if(!UsualUtil.isEmpty(tokenInfo) && tokenInfo.startsWith(tokenHead) ){
            String token = tokenInfo.substring(tokenHead.length());
            Claims claims = jwtTokenUtil.getClaimsFromToken(token);
            if(!UsualUtil.isEmpty(claims)){
                String username= (String) claims.get(ConstValue.JWT_USER_NAME);
                if(!UsualUtil.isEmpty(username)
                        && UsualUtil.isEmpty(SecurityContextHolder.getContext().getAuthentication())){
                    UserDetails userDetails =userDetailsService.loadUserByUsername(username);
                    //验证token是否过期
                    if(!UsualUtil.isEmpty(userDetails) && jwtTokenUtil.verifyExpired(claims.getExpiration().getTime())){
                        //设置用户信息到 security认证框架
                        UsernamePasswordAuthenticationToken  authenticationToken =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        logger.info(userDetails.getUsername()+"token验证通过");
                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
