package com.lhx.springcloud.provider.business.service;

import com.lhx.springcloud.provider.business.po.auto.UserStudy;

import java.util.List;

public interface IUserStudyService {
    int insert(UserStudy record) throws Exception;
    UserStudy findOne(int id)throws Exception;
    List<UserStudy> getList(UserStudy record  )throws Exception;
}
