package com.hedon.interceptor;

import com.hedon.bean.AuditLog;
import com.hedon.bean.User;
import com.hedon.dao.AuditLogRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hedon Wang
 * @create 2020-10-02 17:05
 */
@Component
//Spring 中所有的 Interceptor 都需要继承 HandlerInterceptorAdapter
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    //审计日志需要存到数据库
    @Autowired
    private AuditLogRepository auditLogRepository;

    //在请求进来之前进行处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("3====>审计日志进");


        AuditLog auditLog = new AuditLog();
        //请求方式
        auditLog.setMethod(request.getMethod());
        //请求路径
        auditLog.setPath(request.getRequestURI());
        //用户名
        User user = (User)request.getAttribute("user");
        if (user != null){
            auditLog.setUsername(user.getUsername());
        }
        //存到数据库
        auditLogRepository.save(auditLog);
        //后面在请求出去的时候还需要存入信息，所以这里我们需要获取 auditLogId
        request.setAttribute("auditLogId",auditLog.getId());
        return true;
    }

    /*
    不要去覆盖 postHandle 这个方法，因为它只有在请求成功之后才会执行，我们这里不管请求成功与否，都应该进行记录
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }*/

    //请求完成（不管成功与否）后进行处理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("5=====>审计日志出");
        //获取请求进来时存的 AuditLog
        Integer auditLogId = (Integer) request.getAttribute("auditLogId");
        AuditLog auditLog = auditLogRepository.findById(auditLogId).get();
        //存储响应状态码
        auditLog.setStatus(response.getStatus());
        //保存到数据库
        auditLogRepository.save(auditLog);
    }
}
