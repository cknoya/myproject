package com.hidian.charging.entity;

/**
 * Created by Administrator on 2018/2/5.
 */

public class Devicestatuse {

    /**
     * orderStartTime : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * orderTimeLength : 0
     */

    private long orderStartTime;
    private int orderTimeLength;

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

}
