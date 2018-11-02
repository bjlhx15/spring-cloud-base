package com.lhx.springcloud.security.repository;


import com.lhx.springcloud.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/25 下午5:03
 */
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
