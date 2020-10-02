package com.hedon.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;


/**
 * @author Hedon Wang
 * @create 2020-10-01 17:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo {
    private Integer id;
    private String name;

    @NotBlank(message = "用户名不能为空！")
    private String username;

    @NotBlank(message = "密码不能为空！")
    private String password;

    private String permissions;

    public boolean hasPermission(String method) {
        //默认没权限
        boolean result = false;
        //GET 请求对应 r 读请求
        if (StringUtils.equalsIgnoreCase("get",method)){
            result = StringUtils.contains(permissions,"r");
        }else{
            //非 GET 请求对应 w 写请求
            result = StringUtils.contains(permissions,"w");
        }
        return result;
    }
}
