package com.qexz.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

@Configuration
public class QexzWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {


    /**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //自定义项目内目录
        //registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
        //指向外部目录
//        registry.addResourceHandler("/upload/**").addResourceLocations(QexzConst.UPLOAD_FILE_PATH);
        //自定义项目内目录
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/static/");

//        registry.addResourceHandler("/excel/**").addResourceLocations("E:/");


        super.addResourceHandlers(registry);
    }


}