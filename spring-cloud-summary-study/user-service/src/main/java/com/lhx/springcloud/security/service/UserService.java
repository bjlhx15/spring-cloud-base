package com.lhx.springcloud.security.service;

import com.lhx.springcloud.common.dto.RespDTO;
import com.lhx.springcloud.common.exception.CommonException;
import com.lhx.springcloud.common.exception.ErrorCode;
import com.lhx.springcloud.security.client.AuthServiceClient;
import com.lhx.springcloud.security.domain.JWT;
import com.lhx.springcloud.security.domain.User;
import com.lhx.springcloud.security.dto.LoginDTO;
import com.lhx.springcloud.security.repository.UserDao;
import com.lhx.springcloud.security.util.BPwdEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/31 下午5:40
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    AuthServiceClient authServiceClient;

    public User createUser(User user){
        return  userDao.save(user);
    }

    public User getUserInfo(String username){
        return userDao.findByUsername(username);
    }
    public RespDTO login(String username , String password){
        User user= userDao.findByUsername(username);
        if(null==user){
            throw new CommonException(ErrorCode.USER_NOT_FOUND);
        }
        if(!BPwdEncoderUtils.matches(password,user.getPassword())){
            throw new CommonException(ErrorCode.USER_PASSWORD_ERROR);
        }
//要写成 Basic 客户端授权吗
        JWT jwt = authServiceClient.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==", "password", username, password);
        // 获得用户菜单
        if(null==jwt){
            throw new CommonException(ErrorCode.GET_TOKEN_FAIL);
        }
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setUser(user);
        loginDTO.setToken(jwt.getAccess_token());
        return RespDTO.onSuc(loginDTO);
    }
}
