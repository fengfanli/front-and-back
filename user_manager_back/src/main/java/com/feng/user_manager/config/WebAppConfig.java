package com.feng.user_manager.config;

import com.feng.user_manager.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Value("${open.url}")
    private String openUrl;

    @Bean
    public MyInterceptor myInterceptor(){
        return new MyInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截以 /api 开头的请求，但是排除  openUrl：/**/open/**。 即 ：匹配这个（/**/login/**）URL的所有请求不拦截
        registry.addInterceptor(myInterceptor()).addPathPatterns("/api/**").excludePathPatterns(openUrl);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
