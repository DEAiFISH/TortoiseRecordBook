package com.deaifish.tortoiserecordbook.config;

import com.deaifish.tortoiserecordbook.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 拦截器配置类
 *
 * @author DEAiFISH
 * @date 2023/12/3 01:49
 */
@Configuration
//@EnableWebMvc //禁用boot的默认配置
public class WebMvcInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * @description 多个拦截器组成一个拦截器链
     *              注册顺序就是拦截器执行书序
     *              addPathPatterns 用于添加拦截规则，/**表示拦截所有F请求
     *              excludePathPatterns 用户排除拦截
     * @author DEAiFISH
     * @date 2023/12/3 03:35
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor).addPathPatterns("/tortoise/**")
                .excludePathPatterns();
    }

}
