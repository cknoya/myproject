package com.hidian.charging.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.gson.Gson;
import com.hidian.charging.Dialog.LoginDialog;
import com.hidian.charging.R;
import com.hidian.charging.entity.StatusState;
import com.hidian.charging.entity.doorsEng;
import com.hidian.charging.entity.doorsInfo;
import com.hidian.charging.http.HttpFactory;
import com.hidian.charging.http.HttpResult;
import com.hidian.charging.http.HttpTransformer;
import com.hidian.charging.ui.widget.BottomMenuDialog;
import com.hidian.charging.utils.Globe;
import com.hidian.charging.utils.ImageLoader;
import com.hidian.charging.utils.ToastUtils;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

//import com.amap.api.maps.model.MyLocationStyle;
//import com.amap.api.navi.CheckPermissionsActivity;

public class MainActivity extends CheckPermissionsActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.main_info_layout)
    LinearLayout mainInfoLayout;
    @BindView(R.id.left_icon)
    ImageView lefticon;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_icon)
    ImageView rightIcon;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.can_use)
    TextView canUse;
    @BindView(R.id.all_counts)
    TextView allCounts;
    @BindView(R.id.far_away)
    TextView farAway;
    @BindView(R.id.navigation_layout)
    LinearLayout navigationLayout;
    @BindView(R.id.repation)
    ImageButton repation;
    @BindView(R.id.question)
    ImageButton question;
    @BindView(R.id.saoma_layout)
    LinearLayout saomaLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.sing_out)
    TextView singOut;
    @BindView(R.id.nav_view)
    RelativeLayout navView;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.head_view)
    LinearLayout headView;
    @BindView(R.id.foot_view)
    LinearLayout footView;
    @BindView(R.id.time_layout)
    RelativeLayout timeLayout;
    @BindView(R.id.login_in)
    TextView loginIn;
    @BindView(R.id.head_image)
    ImageView headImage;
    @BindView(R.id.door_name)
    TextView doorName;
    @BindView(R.id.about_layout)
    LinearLayout aboutLayout;
    private MapView mapView;
    private AMap aMap;
    private LatLng mylatLng;
    protected boolean useThemestatusBarColor = false;//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useStatusBarColor = true;
    private List<Marker> markers;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private List<doorsInfo> mdoorsInfos = new ArrayList<>();
    private List<String> packgenames = new ArrayList<>();
    private String deviceid;
    private int Count;
    private BottomMenuDialog bottomMenuDialog;
    private String token;
    private SharedPreferences preference = null;
    private List<Marker> Mymarkers = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.title));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.drawble_layout);
        ButterKnife.bind(this);
        title.setText("Hidian");
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });
        if (isPackageInstalled("com.baidu.BaiduMap")) {
            packgenames.add("百度");
        }
        if (isPackageInstalled("com.autonavi.minimap")) {
            packgenames.add("高德");
        }
        if (isPackageInstalled("com.tencent.map")) {
            packgenames.add("腾讯");
        }
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        aMap.setMapType(AMap.MAP_TYPE_NAVI);//
        setloacation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        super.onRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
        setloacation();
    }

    private void setloacation() {
        UiSettings mUiSettings;//定义一个UiSettings对象
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);//
        aMap.setMapLanguage(AMap.ENGLISH);
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.	LOCATION_TYPE_LOCATE);//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        // aMap.setMaxZoomLevel(18);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mUiSettings.setZoomControlsEnabled(false);
        //地图点击监听
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mainInfoLayout.setVisibility(View.GONE);
                if (markers.size() > 0) {
                    markers.get(0).setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.postion)));
                    markers.clear();

                }
            }
        });
//声明定位回调监听器
        AMapLocationListener mLocationListener = mAMapLocationListener;

//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        AMapLocationClientOption mLocationOption = null;
//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
        aMap.setOnMarkerClickListener(markerClickListener);
    }

    private void getdoorsinfo(final double longitude, double latitude, double distance, int max, int page) {
        HttpFactory.getHttpApiSingleton()
                .getDoorsInfoList(longitude, latitude, distance, max, page, "MOBILE")
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
                    public void onNext(List<doorsInfo> doorsInfos) {
                        markers = new ArrayList<>();
                        ArrayList<MarkerOptions> markesoptions = new ArrayList<>();
                        mdoorsInfos.addAll(doorsInfos);
                        if (Mymarkers.size()>0){
                            for (int i=0;i<Mymarkers.size();i++){
                                Mymarkers.get(i).remove();
                            }
                        }
                        for (int i = 0; i < doorsInfos.size(); i++) {
                            MarkerOptions markerOption = new MarkerOptions();
                            markerOption.draggable(false);//设置Marker可拖动
                            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.drawable.postion)));
                            LatLng latLng = new LatLng(doorsInfos.get(i).getSiteGeoLatitude(), doorsInfos.get(i).getSiteGeoLongitude());
                            markerOption.position(latLng);
                            markesoptions.add(markerOption);
                            //aMap.addMarker(markerOption.position(latLng));
                        }
                        Mymarkers = aMap.addMarkers(markesoptions, false);

                    }
                });

    }

    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(Marker marker) {
            int postion = Integer.parseInt(marker.getId().substring(marker.getId().lastIndexOf("r") + 1, marker.getId().length())) - 2;
            if (postion != -1) {
                if (markers.size() <= 0) {
                    markers.add(marker);
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.postion_choose)));
                } else {
                    markers.get(0).setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.postion)));
                    markers.clear();
                    markers.add(marker);
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.postion_choose)));
                }
                getOnepostion(mdoorsInfos.get(postion).getId());
            } else {
                return false;
            }

            return true;
        }
    };

    private void getOnepostion(String id) {
        HttpFactory.getHttpApiSingleton()
                .getonePostion(id)
                .compose(new HttpTransformer<HttpResult<doorsInfo>, doorsInfo>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<doorsInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.toString());
                    }

                    @Override
                    public void onNext(doorsInfo doorsInfo) {
                        mainInfoLayout.setVisibility(View.VISIBLE);
                        Gson gson = new Gson();
                        doorsEng doorsEng = gson.fromJson(doorsInfo.getJsonObject(), doorsEng.class);
                        doorName.setText(doorsEng.getSite_name());
                        address.setText(doorsEng.getSite_address());
                        canUse.setText(doorsInfo.getTotalDeviceAvailable() + "available");
                        allCounts.setText(doorsInfo.getTotalDeviceDeployed() + "total");
                        float distance = AMapUtils.calculateLineDistance(mylatLng, markers.get(0).getPosition()) / 1000;
                        DecimalFormat df = new DecimalFormat("0.0");
                        String dis = df.format(distance);
                        farAway.setText(dis + "km");
                    }
                });
    }

    @OnClick({R.id.repation, R.id.question, R.id.saoma_layout, R.id.left_icon, R.id.navigation_layout, R.id.right_icon, R.id.sing_out, R.id.login_in, R.id.head_image,R.id.about_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.repation:
                setloacation();
                break;
            case R.id.question:
                startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                break;
            case R.id.saoma_layout:
                //打开扫描界面扫描条形码或二维码
                Intent intent;
                if (Globe.Token != null && !Globe.Token.equals("")) {
//                    intent = new Intent(MainActivity.this, CaptureActivity.class);
//                    //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
//                    startActivityForResult(intent, 0);
                     intent = new Intent(MainActivity.this, WeChatCaptureActivity.class);
                    startActivityForResult(intent,0);
                } else {
                    LoginDialog loginDialog = new LoginDialog();
                    loginDialog.show(getSupportFragmentManager(), "loginDialog");
                }
                break;
            case R.id.left_icon:
                drawerLayout.openDrawer(navView);
                break;
            case R.id.right_icon:
                intent = new Intent(MainActivity.this, NearbyListActivity.class);
                intent.putExtra("longtude", mylatLng.longitude);
                intent.putExtra("latiude", mylatLng.latitude);
                startActivity(intent);
                break;
            case R.id.navigation_layout:
                if (packgenames.size() <= 0) {
                    ToastUtils.showLong("您手机上尚未安装地位软件");
                } else {
                    bottomMenuDialog = new BottomMenuDialog.Builder(MainActivity.this)
                            .addMenus(packgenames, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomMenuDialog.dismiss();
                                    switch (v.getId()) {
                                        case 0:
                                            // 百度地图
                                            Intent naviIntent = new Intent("android.intent.action.VIEW", Uri.parse("baidumap://map/geocoder?location=" + markers.get(0).getPosition().latitude + "," + markers.get(0).getPosition().longitude));
                                            startActivity(naviIntent);
                                            break;
                                        case 1:
                                            // 高德地图
                                            naviIntent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://route?sourceApplication=appName&slat=&slon=&sname=我的位置&dlat=" + markers.get(0).getPosition().latitude + "&dlon=" + markers.get(0).getPosition().longitude + "&dname=目的地&dev=0&t=2"));
                                            startActivity(naviIntent);
                                            break;
                                        case 2:
                                            // 腾讯地图
                                            naviIntent = new Intent("android.intent.action.VIEW", Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=目的地&tocoord=" + markers.get(0).getPosition().latitude + "," + markers.get(0).getPosition().longitude + "&policy=0&referer=appName"));
                                            startActivity(naviIntent);
                                            break;
                                    }
                                }
                            }).create();
                    bottomMenuDialog.show();
                }
                break;
            case R.id.sing_out:
                preference = getSharedPreferences("info", 0);
                preference.edit().clear().commit();
                Globe.Token = "";
                name.setVisibility(View.GONE);
                loginIn.setVisibility(View.VISIBLE);
                headImage.setImageResource(R.drawable.user_image);
                singOut.setVisibility(View.GONE);
                break;
            case R.id.login_in:
                LoginDialog loginDialog = new LoginDialog();
                loginDialog.show(getSupportFragmentManager(), "loginDialog");
                break;
            case R.id.head_image:
                if (Globe.Token!=null && !Globe.Token.equals("")) {

                }else{
                    loginDialog = new LoginDialog();
                    loginDialog.show(getSupportFragmentManager(), "loginDialog");
                }
                break;
            case R.id.about_layout:
               // ApkUpdateUtils.download(MainActivity.this,"http://down1.uc.cn/down2/zxl107821.uc/miaokun1/UCBrowser_V11.5.8.945_android_pf145_bi800_(Build170627172528).apk","更新");
                break;
        }
    }

    public static boolean isPackageInstalled(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    //可以通过类implement方式实现AMapLocationListener接口，也可以通过创造接口类对象的方法实现
//以下为后者的举例：
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    mylatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    getdoorsinfo(amapLocation.getLongitude(), amapLocation.getLatitude(), 10, 300, 1);

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            String scanResult = bundle.getString("result");
            String content = data.getStringExtra("result");

            String deviceid = content.substring(content.lastIndexOf("/") + 1, content.length());
            preference = getSharedPreferences("info", 0);
            SharedPreferences.Editor editor = preference.edit();
            editor.putString("id", deviceid);
            editor.commit();

            Intent intent = new Intent(MainActivity.this, ScanResultActivity.class);
            intent.putExtra("deviceid", deviceid);
            startActivity(intent);
        }
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        preference = getSharedPreferences("info", 0);
        deviceid = preference.getString("id", "");
        if (!deviceid.equals("")) {
            getdevicestatus();
        }
        token = preference.getString("token", "");
        if (token != null && !token.equals("")) {
            Globe.Token = token;
            name.setVisibility(View.VISIBLE);
            loginIn.setVisibility(View.GONE);
            singOut.setVisibility(View.VISIBLE);
            name.setText(preference.getString("name", ""));
            ImageLoader.loadCircleImage(headImage, preference.getString("userimage", ""));

        } else {
            name.setVisibility(View.GONE);
            loginIn.setVisibility(View.VISIBLE);
            singOut.setVisibility(View.GONE);
            headImage.setImageResource(R.drawable.user_image);
        }

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
                        if (statusState.getOrderTimeLength() > 0 && statusState.isSameUser()) {
                            timeLayout.setVisibility(View.VISIBLE);
                            Count = statusState.getOrderTimeLength();
                            handler.postDelayed(runnable, 1000);
                        } else {
                            timeLayout.setVisibility(View.GONE);
                        }
                    }
                });
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Count--;
            time.setText(getStringTime(Count));
            if (Count > 0) {
                handler.postDelayed(this, 1000);
            }
        }
    };

    private String getStringTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
