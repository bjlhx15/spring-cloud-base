package com.lhx.springcloud.security.service;

import com.lhx.springcloud.security.domain.SysLog;
import com.lhx.springcloud.security.repository.SysLogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/11/1 下午5:45
 */
@Service
public class SysLogService {

    @Autowired
    SysLogDAO sysLogDAO;

    public void saveLogger(SysLog sysLog){
        sysLogDAO.save(sysLog);
    }
}
