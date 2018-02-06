package com.hidian.charging.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hidian.charging.R;
import com.hidian.charging.base.BaseFragment;
import com.hidian.charging.entity.Devicestatuse;
import com.hidian.charging.entity.StatusState;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.ui.activity.FeedBackActivity;
import com.hidian.charging.utils.ToastUtils;

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
public class ProgressFragment extends BaseFragment {
    @BindView(R.id.feedback)
    TextView feedback;
    @BindView(R.id.txt_bootom)
    TextView txtBootom;
    Unbinder unbinder;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    private int count = 0;
    private String deviceid;
    private boolean iswork = true;
    private int status;
    private boolean pasue = true;
    public ProgressFragment(String id){
        this.deviceid = id;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.progress_bar_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        progressbar.setProgress(count);
        txtInfo.setText("UnLocking "+count+"%");
        new Thread(new ResultThread()).start();

    }

    class ResultThread implements Runnable {
        @Override
        public void run() {
            while (iswork) {
                try {
                    getstatuse();
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    private void getstatuse(){
        HttpFactory.getHttpApiSingleton()
                .getdevicestatus(deviceid,"1","")
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
                        if (count<99) {
                            count += 3;
                            if (statusState.getDeviceStatus() == 3){
                                iswork = false;
                                txtInfo.setText("UnLocking success");
                            FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                            ChargingFragment chargingFragment = new ChargingFragment(statusState.getOrderTimeLength(),deviceid);
                            transaction.replace(R.id.main_frame, chargingFragment,"chargingFragment");
                            //显示需要显示的fragment
                            transaction.show(chargingFragment);
                            //提交事务
                            transaction.commit();
                            }else {
                                progressbar.setProgress(count);
                                txtInfo.setText("UnLocking " + count + "%");
                            }
                        }else{
                            iswork = false;
                            txtInfo.setText("UnLock failed,\nyou will receive full refund\nin 5 minutes");
//                            if (!pasue) {
                                status = statusState.getDeviceStatus();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                ChargingFragment chargingFragment = new ChargingFragment(statusState.getOrderTimeLength(), deviceid);
                                transaction.replace(R.id.main_frame, chargingFragment, "chargingFragment");
                                //显示需要显示的fragment
                                transaction.show(chargingFragment);
                                //提交事务
                                transaction.commitAllowingStateLoss();
//                            }
                        }
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.feedback:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        iswork = false;
    }
}
