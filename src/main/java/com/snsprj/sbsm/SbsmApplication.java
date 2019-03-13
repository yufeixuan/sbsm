package com.snsprj.sbsm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.snsprj.sbsm.mapper")
public class SbsmApplication extends SpringBootServletInitializer {

    // war包
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SbsmApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SbsmApplication.class, args);
    }
}
