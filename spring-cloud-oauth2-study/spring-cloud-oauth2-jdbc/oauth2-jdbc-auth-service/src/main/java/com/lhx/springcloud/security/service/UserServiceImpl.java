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
public class UserServiceImpl implements UserService {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;

    @Override
    public void create(User user) {

        User existing = userDao.findByUsername(user.getUsername());
        //Assert.isNull(existing, "user already exists: " + user.getUsername());
        String hash = encoder.encode(user.getPassword());
        //User.builder().password(hash);
        user.setPassword(hash);
        userDao.save(user);

    }
}
