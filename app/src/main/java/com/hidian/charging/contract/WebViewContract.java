package com.hidian.charging.contract;

import com.hidian.charging.base.BasePresenter;
import com.hidian.charging.base.BaseView;

/**
 * Created by ZHT on 2017/4/20.
 */

public interface WebViewContract {

    interface View extends BaseView {

        void showNoNetwork();

        void addImageClickListener();
    }

    interface Presenter extends BasePresenter {

    }
}
