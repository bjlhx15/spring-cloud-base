package com.lhx.springcloud.provider.business.mapper.auto;

import com.lhx.springcloud.provider.business.po.auto.UserStudy;

public interface UserStudyMapper {
    int deleteByPrimaryKey(Integer sId);

    int insert(UserStudy record);

    int insertSelective(UserStudy record);

    UserStudy selectByPrimaryKey(Integer sId);

    int updateByPrimaryKeySelective(UserStudy record);

    int updateByPrimaryKey(UserStudy record);
}