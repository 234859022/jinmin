package com.sanposs.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@MapperScan("resources.mapper")
@SpringBootApplication
@ServletComponentScan   //启动器启动时，扫描本目录以及子目录带有的webservlet注解�?
public class Application  extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       return builder.sources(Application.class);
    }

}

