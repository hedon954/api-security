package com.hedon.controller;

import com.hedon.bean.User;
import com.hedon.bean.UserInfo;
import com.hedon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Hedon Wang
 * @create 2020-10-01 17:49
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
        User user = (User) request.getAttribute("user");

        if (user != null && user.getId() == id){
            return userService.getById(id);
        }else{
            throw new RuntimeException("身份认证信息异常，获取用户信息失败！");
        }
    }


}
