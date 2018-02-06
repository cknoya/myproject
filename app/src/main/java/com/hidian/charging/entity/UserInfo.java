package com.hidian.charging.entity;

/**
 * Created by Administrator on 2018/2/2.
 */

public class UserInfo {

    /**
     * userId : 1517568419063777720
     * headImgUrl : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLoXpn1ziaasNOO13NWHviaFJxIeSIuWd8edY0fe0vFlGkeYg6aia4BoasvZTiaHiaCsqlcJG5wNNBhr4Q/132
     * token : c0cc78900c1c4ee6ad08c70023aabb461517568648428
     * nickName : ç¼é»æ åº¦
     */

    private long userId;
    private String headImgUrl;
    private String token;
    private String nickName;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
