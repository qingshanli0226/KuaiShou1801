package com.lqs.kuaishou.kuaishou1801.home.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;

public class CityAdapter extends BaseRVAdapter<CityBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_city;
    }

    @Override
    protected void convert(CityBean.ResultBean itemData, BaseViewHolder baseViewHolder) {
        //获取ImageView
        ImageView imageView = baseViewHolder.getView(R.id.cityImg);
        Glide.with(baseViewHolder.itemView.getContext()).load(itemData.getCoverImg()).into(imageView);
    }
}
