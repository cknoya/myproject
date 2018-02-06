package com.hidian.charging.http;

/**
 * Created by ZHT on 2017/4/19.
 * 响应数据的"基类"，通过指定泛型获取想要的数据类型
 */

public class HttpResult<T> {

    private int result;


    private T body;

    public int getResult() {
        return result;
    }

    public void setResult(int count) {
        this.result = count;
    }


    public T getBody() {
        return body;
    }

    public void setResults(T results) {
        this.body = results;
    }
}
