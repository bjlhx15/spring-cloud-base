package com.lhx.springcloud.security.web;

import com.lhx.springcloud.common.annotation.SysLogger;
import com.lhx.springcloud.common.dto.RespDTO;
import com.lhx.springcloud.common.exception.CommonException;
import com.lhx.springcloud.common.exception.ErrorCode;
import com.lhx.springcloud.security.domain.Blog;
import com.lhx.springcloud.security.service.BlogService;
import com.lhx.springcloud.security.util.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/31 下午5:35
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @ApiOperation(value = "发布博客", notes = "发布博客")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    @SysLogger("postBlog")
    public RespDTO postBlog(@RequestBody Blog blog){
        //字段判读省略
        Blog blog1= blogService.postBlog(blog);
        return RespDTO.onSuc(blog1);
    }

    @ApiOperation(value = "根据用户id获取所有的blog", notes = "根据用户id获取所有的blog")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{username}")
    @SysLogger("getBlogs")
    public RespDTO getBlogs(@PathVariable String  username){
        //字段判读省略
        if(UserUtils.isMyself(username)) {
            List<Blog> blogs = blogService.findBlogs(username);
            return RespDTO.onSuc(blogs);
        }else {
            throw new CommonException(ErrorCode.TOKEN_IS_NOT_MATCH_USER);
        }
    }

    @ApiOperation(value = "获取博文的详细信息", notes = "获取博文的详细信息")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}/detail")
    @SysLogger("getBlogDetail")
    public RespDTO getBlogDetail(@PathVariable Long id){
        return RespDTO.onSuc(blogService.findBlogDetail(id));
    }
}
