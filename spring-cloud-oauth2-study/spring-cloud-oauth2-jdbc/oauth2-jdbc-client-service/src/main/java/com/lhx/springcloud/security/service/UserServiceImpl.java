package com.lhx.springcloud.security.service;

import com.lhx.springcloud.security.domain.User;
import com.lhx.springcloud.security.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/25 下午5:22
 */
@Service
public class UserServiceImpl {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;


    public User create(String username, String password) {

        User user=new User();
        user.setUsername(username);
        String hash = encoder.encode(password);
        user.setPassword(hash);
        User u=userDao.save(user);
        return u;

    }
}
