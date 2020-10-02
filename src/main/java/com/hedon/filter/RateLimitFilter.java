package com.hedon.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Hedon Wang
 * @create 2020-10-01 18:32
 */
@Component
@Order(1)  //流控过滤器的第1个经过的过滤器
//继承 OncePerRequestFilter 确保本过滤器的逻辑只会被执行一次
public class RateLimitFilter extends OncePerRequestFilter {

    /**
     * 创建一个限流的控制器
     *  create() 参数表示1s允许多少个请求，这里设置1s只允许一个请求
     */
    private RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("1 ===> 这是流控");

        if (rateLimiter.tryAcquire()){
            //如果还没达到限流的额度，放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            //如果已经达到阈值了，拦截
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpServletResponse.getWriter().write("too many requests!");
            httpServletResponse.getWriter().flush();
            return;
        }
    }
}
