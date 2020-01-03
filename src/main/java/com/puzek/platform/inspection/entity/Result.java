package com.puzek.platform.inspection.entity;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private int id; // 不用传
    private int car; // 巡检车编号
    private String resultType; // 巡检结果
    private Integer parkingId; // 车位号id
    private String batchNumber; // 批次号
    private String carNumber; // 分析车牌
    private String finalCarNumber;// 最终车牌
    private String pushStatus;// 推送状态
    private String pushTime;// 推送时间
    private String scanTime;// 巡检时间
    private String areaName;
    private String parkingNumber;
    private List<ResultImage> resultImages = new ArrayList<>(); // 图片列表

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getFinalCarNumber() {
        return finalCarNumber;
    }

    public void setFinalCarNumber(String finalCarNumber) {
        this.finalCarNumber = finalCarNumber;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<ResultImage> getResultImages() {
        return resultImages;
    }

    public void setResultImages(List<ResultImage> resultImages) {
        this.resultImages = resultImages;
    }
}
