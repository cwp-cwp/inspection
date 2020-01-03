package com.puzek.platform.inspection.entity;

public class Area {
    private Integer id;
    private String areaName;
    private String operation;
    private String pushUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public static void main(String[] args) {
        System.out.println(String.valueOf(null));
    }
}
