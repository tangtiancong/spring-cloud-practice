package com.tang.userserver.component;

import cn.hutool.json.JSONUtil;
import com.tang.userserver.util.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.AccessException;

/**
 * security访问权限不足
 * @Author tangtiancong
 * @Date 2021/12/6:18:19
 */
@Component
public class SecurityAccessDeniedHandler  implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Cache-Control","no-cache");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().println(JSONUtil.parse(ResultUtil.failResult(e.getMessage(),"权限不足，拒绝访问")));
        httpServletResponse.getWriter().flush();
    }
}
