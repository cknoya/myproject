package com.hidian.charging.Adpater;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hidian.charging.utils.ImageLoader;

/**
 * Created by Administrator on 2018/2/1.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    //创建View容器,根据key为控件id
    private SparseArray<View> viewArray;
    private int viewType;
    public BaseViewHolder(View itemView) {
        super(itemView);
        viewArray = new SparseArray<>();
    }
    /**
     * 获取布局中的View
     */
    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = viewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewArray.put(viewId, view);
        }
        return (T) view;
    }
    public Context getContext() {
        return itemView.getContext();
    }
    public TextView getTextView(int resId){
        return (TextView) getView(resId);
    }
    public void setText(int resId,String str){
        getTextView(resId).setText(str);
    }
    public ImageView getImageview(int resId){
        return (ImageView)getView(resId);
    }
    public void setimage(int resId,String url,int drawlebid){
        if (url!=null){
           ImageLoader.loadImage(getImageview(resId),url);
        }else{
            getImageview(resId).setBackgroundResource(drawlebid);
        }
    }
    public View getView(int viewId) {
        return findViewById(viewId);
    }
    public void setViewTyep(int viewType) {
        this.viewType = viewType;
    }
    public int getViewType() {
        return viewType;
    }
}