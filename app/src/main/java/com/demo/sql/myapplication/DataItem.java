package com.demo.sql.myapplication;

public class DataItem {
    private int rfid;
    private String productType;
    private String recyclable;
    private int percentage;

    public int getRfid() {
        return rfid;
    }

    public void setRfid(int rfid) {
        this.rfid = rfid;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getRecyclable() {
        return recyclable;
    }

    public void setRecyclable(String recyclable) {
        this.recyclable = recyclable;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public DataItem(int rfid, String productType, String recyclable, int percentage) {
        this.rfid = rfid;
        this.productType = productType;
        this.recyclable = recyclable;
        this.percentage = percentage;
    }
}
