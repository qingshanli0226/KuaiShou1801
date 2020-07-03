package com.lqs.kuaishou.kuaishou1801.search.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPActivity;
import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.cache.mode.SearchHistory;
import com.lqs.kuaishou.kuaishou1801.search.SearchPresenterImpl;
import com.lqs.kuaishou.kuaishou1801.search.contract.SearchContract;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchRecommendBean;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchResultBean;

import java.util.List;

public class SearchActivity extends BaseMVPActivity<SearchPresenterImpl, SearchContract.ISearchView> implements View.OnClickListener,SearchContract.ISearchView {

    private RecyclerView historyRv;
    private RecyclerView recommendRv;
    private RecyclerView searchResultRv;

    private SearchHistoryAdapter searchHistoryAdapter;
    private SearchRecommendAdapter searchRecommendAdapter;
    private SearchResultAdapter searchResultAdapter;

    private SearchRecommendBean searchRecommendBean;
    private List<SearchHistory> searchHistoryList;

    private EditText searchEditText;
    private LinearLayout searchNote;
    @Override
    protected void initData() {
//不要实现，现在还没有调用
    }

    @Override
    protected void initView() {
        searchNote = findViewById(R.id.searchNote);
        //初始化历史记录列表
        historyRv = findViewById(R.id.historyRv);
        historyRv.setLayoutManager(new LinearLayoutManager(this));
        searchHistoryAdapter = new SearchHistoryAdapter();
        historyRv.setAdapter(searchHistoryAdapter);
        //初始化推荐列表
        recommendRv = findViewById(R.id.recommendRv);
        recommendRv.setLayoutManager(new GridLayoutManager(this,2));
        searchRecommendAdapter = new SearchRecommendAdapter();
        recommendRv.setAdapter(searchRecommendAdapter);
        findViewById(R.id.btnSearch).setOnClickListener(this);
        searchEditText = findViewById(R.id.searchEditText);

        //初始化搜索结果列表
        searchResultRv = findViewById(R.id.searchResultRv);
        searchResultRv.setLayoutManager(new LinearLayoutManager(this));
        searchResultAdapter = new SearchResultAdapter();
        searchResultRv.setAdapter(searchResultAdapter);


        getSearchHistoryDataFromCache();
        getRecommendDataFraomCache();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNote.setVisibility(View.VISIBLE);
                searchResultRv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //从缓存中获取搜索历史记录
    private void getSearchHistoryDataFromCache() {
        searchHistoryList = CacheManager.getInstance().getSearchHistoryList();
        if (searchHistoryList.size()>0) {//如果存在搜索历史记录，直接显示就行
            searchHistoryAdapter.updataData(searchHistoryList);
        }
    }

    //从缓存中获取推荐列表是数据
    private void getRecommendDataFraomCache() {
        searchRecommendBean = CacheManager.getInstance().getSearchRecommendBean();
        searchRecommendAdapter.updataData(searchRecommendBean.getResult());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                //点击搜索按钮
                //暂时不去服务端获取搜素数据，先调试搜索历史记录
                processSearch();
                break;
        }
    }

    //必须
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    getSearchHistoryDataFromCache();
                    break;
            }
        }
    };

    private void processSearch() {
        final String searchContent = searchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(searchContent)) {
            showMessage("输入不能空");
        } else {
            //先调用网络请求获取搜索内容
            iHttpPresenter.searchData(searchContent);
            //通过子线程将数据添加到数据库中
            CacheManager.getInstance().getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    SearchHistory searchHistory = new SearchHistory();
                    searchHistory.setSearchContent(searchContent);
                    searchHistory.setTime(System.currentTimeMillis());//生成一个搜索的历史记录
                    CacheManager.getInstance().addOneSearchHistory(searchHistory);
                    handler.sendEmptyMessage(1);//告诉主线程去刷新数据
                }
            });
        }
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new SearchPresenterImpl();
    }

    @Override
    public void onSearch(SearchResultBean searchResultBean) {
         //让搜索结果列表显示出来
        searchResultRv.setVisibility(View.VISIBLE);
        searchResultAdapter.updataData(searchResultBean.getResult());
         //隐藏搜索提示
        searchNote.setVisibility(View.GONE);
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoaing() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void pause() {
        super.pause();
    }
}
