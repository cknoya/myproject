package com.hidian.charging.Message;

/**
 * Created by Administrator on 2018/2/1.
 */

public class isShow {
    public boolean isshow;
    private boolean light;
    public isShow(boolean isshow,boolean light){
        this.isshow = isshow;
        this.light = light;
    }
    public boolean getisshow(){
        return isshow;
    }
    public boolean getlight(){
        return light;
    }
}
