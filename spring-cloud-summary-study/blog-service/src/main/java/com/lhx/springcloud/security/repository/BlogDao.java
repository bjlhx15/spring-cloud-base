package com.lhx.springcloud.security.repository;

import com.lhx.springcloud.security.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:31
 */
public interface BlogDao extends JpaRepository<Blog, Long> {

    List<Blog> findByUsername(String username);

}
