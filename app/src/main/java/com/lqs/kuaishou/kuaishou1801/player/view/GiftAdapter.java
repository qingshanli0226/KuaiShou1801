package com.lqs.kuaishou.kuaishou1801.player.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.player.mode.GiftBean;

public class GiftAdapter extends BaseRVAdapter<GiftBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_gift;
    }

    @Override
    protected void convert(GiftBean.ResultBean itemData, BaseViewHolder baseViewHolder) {
        ImageView giftImg = baseViewHolder.getView(R.id.giftImg);

        Glide.with(baseViewHolder.itemView.getContext()).load(KSConstant.BASE_RESOURCE_URL+itemData.getGif_url())
                .into(giftImg);

    }
}
