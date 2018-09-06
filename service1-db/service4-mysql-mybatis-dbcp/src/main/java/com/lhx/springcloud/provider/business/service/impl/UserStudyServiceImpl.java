package com.lhx.springcloud.provider.business.service.impl;

import com.lhx.springcloud.provider.business.mapper.auto.UserStudyMapper;
import com.lhx.springcloud.provider.business.po.auto.UserStudy;
import com.lhx.springcloud.provider.business.service.IUserStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStudyServiceImpl implements IUserStudyService {
    @Autowired
    private UserStudyMapper mapper;
    @Override
    public int insert(UserStudy record) throws Exception{
        return mapper.insert(record);
    }

    @Override
    public UserStudy findOne(int id) throws Exception {
        return mapper.selectByPrimaryKey(id);
    }
}
