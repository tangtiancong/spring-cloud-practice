package com.tang.userserver.configuration;


import com.tang.userserver.filter.DispatchFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * proxyBeanMethods作用，默认为true时，在调用容器代理组件或者依赖组件时使用的是唯一实例，即在容器中找组件，找到则使用该组件，
 * 为false时，不是同一组件实例
 * @Author tangtiancong
 * @Date 2021/11/22:16:32
 */
@Configuration(proxyBeanMethods = true)
public class WebConfig implements WebMvcConfigurer {
    /**
     * 注册过滤器
     */
    @Bean
    public FilterRegistrationBean dispatcherFilter(){
        DispatchFilter dispatchFilter = new DispatchFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //注册过滤器
        filterRegistrationBean.setFilter(dispatchFilter);
        //设置过滤的url
        List<String> urlPattern = new ArrayList<String>();
        urlPattern.add("/*");
        filterRegistrationBean.setUrlPatterns(urlPattern);
        //设置执行顺序
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
    /**
     * 注册 监听器
     */
//    @Bean
//    public ServletListenerRegistrationBean dispatchListener(){
//        return null ;
//    }
    /**
     * 注册 servlet
     */
//    @Bean
//    public ServletRegistrationBean userDispatcherServlet(){
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//        servletRegistrationBean.setServlet(new UserServletDispatcher());
//        //设置过滤的url
//        List<String> urlPattern = new ArrayList<String>();
//        urlPattern.add("/*");
//        servletRegistrationBean.setUrlMappings(urlPattern);
//        servletRegistrationBean.setLoadOnStartup(1);
//        return servletRegistrationBean;
//    }

}
