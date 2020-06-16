package com.lqs.kuaishou.kuaishou1801.home.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPFragment;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.home.contract.HomeContract;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;
import com.lqs.kuaishou.kuaishou1801.home.presenter.HomePresenterImpl;

public class HomeFragment extends BaseMVPFragment<HomePresenterImpl, HomeContract.IHomeView> implements HomeContract.IHomeView, BaseRVAdapter.IRecyclerViewItemClickListener {
    private RecyclerView homeRv;
    private HomeAdapter homeAdapter;

    @Override
    protected void initHttpData() {
        ihttpPresenter.getHomeData();
    }

    @Override
    protected void initPresenter() {
        ihttpPresenter = new HomePresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        homeRv = findViewById(R.id.rv);
        homeRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        homeAdapter = new HomeAdapter();
        homeAdapter.setiRecyclerViewItemClickListener(this);
        homeRv.setAdapter(homeAdapter);
    }

    //返回网络请求数据
    @Override
    public void onHomeData(HomeBean homeBean) {
        printLog("获取到首页数据");
        homeAdapter.updataData(homeBean.getResult());
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
    }
}
