package com.hedon.interceptor;

import com.hedon.bean.User;
import com.hedon.bean.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hedon Wang
 * @create 2020-10-02 17:54
 */
@Component
public class AclInterceptor extends HandlerInterceptorAdapter {

    //不需要身份认证就可以访问的请求
    private String[] permitUrls = new String[]{"/user/login"};


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("4=====>授权");

        //默认过
        boolean result = true;

        //不需要认证的请求直接通过
        if (ArrayUtils.contains(permitUrls,request.getRequestURI())){
            return true;
        }

        //拿到当前用户信息
        UserInfo user = (UserInfo)request.getSession().getAttribute("user");
        //考虑问题一：是否需要认证
        if (user == null){
            //这里我们设置所有请求都需要进行认证
            response.setContentType("text/plain");
            response.getWriter().write("need authentication!!!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            result = false;
        }else {
            //认证通过
            //考虑问题二：是否拥有权限
            String method = request.getMethod();
            if (!user.hasPermission(method)){
                //没有权限
                response.setContentType("text/plain");
                response.getWriter().write("forbidden!!!");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                result = false;
            }else{
                //有权限
            }
        }
        return result;
    }
}
