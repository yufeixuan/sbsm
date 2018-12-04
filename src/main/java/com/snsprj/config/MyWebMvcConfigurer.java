package com.snsprj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SKH
 * @date 2018-08-20 10:12
 **/
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {


    /**
     * 解决跨域
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
