package com.puzek.platform.inspection.entity;

public class ResultImage {
    private Integer id; // 不用传
    private String parkingId;// 车位id
    private String batchNumber;// 批次号
    private String imageName;// 图片名
    private String imageType;// 图片类型，暂时两种，有车牌的和全景的

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
