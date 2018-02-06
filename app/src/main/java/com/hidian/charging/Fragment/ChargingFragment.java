package com.hidian.charging.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hidian.charging.App;
import com.hidian.charging.R;
import com.hidian.charging.base.BaseFragment;
import com.hidian.charging.entity.Order;
import com.hidian.charging.entity.Orderinfo;
import com.hidian.charging.entity.Payinfo;
import com.hidian.charging.entity.StatusState;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.ui.activity.FeedBackActivity;
import com.hidian.charging.ui.activity.MainActivity;
import com.hidian.charging.utils.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Administrator on 2018/2/1.
 */

@SuppressLint("ValidFragment")
public class ChargingFragment extends BaseFragment {
    @BindView(R.id.feedback)
    ImageView feedback;
    @BindView(R.id.xrefreshview_footer_progressbar)
    ProgressBar xrefreshviewFooterProgressbar;
    @BindView(R.id.more_time)
    TextView moreTime;
    @BindView(R.id.txt_bootom)
    TextView txtBootom;
    Unbinder unbinder;
    @BindView(R.id.time_count)
    TextView timeCount;
    private TimerTask timerTask;
    private Timer timer = new Timer();
    private int Count;
    private boolean iswork = true;
    private String deviceid;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    timeCount.setText(getStringTime(Count));
                    break;
                case 1:
                    iswork = false;
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    break;
            }
        }
    };

    public ChargingFragment(int time, String deviceid) {
        this.Count = time;
        this.deviceid = deviceid;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charging_fragment_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        timeCount.setText(getStringTime(Count));
        new Thread(new TimeThread()).start();
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
                        Count = statusState.getOrderTimeLength();
                        timeCount.setText(getStringTime(Count));
                    }
                });
    }

    class TimeThread implements Runnable {
        @Override
        public void run() {
            while (iswork) {
                try {
                    Count--;
                    if (Count > 0) {
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getStringTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.feedback, R.id.more_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.feedback:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.more_time:
                createorder();
                break;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        getdevicestatus();
    }

    private void createorder() {
        Order order = new Order();
        order.setPay_channel("wx");
        HttpFactory.getHttpApiSingleton()
                .createOrder(deviceid, order)
                .compose(new HttpTransformer<HttpResult<Orderinfo>, Orderinfo>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<Orderinfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(Orderinfo orderinfo) {
                        Pay(orderinfo.getId());
                    }
                });
    }

    private void Pay(String id) {
        HttpFactory.getHttpApiSingleton()
                .Wxpay(id, "APP")
                .compose(new HttpTransformer<HttpResult<Payinfo>, Payinfo>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<Payinfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(Payinfo payinfo) {
                        if (payinfo != null) {
                            PayReq payReq = new PayReq();
                            payReq.appId = payinfo.getWechatAppDto().getAppid();
                            payReq.partnerId = payinfo.getWechatAppDto().getPartnerid();
                            payReq.prepayId = payinfo.getWechatAppDto().getPrepayid();
                            payReq.packageValue = payinfo.getWechatAppDto().getPackageX();
                            payReq.nonceStr = payinfo.getWechatAppDto().getNoncestr();
                            payReq.timeStamp = payinfo.getWechatAppDto().getTimestamp();
                            payReq.sign = payinfo.getWechatAppDto().getSign();
                            App.mWxApi.sendReq(payReq);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        iswork = false;
    }
}
