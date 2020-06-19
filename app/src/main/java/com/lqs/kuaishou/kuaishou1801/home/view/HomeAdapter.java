package com.lqs.kuaishou.kuaishou1801.home.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;

public class HomeAdapter extends BaseRVAdapter<HomeBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_home;
    }

    @Override
    protected void convert(HomeBean.ResultBean itemData, BaseViewHolder baseViewHolder) {
        //获取ImageView
        ImageView imageView = baseViewHolder.getView(R.id.homeImg);
        Glide.with(baseViewHolder.itemView.getContext()).load(itemData.getCoverImg()).into(imageView);
    }
}
