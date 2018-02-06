package com.hidian.charging.ui.activity;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.android.AutoScannerView;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.google.zxing.client.android.CaptureActivityHandler;
import com.hidian.charging.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 模仿微信的扫描界面
 */
public class WeChatCaptureActivity extends BaseCaptureActivity implements View.OnClickListener {

    private static final String TAG = WeChatCaptureActivity.class.getSimpleName();
    @BindView(R.id.preview_view)
    SurfaceView previewView;
    @BindView(R.id.autoscanner_view)
    AutoScannerView autoscannerView;
    @BindView(R.id.flashLightIv)
    AppCompatImageView flashLightIv;
    @BindView(R.id.flashLightTv)
    TextView flashLightTv;
    @BindView(R.id.flashLightLayout)
    LinearLayout flashLightLayout;
    @BindView(R.id.albumLayout)
    LinearLayout albumLayout;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.activity_wechat_capture)
    FrameLayout activityWechatCapture;

       private SurfaceView surfaceView;
//    private AutoScannerView autoScannerView;
//    private LinearLayout flashLightLayout;
//    private AppCompatImageView flashLightIv;
//    private TextView flashLightTv;
//    private LinearLayout albumLayout;
//    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_wechat_capture);
        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

//        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
//      autoScannerView = (AutoScannerView) findViewById(R.id.autoscanner_view);
//        flashLightLayout = (LinearLayout) findViewById(R.id.flashLightLayout);
//        flashLightIv = (AppCompatImageView)findViewById(R.id.flashLightIv);
//        flashLightTv = (TextView) findViewById(R.id.flashLightTv);
//        flashLightLayout.setOnClickListener(this);
//        albumLayout = (LinearLayout) findViewById(R.id.albumLayout);
//        albumLayout.setOnClickListener(this);
        flashLightLayout.setOnClickListener(this);
        albumLayout.setOnClickListener(this);
                 /*有闪光灯就显示手电筒按钮  否则不显示*/
        if (isSupportCameraLedFlash(getPackageManager())) {
            flashLightLayout.setVisibility(View.VISIBLE);
        } else {
            flashLightLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoscannerView.setCameraManager(cameraManager);
    }


    /**
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    public void switchFlashImg(int flashState) {

        if (flashState == 9) {
           flashLightIv.setImageResource(R.drawable.ic_open);
            flashLightTv.setText("关闭闪光灯");
        } else {
           flashLightIv.setImageResource(R.drawable.ic_close);
            flashLightTv.setText("打开闪光灯");
        }

    }

    @Override
    public SurfaceView getSurfaceView() {
        return (previewView == null) ? (SurfaceView) findViewById(R.id.preview_view) : previewView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        Log.i(TAG, "dealDecode ~~~~~ " + rawResult.getText() + " " + barcode + " " + scaleFactor);
        playBeepSoundAndVibrate(true, false);
        Intent intent = getIntent();
        intent.putExtra("result", rawResult.getText());
        setResult(RESULT_OK, intent);
        this.finish();
//        对此次扫描结果不满意可以调用
//        reScan();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.flashLightLayout) {
            /*切换闪光灯*/
            cameraManager.switchFlashLight((CaptureActivityHandler) getHandler());
        } else if (id == R.id.albumLayout) {
            /*打开相册*/
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
        }
    }
}
