package com.lhx.springcloud.security.service.impl;

import com.lhx.springcloud.security.entity.Blog;
import com.lhx.springcloud.security.service.IBlogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/23 上午9:42
 */
@Service
public class BlogService implements IBlogService {

    private List<Blog> list=new ArrayList<>();

    public BlogService(){
        list.add(new Blog(1L, " spring in action", "good!"));
        list.add(new Blog(2L,"spring boot in action", "nice!"));
    }

    @Override
    public List<Blog> getBlogs() {
        return list;
    }

    @Override
    public void deleteBlog(long id) {
        Iterator iter = list.iterator();
        while(iter.hasNext()) {
            Blog blog= (Blog) iter.next();
            if (blog.getId()==id){
                iter.remove();
            }
        }
    }
}
