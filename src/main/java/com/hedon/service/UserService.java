package com.hedon.service;

import com.hedon.bean.User;
import com.hedon.bean.UserInfo;

import java.util.List;

/**
 * @author Hedon Wang
 * @create 2020-10-01 19:49
 */
public interface UserService {

    UserInfo create(UserInfo userInfo);

    UserInfo update(UserInfo userInfo);

    void delete(Integer id);

    UserInfo getById(Integer id);


}
