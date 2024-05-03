package com.wosys.base.config;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TokenConfigure implements WebMvcConfigurer {

    @Resource
    private SessionFilter filter;

    /**
     * 注册token 拦截器，该拦截器主要是将token 解析出来
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TokenInterceptor拦截器，主要设置前后端cors 头设置，和前端提交的token 进行处理
        InterceptorRegistration registration = registry.addInterceptor(filter);
        registration.addPathPatterns("/**"); //所有路径都被拦截
    }
}
