package com.alivin.myblog.config;

import com.alivin.myblog.interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Fer
 * @date 2021/8/16
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/login.html").setViewName("admin/login");

    }


    // 使自定义化组件生效
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    @Autowired
    private BaseInterceptor baseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
    }

}
