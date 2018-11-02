package com.lhx.springcloud.security.service;

import com.lhx.springcloud.security.entity.Blog;

import java.util.List;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/23 上午9:41
 */
public interface IBlogService {
    List<Blog> getBlogs();
    void deleteBlog(long id);
}
