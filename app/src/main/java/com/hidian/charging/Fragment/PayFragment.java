package com.hidian.charging.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hidian.charging.App;
import com.hidian.charging.R;
import com.hidian.charging.base.BaseFragment;
import com.hidian.charging.entity.Order;
import com.hidian.charging.entity.Orderinfo;
import com.hidian.charging.entity.Payinfo;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.ui.activity.FeedBackActivity;
import com.hidian.charging.utils.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Administrator on 2018/1/31.
 */

@SuppressLint("ValidFragment")
public class PayFragment extends BaseFragment {
    @BindView(R.id.txt_bootom)
    TextView txtBootom;
    Unbinder unbinder;
    @BindView(R.id.pay_btn)
    LinearLayout payBtn;
    private String deviceid;
    public PayFragment(String deviceid){
        this.deviceid = deviceid;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.pay_fragment_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.pay_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_btn:
                    createorder();
                break;
        }
    }
    private void createorder(){
        Order order  =new Order();
        order.setPay_channel("wx");
        HttpFactory.getHttpApiSingleton()
                .createOrder(deviceid,order)
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
    private void Pay(String id){
        HttpFactory.getHttpApiSingleton()
                .Wxpay(id,"APP")
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
                        if (payinfo!=null) {
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
    }
}
