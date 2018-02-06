package com.hidian.charging.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.hidian.charging.entity.UserInfo;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.ui.activity.MainActivity;
import com.hidian.charging.utils.Globe;
import com.hidian.charging.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Administrator on 2017/12/22.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;
    private String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       api = WXAPIFactory.createWXAPI(WXEntryActivity.this,"wx83c2ea85dcacc8ce");
      //  App.mWxApi.registerApp("wxbfa3f79969b1063d");
        //如果没回调onResp，八成是这句没有写
       api.handleIntent(getIntent(), WXEntryActivity.this);
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        if(resp instanceof SendAuth.Resp){
            SendAuth.Resp newResp = (SendAuth.Resp) resp;
            //获取微信传回的code
             code = newResp.code;
            Log.i("newRespnewResp","   code    :"+code);

        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
            if(resp.getType()== ConstantsAPI.COMMAND_SENDAUTH){
                    //登录
                 Login();
                }
                Log.i("savedInstanceState","发送成功ERR_OKERR_OK");
                //发送成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                Log.i("savedInstanceState","发送取消ERR_USER_CANCEL");

                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.i("savedInstanceState","发送取消ERR_AUTH_DENIEDERR_AUTH_DENIEDERR_AUTH_DENIED");
//                if (resp.getType()== ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
//                    //分享
//                    finish();
//                    Intent intent =new Intent(this,MyInviteActivity.class);
//                    intent.putExtra("type",false);
//                    startActivity(intent);
//                }else if(resp.getType()== ConstantsAPI.COMMAND_SENDAUTH){
//                    //登录
//                    finish();
//                    startActivity(new Intent(this, LoginActivity.class));
//                }else if(resp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
//                    finish();
//                    startActivity(new Intent(this, PayActivity.class));
//                }
                //发送被拒绝
                break;
            default:
                Log.i("savedInstanceState","发送返回breakbreakbreak");
                //发送返回
                break;
        }

    }
    private void Login(){
        HttpFactory.getHttpApiSingleton()
                .login(code)
                .compose(new HttpTransformer<HttpResult<UserInfo>, UserInfo>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        Globe.Token = userInfo.getToken();
                        SharedPreferences preference = getSharedPreferences("info", 0);
                        SharedPreferences.Editor editor = preference.edit();
                        editor.putString("token", Globe.Token);
                        editor.putString("name",userInfo.getNickName());
                        editor.putString("userimage",userInfo.getHeadImgUrl());
                        editor.commit();
                        finish();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
