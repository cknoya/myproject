package com.hidian.charging.http;

import rx.functions.Func1;

/**
 * Created by ZHT on 2017/4/19.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.getResult()!=1) {
            throw new ApiException("网络异常");
        }
        return tHttpResult.getBody();
    }
}
