package com.hedon.filter;

import com.hedon.bean.User;
import com.hedon.dao.UserRepository;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Hedon Wang
 * @create 2020-10-01 22:14
 */
@Component
@Order(2)   //认证过滤器是 API 请求第 2 个经过的
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    //认证的过程需要查询数据库
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("2=====>这是认证");

        //示意图中说明了 BasicHttp 的认证方式需要用户将信息放入请求头中
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (StringUtils.isNotBlank(authHeader)){
            //有请求头的话，取出请求头"Basic "后面的字符串 => 信息密文（基于Base64）
            String token64 = StringUtils.substringAfter(authHeader,"Basic ");
            //解密 => 明文
            String token = new String(Base64Utils.decodeFromString(token64));
            //获取 username 和 password
            String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(token,":");
            String username = items[0];
            String password = items[1];
            //查询数据库
            User user = userRepository.findByUsername(username);
            //检查用户是否存在且密码是否正确
            if (user != null && SCryptUtil.check(password,user.getPassword())){
                //放入请求体，进入下一环
                httpServletRequest.getSession().setAttribute("user",user.buildUser(user));
                //表明该 session 是一个临时 session，请求过后需要在 finally 销毁
                httpServletRequest.getSession().setAttribute("temp","yes");
            }
        }

        try {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        } finally {
            //请求结束后才会执行 finally 语句
            HttpSession session = httpServletRequest.getSession();
            //用 Basic 认证的，每次请求后都销毁 session
            if (session.getAttribute("temp")!=null){
                session.invalidate();
            }
        }
    }
}
