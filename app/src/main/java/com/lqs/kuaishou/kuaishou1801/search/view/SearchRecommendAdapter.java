package com.lqs.kuaishou.kuaishou1801.search.view;

import android.widget.TextView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchRecommendBean;

public class SearchRecommendAdapter extends BaseRVAdapter<SearchRecommendBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_search_recommend;
    }

    @Override
    protected void convert(SearchRecommendBean.ResultBean itemData, BaseViewHolder baseViewHolder) {
        TextView textView = baseViewHolder.getView(R.id.recommendTv);
        textView.setText(itemData.getName());
    }
}
