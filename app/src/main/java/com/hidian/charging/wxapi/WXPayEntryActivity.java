package com.hidian.charging.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2018/1/19.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(WXPayEntryActivity.this,"wx83c2ea85dcacc8ce");
        //  App.mWxApi.registerApp("wxbfa3f79969b1063d");
        //如果没回调onResp，八成是这句没有写
        api.handleIntent(getIntent(), WXPayEntryActivity.this);
    }
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                Log.i("savedInstanceState","发送成功ERR_OKERR_OK");
                //发送成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                Log.i("savedInstanceState","发送取消ERR_USER_CANCEL");
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.i("savedInstanceState","发送取消ERR_AUTH_DENIEDERR_AUTH_DENIEDERR_AUTH_DENIED");
                //发送被拒绝
                break;
            default:
                Log.i("savedInstanceState","发送返回breakbreakbreak");
                //发送返回
                break;
        }
    }
}
