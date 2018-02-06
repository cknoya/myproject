package com.hidian.charging.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hidian.charging.Adpater.FeedBackAdpater;
import com.hidian.charging.Message.isShow;
import com.hidian.charging.R;
import com.hidian.charging.base.BaseActivity;
import com.hidian.charging.entity.FeedBack;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Administrator on 2018/2/1.
 */

public class FeedBackActivity extends BaseActivity {
    List<String> problems = new ArrayList<>();
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txt)
    EditText txt;
    @BindView(R.id.txt_layout)
    LinearLayout txtLayout;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.submit)
    TextView submit;
    private String probleminfo;
    private int problemcode;
    private FeedBackAdpater feedBackAdpater;
    @Override
    protected int getLayoutId() {
        return R.layout.feed_back_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        title.setText("Feedback");
        problems.add("Charging not working with light on");
        problems.add("Charging not working with light off");
        problems.add("Device powered off after charging for a while");
        problems.add("None of above");
         feedBackAdpater = new FeedBackAdpater(FeedBackActivity.this);
        feedBackAdpater.setData(problems);
        list.setLayoutManager(new LinearLayoutManager(FeedBackActivity.this));
        list.setAdapter(feedBackAdpater);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0){
                    submit.setBackgroundResource(R.drawable.text_green);
                    submit.setTextColor(getResources().getColor(R.color.white));
                }else{
                    submit.setBackgroundResource(R.drawable.backgroung_gray);
                    submit.setTextColor(getResources().getColor(R.color.txt_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void feedback(){
        FeedBack feedBack  =new FeedBack();
        feedBack.setFeedbackProblem(probleminfo);
        feedBack.setFeedbackProblemCode(problemcode);
        feedBack.setPhone("");
        feedBack.setRefund(false);
        HttpFactory.getHttpApiSingleton()
                .feedback(feedBack)
                .compose(new HttpTransformer<HttpResult<String>, String>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        ToastUtils.showLong("success");
                    }
                });

    }
    @OnClick({R.id.back,R.id.submit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if (!txt.getText().toString().equals("")){
                    probleminfo = txt.getText().toString();
                    problemcode = 0;
                }else{
                    probleminfo = problems.get(feedBackAdpater.getSelectedPos());
                    problemcode = feedBackAdpater.getSelectedPos();
                }
                feedback();
                break;
        }

    }
    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(isShow isShow) {
        if (isShow.getisshow()) {
            txtLayout.setVisibility(View.VISIBLE);
        } else {
            txtLayout.setVisibility(View.GONE);
        }
        if (isShow.getlight()) {
            submit.setBackgroundResource(R.drawable.text_green);
            submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            submit.setBackgroundResource(R.drawable.backgroung_gray);
            submit.setTextColor(getResources().getColor(R.color.txt_gray));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
