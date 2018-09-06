package com.lhx.springcloud.provider.business.po.auto;

public class UserStudy {
    private Integer sId;

    private String userName;

    private String sAge;

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getsAge() {
        return sAge;
    }

    public void setsAge(String sAge) {
        this.sAge = sAge == null ? null : sAge.trim();
    }
}