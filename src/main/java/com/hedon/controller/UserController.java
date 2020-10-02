package com.hedon.controller;

import com.hedon.bean.User;
import com.hedon.bean.UserInfo;
import com.hedon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Hedon Wang
 * @create 2020-10-01 17:49
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/logout")
    public void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }

    //UserInfo 前面去掉 @RequestBody,方便在浏览器进行测试（实际应用是需要加上的
    @GetMapping("/login")
    public void login(@Validated UserInfo userInfo,HttpServletRequest request){
        UserInfo info = userService.login(userInfo);
        //getSession(false)，如果之前有存 session，那就返回该 session，没有就返回空
        HttpSession session = request.getSession(false);
        if (session != null){
            //如果不为空，那就让原来的 session 失效，这样就可以避免携带黑客的 session 进行访问了
            session.invalidate();
        }
        request.getSession().setAttribute("user",info);
    }

    @PostMapping("/")
    public UserInfo create(@RequestBody @Validated UserInfo userInfo){
        return  userService.create(userInfo);
    }

    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo user){
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        userService.delete(id);
    }

    /**
     * 这里我们获取用户信息的时候，用户需要携带 Basic 认证信息，而且用户只能查询到自己的信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public UserInfo getUserById(@PathVariable("id") Integer id, HttpServletRequest request){
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");

        if (user != null && user.getId() == id){
            return userService.getById(id);
        }else{
            throw new RuntimeException("身份认证信息异常，获取用户信息失败！");
        }
    }


}
