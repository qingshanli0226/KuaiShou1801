package com.lqs.kuaishou.kuaishou1801.search.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchResultBean;

import java.util.HashMap;

public class SearchContract {

    public interface ISearchView extends IView {
        void onSearch(SearchResultBean searchResultBean);
    }


    public static abstract class SearchPresenter extends BasePresenter<ISearchView> {
        public abstract void searchData(String rId);
    }
}
