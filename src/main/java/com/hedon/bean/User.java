package com.hedon.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Columns;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.BeanUtils;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


/**
 * @author Hedon Wang
 * @create 2020-10-01 17:44
 */
@Entity  //告诉 Spring Data JPA 这是一个实体类，需要在数据库中建表
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Integer id;
    private String name;
    @NotBlank(message = "用户名不能为空！")
    @Column(unique = true,nullable = false,table = "user") //数据库底层的约束 -> 用户名唯一性且不能为空
    private String username;
    @NotBlank(message = "密码不能为空！")
    private String password;

    private String permissions;

    public UserInfo buildUser(User user){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        return userInfo;
    }

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
