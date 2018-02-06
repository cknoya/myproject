package com.hidian.charging.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class StatusState {

    /**
     * batteryPercentage : 0
     * currentTime : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * deviceId : string
     * deviceStatus : 0
     * newUser : false
     * orderStartTime : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * orderTimeLength : 0
     * priceOptions : [{"createTime":{"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0},"currentDuration":0,"currentPrice":0,"durationDifference":0,"id":0,"originalDuration":0,"originalPrice":0,"priceDifference":0,"updateTime":{"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}}]
     * priceTag : 0
     * ratePerHour : 0
     * sameUser : false
     * siteAddress : string
     * siteName : string
     * totalDeviceAvailable : 0
     * totalDeviceDeployed : 0
     */

    private int batteryPercentage;
    private long currentTime;
    private String deviceId;
    private int deviceStatus;
    private boolean newUser;
    private long orderStartTime;
    private int orderTimeLength;
    private int priceTag;
    private int ratePerHour;
    private boolean sameUser;
    private String siteAddress;
    private String siteName;
    private int totalDeviceAvailable;
    private int totalDeviceDeployed;
    private List<PriceOptionsBean> priceOptions;

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public long getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(long orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public int getOrderTimeLength() {
        return orderTimeLength;
    }

    public void setOrderTimeLength(int orderTimeLength) {
        this.orderTimeLength = orderTimeLength;
    }

    public int getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(int priceTag) {
        this.priceTag = priceTag;
    }

    public int getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(int ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public boolean isSameUser() {
        return sameUser;
    }

    public void setSameUser(boolean sameUser) {
        this.sameUser = sameUser;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getTotalDeviceAvailable() {
        return totalDeviceAvailable;
    }

    public void setTotalDeviceAvailable(int totalDeviceAvailable) {
        this.totalDeviceAvailable = totalDeviceAvailable;
    }

    public int getTotalDeviceDeployed() {
        return totalDeviceDeployed;
    }

    public void setTotalDeviceDeployed(int totalDeviceDeployed) {
        this.totalDeviceDeployed = totalDeviceDeployed;
    }

    public List<PriceOptionsBean> getPriceOptions() {
        return priceOptions;
    }

    public void setPriceOptions(List<PriceOptionsBean> priceOptions) {
        this.priceOptions = priceOptions;
    }

    public static class PriceOptionsBean {
        /**
         * createTime : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
         * currentDuration : 0
         * currentPrice : 0
         * durationDifference : 0
         * id : 0
         * originalDuration : 0
         * originalPrice : 0
         * priceDifference : 0
         * updateTime : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
         */

        private long createTime;
        private int currentDuration;
        private int currentPrice;
        private int durationDifference;
        private int id;
        private int originalDuration;
        private int originalPrice;
        private int priceDifference;
        private long updateTime;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getCurrentDuration() {
            return currentDuration;
        }

        public void setCurrentDuration(int currentDuration) {
            this.currentDuration = currentDuration;
        }

        public int getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(int currentPrice) {
            this.currentPrice = currentPrice;
        }

        public int getDurationDifference() {
            return durationDifference;
        }

        public void setDurationDifference(int durationDifference) {
            this.durationDifference = durationDifference;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOriginalDuration() {
            return originalDuration;
        }

        public void setOriginalDuration(int originalDuration) {
            this.originalDuration = originalDuration;
        }

        public int getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(int originalPrice) {
            this.originalPrice = originalPrice;
        }

        public int getPriceDifference() {
            return priceDifference;
        }

        public void setPriceDifference(int priceDifference) {
            this.priceDifference = priceDifference;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }


    }
}
