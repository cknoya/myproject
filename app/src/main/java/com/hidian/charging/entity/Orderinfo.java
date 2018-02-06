package com.hidian.charging.entity;

/**
 * Created by Administrator on 2018/2/3.
 */

public class Orderinfo {

    /**
     * createdAt : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * deviceId : string
     * duration : 0
     * id : string
     * paidAt : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * pointPaySuccess : true
     * price : 0
     * refundedAt : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * siteId : string
     * state : string
     * unlockedAt : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * updatedAt : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * userId : string
     */

    private long createdAt;
    private String deviceId;
    private int duration;
    private String id;
    private long paidAt;
    private boolean pointPaySuccess;
    private int price;
    private long refundedAt;
    private String siteId;
    private String state;
    private long unlockedAt;
    private long updatedAt;
    private String userId;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(long paidAt) {
        this.paidAt = paidAt;
    }

    public boolean isPointPaySuccess() {
        return pointPaySuccess;
    }

    public void setPointPaySuccess(boolean pointPaySuccess) {
        this.pointPaySuccess = pointPaySuccess;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getRefundedAt() {
        return refundedAt;
    }

    public void setRefundedAt(long refundedAt) {
        this.refundedAt = refundedAt;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(long unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
