package com.lhx.springcloud.provider.business.po;

import com.github.pagehelper.PageInfo;
import com.lhx.springcloud.provider.business.po.auto.UserStudy;

public class UserStudyVo {
    private UserStudy record;

    private PageInfo pageInfo;

    public UserStudy getRecord() {
        return record;
    }

    public void setRecord(UserStudy record) {
        this.record = record;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}