package com.lqs.kuaishou.kuaishou1801.home.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPFragment;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.cache.mode.HistoryEntity;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.home.contract.FocusContract;
import com.lqs.kuaishou.kuaishou1801.home.mode.FocusBean;
import com.lqs.kuaishou.kuaishou1801.home.presenter.FocusPresenterImpl;
import com.lqs.kuaishou.kuaishou1801.player.view.GsyPlayerActivity;

public class FocusFragment extends BaseMVPFragment<FocusPresenterImpl, FocusContract.IFocusView> implements FocusContract.IFocusView, BaseRVAdapter.IRecyclerViewItemClickListener {
    private RecyclerView focusRv;
    private FocusAdapter focusAdapter;

    @Override
    protected void initHttpData() {
        ihttpPresenter.getFocusData();
    }

    @Override
    protected void initPresenter() {
        ihttpPresenter = new FocusPresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_focus;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        focusRv = findViewById(R.id.rv);
        focusRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        focusAdapter = new FocusAdapter();
        focusAdapter.setiRecyclerViewItemClickListener(this);
        focusRv.setAdapter(focusAdapter);
    }

    //返回网络请求数据
    @Override
    public void onFocusData(FocusBean focusBean) {
        printLog("获取到关注数据");
        focusAdapter.updataData(focusBean.getResult());
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
    public void onItemClick(int position) {
        showMessage("你点击了第" + position + "个位置");
        String videoUrl = KSConstant.BASE_RESOURCE_URL+ focusAdapter.getItemData(position).getVedioUrl();
        printLog(videoUrl);

        Bundle bundle = new Bundle();
        bundle.putString(KSConstant.PLAYER_VIDEO_URL, videoUrl);


        //添加一条历史记录
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setTime(System.currentTimeMillis());
        historyEntity.setImageUrl(focusAdapter.getItemData(position).getCoverImg());
        historyEntity.setVideoUrl(focusAdapter.getItemData(position).getVedioUrl());
        historyEntity.setUserId(String.valueOf(focusAdapter.getItemData(position).getUserId()));
        CacheManager.getInstance().addOneHistoryEntity(historyEntity, null);

        launchActivity(GsyPlayerActivity.class, bundle);
    }
}
