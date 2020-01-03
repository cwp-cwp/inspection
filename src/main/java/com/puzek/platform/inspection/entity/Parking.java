package com.puzek.platform.inspection.entity;

public class Parking {
    private Integer id;
    private Integer area;
    private String parkingNumber;
    private String x1;
    private String y1;
    private String x2;
    private String y2;
    private String x3;
    private String y3;
    private String x4;
    private String y4;
    private String status;
    private String operateTime;
    private String streetName;
    private String areaName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getX1() {
        return x1;
    }

    public void setX1(String x1) {
        this.x1 = x1;
    }

    public String getY1() {
        return y1;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public String getY2() {
        return y2;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public String getX3() {
        return x3;
    }

    public void setX3(String x3) {
        this.x3 = x3;
    }

    public String getY3() {
        return y3;
    }

    public void setY3(String y3) {
        this.y3 = y3;
    }

    public String getX4() {
        return x4;
    }

    public void setX4(String x4) {
        this.x4 = x4;
    }

    public String getY4() {
        return y4;
    }

    public void setY4(String y4) {
        this.y4 = y4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "---------------------Parking{" +
                "id=" + id +
                ", area=" + area +
                ", parkingNumber='" + parkingNumber + '\'' +
                ", x1='" + x1 + '\'' +
                ", y1='" + y1 + '\'' +
                ", x2='" + x2 + '\'' +
                ", y2='" + y2 + '\'' +
                ", x3='" + x3 + '\'' +
                ", y3='" + y3 + '\'' +
                ", x4='" + x4 + '\'' +
                ", y4='" + y4 + '\'' +
                ", status='" + status + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
