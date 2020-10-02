package com.hedon.service.impl;

import com.hedon.bean.User;
import com.hedon.bean.UserInfo;
import com.hedon.dao.UserRepository;
import com.hedon.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * @author Hedon Wang
 * @create 2020-10-01 19:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo create(UserInfo userInfo) {
        User user = new User();
        //在存储到数据库之前，进行密码加密，后面3个参数不需要过多了解
        userInfo.setPassword(SCryptUtil.scrypt(userInfo.getPassword(),32768,8,1));
        BeanUtils.copyProperties(userInfo,user);
        userRepository.save(user);
        userInfo.setId(user.getId());
        return userInfo;
    }

    @Override
    public UserInfo update(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        userRepository.save(user);
        return userInfo;
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserInfo getById(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId != null){
            User user = byId.get();
            UserInfo userInfo = user.buildUser(user);
            return userInfo;
        }else{
            return null;
        }
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        User user = userRepository.findByUsername(userInfo.getUsername());
        return user.buildUser(user);
    }
}
