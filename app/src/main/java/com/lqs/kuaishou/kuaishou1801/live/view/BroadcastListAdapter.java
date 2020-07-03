package com.lqs.kuaishou.kuaishou1801.live.view;

import android.widget.TextView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.live.mode.BroadcastListBean;

public class BroadcastListAdapter extends BaseRVAdapter<BroadcastListBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_broadcast_list;
    }

    @Override
    protected void convert(BroadcastListBean.ResultBean itemData, BaseViewHolder baseViewHolder) {

        TextView textView = baseViewHolder.getView(R.id.userName);
        textView.setText(itemData.getName());
    }
}
