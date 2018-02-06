package com.hidian.charging.Adpater;

import android.content.Context;
import android.view.View;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.hidian.charging.R;
import com.hidian.charging.entity.doorsEng;
import com.hidian.charging.entity.doorsInfo;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/2/1.
 */

public class NearbyAdpater extends BaseRecyclerViewAdapter<doorsInfo> {
    private LatLng latLng;
    public NearbyAdpater(Context context,LatLng latLng) {
        super(context);
        this.latLng =latLng;

    }
    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.near_by_item;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, doorsInfo doorsInfo) {
        Gson gson = new Gson();
        doorsEng doorsEng = gson.fromJson(doorsInfo.getJsonObject(), doorsEng.class);
        holder.setText(R.id.naem,doorsEng.getSite_name());
        holder.setText(R.id.address,doorsEng.getSite_address());
        holder.setText(R.id.can_use,doorsInfo.getTotalDeviceAvailable() + "available");
        holder.setText(R.id.all_counts,  doorsInfo.getTotalDeviceDeployed() + "total");
        LatLng mylatLng = new LatLng(doorsInfo.getSiteGeoLatitude(), doorsInfo.getSiteGeoLongitude());
        float distance = AMapUtils.calculateLineDistance(latLng, mylatLng) / 1000;
        DecimalFormat df = new DecimalFormat("0.0");
        String dis = df.format(distance);
        holder.setText(R.id.far_away,dis + "km");
    }

    @Override
    protected void onItemClickListener(View itemView, int position, final doorsInfo doorsInfo) {

    }
}
