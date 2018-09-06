package com.lhx.springcloud.provider.business.service;

import com.lhx.springcloud.provider.business.po.auto.UserStudy;

public interface IUserStudyService {
    int insert(UserStudy record) throws Exception;
    UserStudy findOne(int id)throws Exception;
}
