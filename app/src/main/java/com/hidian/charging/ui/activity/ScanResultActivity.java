package com.hidian.charging.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hidian.charging.Fragment.BadFragment;
import com.hidian.charging.Fragment.ChargingFragment;
import com.hidian.charging.Fragment.LowePowerFragment;
import com.hidian.charging.Fragment.NoNetWorkFragment;
import com.hidian.charging.Fragment.PayFragment;
import com.hidian.charging.Fragment.ProgressFragment;
import com.hidian.charging.R;
import com.hidian.charging.base.BaseActivity;
import com.hidian.charging.entity.StatusState;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ScanResultActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    private String deviceid;
    private PayFragment payFragment;
    private LowePowerFragment lowePowerFragment;
    private NoNetWorkFragment noNetWorkFragment;
    private BadFragment badFragment;
    private ProgressFragment progressFragment;
    private ChargingFragment chargingFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.scan_result_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        deviceid = getIntent().getStringExtra("deviceid");
        getdevicestatus();
    }

    private void getdevicestatus() {
        HttpFactory.getHttpApiSingleton()
                .getdevicestatus(deviceid, "1", "")
                .compose(new HttpTransformer<HttpResult<StatusState>, StatusState>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<StatusState>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(StatusState statusState) {

                        switch (statusState.getDeviceStatus()){
                            case 0:
                                // TODO: 2018/1/31 正常 支付
                                title.setText("Payment");
                                initpayFragment();
                                break;
                            case 2:
                                title.setText("Unlocking");
                                initprogressFragment();
                            case 3:
                                // TODO: 2018/1/31  充电页面
                                if (statusState.isSameUser()) {
                                    title.setText("Unlock succeed");
                                    initchargingFragment(statusState.getOrderTimeLength());
                                }else {
                                    title.setText("Payment");
                                    initpayFragment();
                                }
                                break;
                            case 50:
                            case 56:
                                //todo 电量低
                                title.setText("low battery level");
                               initlowerpowerFragment();
                                break;
                            case 55 :
                                // TODO: 2018/1/31 掉线
                                title.setText("Device offline");
                                initnonetworkfragment();
                                 break;
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                                title.setText("Device not available");
                                initbadfragement();
                                //todo 报废
                                break;

                        }
                    }
                });
    }
    private void initchargingFragment(int time) {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (chargingFragment == null) {
            chargingFragment = new ChargingFragment(time,deviceid);
            transaction.add(R.id.main_frame, chargingFragment,"chargingFragment");
        }

        //显示需要显示的fragment
        transaction.show(chargingFragment);
        //提交事务
        transaction.commit();
    }
    private void initprogressFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (progressFragment == null) {
            progressFragment = new ProgressFragment(deviceid);
            transaction.add(R.id.main_frame, progressFragment,"progressFragment");
        }

        //显示需要显示的fragment
        transaction.show(progressFragment);
        //提交事务
        transaction.commit();
    }
    private void initpayFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (payFragment == null) {
            payFragment = new PayFragment(deviceid);
            transaction.add(R.id.main_frame, payFragment,"payFragment");
        }

        //显示需要显示的fragment
        transaction.show(payFragment);
        //提交事务
        transaction.commit();
    }

    private void initlowerpowerFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (lowePowerFragment == null) {
            lowePowerFragment = new LowePowerFragment();
            transaction.add(R.id.main_frame, lowePowerFragment,"lowePowerFragment");
        }
        //显示需要显示的fragment
        transaction.show(lowePowerFragment);
        //提交事务
        transaction.commit();
    }
    private void initnonetworkfragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (noNetWorkFragment == null) {
            noNetWorkFragment = new NoNetWorkFragment();
            transaction.add(R.id.main_frame, noNetWorkFragment,"noNetWorkFragment");
        }
        //显示需要显示的fragment
        transaction.show(noNetWorkFragment);
        //提交事务
        transaction.commit();
    }
    private void initbadfragement() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (badFragment == null) {
            badFragment = new BadFragment();
            transaction.add(R.id.main_frame, badFragment,"badFragment");
        }

        //显示需要显示的fragment
        transaction.show(badFragment);
        //提交事务
        transaction.commit();
    }
    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
