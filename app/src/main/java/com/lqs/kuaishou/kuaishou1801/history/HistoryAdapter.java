package com.lqs.kuaishou.kuaishou1801.history;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.cache.mode.HistoryEntity;

public class HistoryAdapter extends BaseRVAdapter<HistoryEntity> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_history;
    }

    @Override
    protected void convert(HistoryEntity itemData, BaseViewHolder baseViewHolder) {
        ImageView historyImage = baseViewHolder.getView(R.id.historyImg);
        Glide.with(baseViewHolder.itemView.getContext()).load(itemData.getImageUrl()).into(historyImage);
        TextView textView = baseViewHolder.getView(R.id.historyUserId);
        textView.setText(itemData.getUserId());
    }
}
