package com.lqs.kuaishou.kuaishou1801.search.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchResultBean;

public class SearchResultAdapter extends BaseRVAdapter<SearchResultBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_search_result;
    }

    @Override
    protected void convert(SearchResultBean.ResultBean itemData, BaseViewHolder baseViewHolder) {
        //获取ImageView
        ImageView imageView = baseViewHolder.getView(R.id.searchResultImg);
        Glide.with(baseViewHolder.itemView.getContext()).load(itemData.getCoverImg()).into(imageView);

    }
}
