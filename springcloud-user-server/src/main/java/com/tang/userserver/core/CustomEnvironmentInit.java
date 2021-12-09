package com.tang.userserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * 在springboot初始化之前加载自定义配置文件
 * @Author tangtiancong
 * @Date 2021/11/25:16:07
 */
public class CustomEnvironmentInit implements EnvironmentPostProcessor {
    Logger logger = LoggerFactory.getLogger(getClass());
    private static Properties properties = new Properties();
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String [] customPros = {
                "/customConfig/base.properties"
        };
        for (String pro:customPros
             ) {
            Resource resource =new ClassPathResource(pro);
            PropertySource propertySource = null;
            if(!resource.exists()){
                logger.error(pro+"资源文件不存在");
            }
            try {
                properties.load(resource.getInputStream());
                propertySource = new PropertiesPropertySource(resource.getFilename(),properties);
            } catch (IOException e) {
                logger.error(pro+"资源文件加载失败");
            }
            environment.getPropertySources().addLast(propertySource);
        }
    }
}
