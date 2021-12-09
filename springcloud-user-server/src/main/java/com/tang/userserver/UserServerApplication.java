package com.tang.userserver;

import com.tang.userserver.exception.UserApplicationException;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author tangtiancong
 */

@SpringBootApplication
@MapperScan("com.tang.userserver.mapper.*")
public class UserServerApplication {
    public static  Logger logger = LoggerFactory.getLogger(UserApplicationException.class);
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UserServerApplication.class, args);
        String []beanNames = run.getBeanDefinitionNames();
        for (String name:beanNames
             ) {
            logger.info("springboot自动加载的配置："+name);
        }
    }

}
