package com.hidian.charging.Adpater;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.hidian.charging.Message.isShow;
import com.hidian.charging.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class FeedBackAdpater extends BaseRecyclerViewAdapter<String> {
    private int mSelectedPos=-1;//保存当前选中的position 重点！
    public FeedBackAdpater(Context context ) {
        super(context);

    }

    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.feed_back_item;
    }

    //提供给外部Activity来获取当前checkBox选中的item，这样就不用去遍历了 重点！
    public int getSelectedPos(){
        return mSelectedPos;
    }
    @Override
    protected void bindData(final BaseViewHolder holder, final int position, String s) {
        holder.setText(R.id.problem,s);
        final RadioButton checkimage= (RadioButton)(holder.getView(R.id.checked_img));
        checkimage.setChecked(mSelectedPos==position);
        checkimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedPos!=position){//当前选中的position和上次选中不是同一个position 执行
                    checkimage.setChecked(true);
                    if(mSelectedPos!=-1){//判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos,0);
                    }
                    if (position==3){
                        EventBus.getDefault().post(new isShow(true,false));
                    }else{
                        EventBus.getDefault().post(new isShow(false,true));
                    }
                    mSelectedPos=position;
                }
            }
        });
    }

    @Override
    protected void onItemClickListener(View itemView, int position, String s) {

    }
}
