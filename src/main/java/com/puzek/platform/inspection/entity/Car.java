package com.puzek.platform.inspection.entity;

public class Car {
    private Integer id;
    private String license;
    private Integer area;
    private String loginTime;
    private String loginPosition;
    private String areaName;
    private String operation;
    private String pushUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginPosition() {
        return loginPosition;
    }

    public void setLoginPosition(String loginPosition) {
        this.loginPosition = loginPosition;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }
}
