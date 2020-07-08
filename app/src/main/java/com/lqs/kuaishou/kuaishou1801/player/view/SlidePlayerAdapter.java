package com.lqs.kuaishou.kuaishou1801.player.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;

public class SlidePlayerAdapter extends BaseRVAdapter<CityBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_slide_video_view;
    }

    @Override
    protected void convert(CityBean.ResultBean itemData, BaseViewHolder baseViewHolder) {

        //获取ImageView
        ImageView imageView = baseViewHolder.getView(R.id.videoImage);
        Glide.with(baseViewHolder.itemView.getContext()).load(itemData.getCoverImg()).into(imageView);

        //gsyPlayer播放视频
    }
}
