package com.hidian.charging.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/2/3.
 */

public class Payinfo {

    /**
     * alipayResBody : string
     * wechatAppDto : {"appid":"string","noncestr":"string","package":"string","partnerid":"string","prepayid":"string","sign":"string","timestamp":"string"}
     * wechatPubDto : {"appId":"string","nonceStr":"string","package":"string","paySign":"string","signType":"string","timeStamp":"string"}
     * wechatPubQrDto : {"orderCode":"string"}
     */

    private String alipayResBody;
    private WechatAppDtoBean wechatAppDto;
    private WechatPubDtoBean wechatPubDto;
    private WechatPubQrDtoBean wechatPubQrDto;

    public String getAlipayResBody() {
        return alipayResBody;
    }

    public void setAlipayResBody(String alipayResBody) {
        this.alipayResBody = alipayResBody;
    }

    public WechatAppDtoBean getWechatAppDto() {
        return wechatAppDto;
    }

    public void setWechatAppDto(WechatAppDtoBean wechatAppDto) {
        this.wechatAppDto = wechatAppDto;
    }

    public WechatPubDtoBean getWechatPubDto() {
        return wechatPubDto;
    }

    public void setWechatPubDto(WechatPubDtoBean wechatPubDto) {
        this.wechatPubDto = wechatPubDto;
    }

    public WechatPubQrDtoBean getWechatPubQrDto() {
        return wechatPubQrDto;
    }

    public void setWechatPubQrDto(WechatPubQrDtoBean wechatPubQrDto) {
        this.wechatPubQrDto = wechatPubQrDto;
    }

    public static class WechatAppDtoBean {
        /**
         * appid : string
         * noncestr : string
         * package : string
         * partnerid : string
         * prepayid : string
         * sign : string
         * timestamp : string
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class WechatPubDtoBean {
        /**
         * appId : string
         * nonceStr : string
         * package : string
         * paySign : string
         * signType : string
         * timeStamp : string
         */

        private String appId;
        private String nonceStr;
        @SerializedName("package")
        private String packageX;
        private String paySign;
        private String signType;
        private String timeStamp;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
    }

    public static class WechatPubQrDtoBean {
        /**
         * orderCode : string
         */

        private String orderCode;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }
    }
}
