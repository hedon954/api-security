package com.hedon.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
}
