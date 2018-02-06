package com.hidian.charging.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hidian.charging.App;
import com.hidian.charging.R;
import com.hidian.charging.base.BaseDialogFragment;
import com.hidian.charging.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/2/2.
 */

public class LoginDialog extends BaseDialogFragment {
    @BindView(R.id.wechat)
    ImageView wechat;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.login_dialog_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }
    @OnClick({R.id.wechat})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.wechat:
                wxLogin();
                break;

        }

    }

        public void wxLogin() {
            if (!App.mWxApi.isWXAppInstalled()) {
                ToastUtils.showLong("您还未安装微信客户端");
                return;
            }
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            // req.state = "diandi_wx_login";
            App.mWxApi.sendReq(req);
            dismiss();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
