package com.hidian.charging;

import android.app.Application;
import android.os.Build;
import android.os.Handler;

import com.hidian.charging.utils.FileUtils;
import com.hidian.charging.utils.LogUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by ZHT on 2017/4/17.
 * 自定义Application
 */

public class App extends Application {

    private static App mContext;
    public static IWXAPI mWxApi;
    private static Handler mMainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        mMainThreadHandler = new Handler();
        registToWX();
        //设置是否打印日志
        LogUtils.setIsLog(BuildConfig.LOG_DEBUG);
        //在6.0(M)版本下直接创建应用对应的文件夹
        //在6.0(M)版本以上的需要先进行权限申请
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            FileUtils.init(this);
        }
    }

    public static App getApplication() {
        return mContext;
    }
    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this,"wx83c2ea85dcacc8ce",true);
        // 将该app注册到微信
        mWxApi.registerApp("wx83c2ea85dcacc8ce");
    }
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }
}
