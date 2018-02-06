package com.hidian.charging.http;

import com.hidian.charging.entity.Devicestatuse;
import com.hidian.charging.entity.FeedBack;
import com.hidian.charging.entity.Order;
import com.hidian.charging.entity.Orderinfo;
import com.hidian.charging.entity.Payinfo;
import com.hidian.charging.entity.StatusState;
import com.hidian.charging.entity.UserInfo;
import com.hidian.charging.entity.doorsInfo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ZHT on 2017/4/19.
 * Retrofit接口，定义请求方法
 */

public interface HttpApi {

    String BASE_URL = "http://test.business.api.hidian.in/";
    //获取地图上点
    @GET("rest/sites")
    Observable<HttpResult<List<doorsInfo>>>getDoorsInfoList(@Query("longitude") double longitude,@Query("latitude") double latitude,@Query("distance") double distance, @Query("max") int max,@Query("page") int page
            ,@Query("source") String source);
    //获取设备状态
    @GET("rest/devicestatus")
    Observable<HttpResult<StatusState>>getdevicestatus(@Query("id") String id,@Query("check") String check,@Query("pay_channel") String pay_channel);
    //获取摸一个点
    @GET("rest/siteinfo")
    Observable<HttpResult<doorsInfo>>getonePostion( @Query("siteid") String siteid);
    //反馈
    @POST("rest/feedbacks")
    Observable<HttpResult<String>>feedback(@Body()FeedBack feedBack);
    //登录
    @GET("rest/user/Login")
    Observable<HttpResult<UserInfo>>login(@Query("code") String code);
    //创建订单
    @POST("rest/order/createOrder/{qrcodeKey}")
    Observable<HttpResult<Orderinfo>>createOrder(@Path("qrcodeKey") String deviceid , @Body() Order order);
    //发起支付
    @POST("rest/order/pay/{orderId} ")
    Observable<HttpResult<Payinfo>>Wxpay(@Path("orderId") String orderid,@Query("payChannel") String patchannel);
    //查询解锁状态
//    @GET("rest/deviceunlockinfo")
//    Observable<HttpResult<Devicestatuse>>getStatuse(@Query("id") String deviceid);
}
