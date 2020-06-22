package com.lqs.kuaishou.kuaishou1801.history;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.cache.mode.HistoryEntity;

import java.util.List;

public class HistoryActivity extends BaseActivity implements CacheManager.IHistoryQueryCallback {
    private HistoryAdapter historyAdapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = findViewById(R.id.historyRv);
        historyAdapter = new HistoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdapter);

        //获取浏览历史记录的数据
        CacheManager.getInstance().queryHistoryEntity(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public void onQueryCallback(final List<HistoryEntity> historyEntityList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                historyAdapter.updataData(historyEntityList);
            }
        });

    }
}
