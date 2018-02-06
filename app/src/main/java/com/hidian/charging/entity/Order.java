package com.hidian.charging.entity;

/**
 * Created by Administrator on 2018/2/3.
 */

public class Order {

    /**
     * experiment_id : 0
     * pay_channel : string
     * payment_id : 0
     */

    private int experiment_id;
    private String pay_channel;
    private int payment_id;

    public int getExperiment_id() {
        return experiment_id;
    }

    public void setExperiment_id(int experiment_id) {
        this.experiment_id = experiment_id;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
}
