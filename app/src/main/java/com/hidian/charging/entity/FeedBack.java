package com.hidian.charging.entity;

/**
 * Created by Administrator on 2018/2/1.
 */

public class FeedBack {

    /**
     * feedbackProblem : string
     * feedbackProblemCode : 0
     * phone : string
     * refund : true
     */

    private String feedbackProblem;
    private int feedbackProblemCode;
    private String phone;
    private boolean refund;

    public String getFeedbackProblem() {
        return feedbackProblem;
    }

    public void setFeedbackProblem(String feedbackProblem) {
        this.feedbackProblem = feedbackProblem;
    }

    public int getFeedbackProblemCode() {
        return feedbackProblemCode;
    }

    public void setFeedbackProblemCode(int feedbackProblemCode) {
        this.feedbackProblemCode = feedbackProblemCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRefund() {
        return refund;
    }

    public void setRefund(boolean refund) {
        this.refund = refund;
    }
}
