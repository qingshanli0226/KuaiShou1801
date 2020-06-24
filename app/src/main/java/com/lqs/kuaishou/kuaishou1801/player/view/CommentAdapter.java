package com.lqs.kuaishou.kuaishou1801.player.view;

import android.widget.TextView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;

public class CommentAdapter extends BaseRVAdapter<String> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_comment;
    }

    @Override
    protected void convert(String itemData, BaseViewHolder baseViewHolder) {

        TextView textView = baseViewHolder.getView(R.id.content);
        textView.setText(itemData);
    }
}
