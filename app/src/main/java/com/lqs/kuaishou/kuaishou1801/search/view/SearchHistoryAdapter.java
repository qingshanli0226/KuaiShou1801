package com.lqs.kuaishou.kuaishou1801.search.view;

import android.widget.TextView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.cache.mode.SearchHistory;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchRecommendBean;

public class SearchHistoryAdapter extends BaseRVAdapter<SearchHistory> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_search_history;
    }

    @Override
    protected void convert(SearchHistory itemData, BaseViewHolder baseViewHolder) {
        TextView textView = baseViewHolder.getView(R.id.searchContent);
        textView.setText(itemData.getSearchContent());
    }

}
