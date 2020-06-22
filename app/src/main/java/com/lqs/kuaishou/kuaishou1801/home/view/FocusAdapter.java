package com.lqs.kuaishou.kuaishou1801.home.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.home.mode.FocusBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;

public class FocusAdapter extends BaseRVAdapter<FocusBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_focus;
    }

    @Override
    protected void convert(FocusBean.ResultBean itemData, BaseViewHolder baseViewHolder) {
        //获取ImageView
        ImageView imageView = baseViewHolder.getView(R.id.focusImg);
        Glide.with(baseViewHolder.itemView.getContext()).load(itemData.getCoverImg()).into(imageView);
    }
}
