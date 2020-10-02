package com.hedon.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


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
    private String username;
    private String password;

    public UserInfo buildUser(User user){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        return userInfo;
    }
}
