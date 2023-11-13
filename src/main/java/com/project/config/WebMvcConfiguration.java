package com.project.config;

import com.project.interceptor.JwTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Slf4j
public class WebMvcConfiguration  extends WebMvcConfigurationSupport {

    @Autowired
    private JwTokenUserInterceptor jwTokenUserInterceptor;

    //注册自定义拦截器
    protected void addInterceptors(InterceptorRegistry registry){
        log.info("start registering custom interceptors");
        registry.addInterceptor(jwTokenUserInterceptor)
                .addPathPatterns("/api/v2/users/**")
                .excludePathPatterns("/api/v2/users/signin")
                .excludePathPatterns("/api/v2/users/signup");
    }
}
