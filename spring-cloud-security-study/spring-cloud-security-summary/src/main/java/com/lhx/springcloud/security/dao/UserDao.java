package com.lhx.springcloud.security.dao;

import com.lhx.springcloud.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/23 上午9:40
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
