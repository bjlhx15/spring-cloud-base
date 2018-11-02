package com.lhx.springcloud.security.service;

import com.lhx.springcloud.common.dto.RespDTO;
import com.lhx.springcloud.common.exception.CommonException;
import com.lhx.springcloud.common.exception.ErrorCode;
import com.lhx.springcloud.security.client.UserServiceClient;
import com.lhx.springcloud.security.domain.Blog;
import com.lhx.springcloud.security.domain.User;
import com.lhx.springcloud.security.dto.BlogDetailDTO;
import com.lhx.springcloud.security.repository.BlogDao;
import com.lhx.springcloud.security.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:33
 */
@Service
public class BlogService {

    @Autowired
    BlogDao blogDao;

    @Autowired
    UserServiceClient userServiceClient;

    public Blog postBlog(Blog blog) {
        return blogDao.save(blog);
    }

    public List<Blog> findBlogs(String username) {
        return blogDao.findByUsername(username);
    }


    public BlogDetailDTO findBlogDetail(Long id) {
        Optional<Blog> blogOptional = blogDao.findById(id);
        Blog blog = blogOptional.get();
        if (null == blog) {
            throw new CommonException(ErrorCode.BLOG_IS_NOT_EXIST);
        }
        RespDTO<User> respDTO = userServiceClient.getUser(UserUtils.getCurrentToken(), blog.getUsername());
        if (respDTO==null) {
            throw new CommonException(ErrorCode.RPC_ERROR);
        }
        BlogDetailDTO blogDetailDTO = new BlogDetailDTO();
        blogDetailDTO.setBlog(blog);
        blogDetailDTO.setUser(respDTO.data);
        return blogDetailDTO;
    }

}
