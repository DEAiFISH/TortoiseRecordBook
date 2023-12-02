package com.deaifish.tortoiserecordbook.interceptor;

import com.deaifish.tortoiserecordbook.bean.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @description 登录验证过滤器
 *
 * @author DEAiFISH
 * @date 2023/12/3 01:38
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println("开始拦截 LoginInterceptor.........");
        //业务代码
        //请求单点登录系统，根据request中的token获取登录信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            System.out.println("LoginInterceptor 用户当前无登录状态！");
            response.sendRedirect("/tortoiserecordbook/index.html");
            return false;
        }
        request.setAttribute("user", user);
        return true;
    }

}
