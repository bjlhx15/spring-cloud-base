package com.lhx.springcloud.provider.business.mapper;

import com.lhx.springcloud.provider.business.po.auto.UserStudy;

import java.util.List;

public interface UserStudyExtMapper {
    List<UserStudy> getList(UserStudy record);
}