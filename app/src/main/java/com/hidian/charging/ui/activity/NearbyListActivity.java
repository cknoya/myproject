package com.hidian.charging.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.hidian.charging.Adpater.BaseRecyclerViewAdapter;
import com.hidian.charging.Adpater.NearbyAdpater;
import com.hidian.charging.R;
import com.hidian.charging.base.BaseActivity;
import com.hidian.charging.entity.doorsInfo;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.ui.widget.BottomMenuDialog;
import com.hidian.charging.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

import static com.hidian.charging.ui.activity.MainActivity.isPackageInstalled;

/**
 * Created by Administrator on 2018/2/1.
 */

public class NearbyListActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.list)
    RecyclerView list;
    private double longtude;
    private double latitude;
    private LatLng mylatLng;
    private List<String> packgenames = new ArrayList<>();
    private BottomMenuDialog bottomMenuDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.near_by_layout;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        title.setText("Devices nearby");
        longtude = getIntent().getDoubleExtra("longtude", 0);
        latitude = getIntent().getDoubleExtra("latiude", 0);
        mylatLng = new LatLng(latitude, longtude);
        getdoorsinfo();
        if (isPackageInstalled("com.baidu.BaiduMap")){
            packgenames.add("百度");
        }
        if (isPackageInstalled("com.autonavi.minimap")){
            packgenames.add("高德");
        }
        if (isPackageInstalled("com.tencent.map")){
            packgenames.add("腾讯");
        }
    }

    private void getdoorsinfo() {
        HttpFactory.getHttpApiSingleton()
                .getDoorsInfoList(longtude, latitude, 2, 30, 1, "MOBILE")
                .compose(new HttpTransformer<HttpResult<List<doorsInfo>>, List<doorsInfo>>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<List<doorsInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(final List<doorsInfo> doorsInfos) {
                        NearbyAdpater nearbyAdpater = new NearbyAdpater(NearbyListActivity.this,mylatLng);
                        nearbyAdpater.setData(doorsInfos);
                        list.setLayoutManager(new LinearLayoutManager(NearbyListActivity.this));
                        list.setAdapter(nearbyAdpater);
                        nearbyAdpater.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, final int position, Object o) {
                                if (packgenames.size() <= 0) {
                                    ToastUtils.showLong("您手机上尚未安装地位软件");
                                } else {
                                    bottomMenuDialog = new BottomMenuDialog.Builder(NearbyListActivity.this)
                                            .addMenus(packgenames, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    bottomMenuDialog.dismiss();
                                                    switch (v.getId()) {
                                                        case 0:
                                                            // 百度地图
                                                            Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" + doorsInfos.get(position).getSiteGeoLatitude() + "," + doorsInfos.get(position).getSiteGeoLongitude()));
                                                            startActivity(naviIntent);
                                                            break;
                                                        case 1:
                                                            // 高德地图
                                                            naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://route?sourceApplication=appName&slat=&slon=&sname=我的位置&dlat=" + doorsInfos.get(position).getSiteGeoLatitude() + "&dlon=" + doorsInfos.get(position).getSiteGeoLongitude() + "&dname=目的地&dev=0&t=2"));
                                                            startActivity(naviIntent);
                                                            break;
                                                        case 2:
                                                            // 腾讯地图
                                                            naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=目的地&tocoord=" + doorsInfos.get(position).getSiteGeoLatitude() + "," +doorsInfos.get(position).getSiteGeoLongitude() + "&policy=0&referer=appName"));
                                                            startActivity(naviIntent);
                                                            break;
                                                    }
                                                }
                                            }).create();
                                    bottomMenuDialog.show();
                                }
                            }
                        });

                    }
                });

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
