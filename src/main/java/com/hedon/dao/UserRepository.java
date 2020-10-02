package com.hedon.dao;

import com.hedon.bean.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Hedon Wang
 * @create 2020-10-01 18:19
 */
public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User,Integer> {

    //因为我们前面继承了 JpaSpecificationExecutor，所以 JPA 会自动根据 findByUsername 去生成 SQL 语句
    User findByUsername(String username);

}
