package com.lqs.kuaishou.kuaishou1801.home.view;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPFragment;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.cache.mode.HistoryEntity;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.home.contract.CityContract;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;
import com.lqs.kuaishou.kuaishou1801.home.presenter.CityPresenterImpl;
import com.lqs.kuaishou.kuaishou1801.player.view.GsyPlayerActivity;
import com.lqs.kuaishou.kuaishou1801.player.view.SlidePlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class CityFragment extends BaseMVPFragment<CityPresenterImpl, CityContract.ICityView> implements CityContract.ICityView, BaseRVAdapter.IRecyclerViewItemClickListener {
    private RecyclerView cityRv;
    private CityAdapter cityAdapter;

    private ArrayList<CityBean.ResultBean> beanList = new ArrayList<>();

    @Override
    protected void initHttpData() {
        ihttpPresenter.getCityData();
    }

    @Override
    protected void initPresenter() {
        ihttpPresenter = new CityPresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        cityRv = findViewById(R.id.rv);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        cityRv.setLayoutManager(staggeredGridLayoutManager);
        cityAdapter = new CityAdapter();
        cityAdapter.setiRecyclerViewItemClickListener(this);
        cityRv.setAdapter(cityAdapter);
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
        String videoUrl = KSConstant.BASE_RESOURCE_URL+ cityAdapter.getItemData(position).getVedioUrl();
        printLog(videoUrl);


        //添加一条历史记录
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setTime(System.currentTimeMillis());
        historyEntity.setImageUrl(cityAdapter.getItemData(position).getCoverImg());
        historyEntity.setVideoUrl(cityAdapter.getItemData(position).getVedioUrl());
        historyEntity.setUserId(String.valueOf(cityAdapter.getItemData(position).getUserId()));
        CacheManager.getInstance().addOneHistoryEntity(historyEntity, null);

        //启动滑动播放页面
        Bundle bundle = new Bundle();
        //将列表数据传递到滑动播放页面
        bundle.putParcelableArrayList(KSConstant.PLAYER_VIDEO_LIST, beanList);//把beanList列表传到滑动播放页面
        bundle.putInt(KSConstant.PLAYER_VIDEO_POSITION, position);
        launchActivity(SlidePlayerActivity.class, bundle);
    }

    @Override
    public void onCityData(CityBean cityBean) {
        printLog("获取到同城数据");
        cityAdapter.updataData(cityBean.getResult());
        if (cityBean.getResult() == null || cityBean.getResult().size() == 0) {
            return;
        }
        beanList.addAll(cityBean.getResult());
    }
}
