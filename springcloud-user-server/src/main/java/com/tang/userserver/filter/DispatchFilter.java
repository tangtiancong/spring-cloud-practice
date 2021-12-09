package com.tang.userserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author tangtiancong
 * @Date 2021/11/22:16:20
 */
public class DispatchFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(DispatchFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //设置上送与返回报文编码格式
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        logger.info("前端服务器国区："+servletRequest.getLocale());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
