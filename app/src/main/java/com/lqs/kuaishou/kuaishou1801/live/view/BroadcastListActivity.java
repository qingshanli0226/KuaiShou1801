package com.lqs.kuaishou.kuaishou1801.live.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPActivity;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.live.contract.GetBroadcastListContract;
import com.lqs.kuaishou.kuaishou1801.live.mode.BroadcastListBean;
import com.lqs.kuaishou.kuaishou1801.live.presenter.GetBroadcastListPresenterImpl;
import com.lqs.kuaishou.kuaishou1801.player.view.GsyPlayerActivity;

public class BroadcastListActivity extends BaseMVPActivity<GetBroadcastListPresenterImpl, GetBroadcastListContract.IGetBroadcastListView> implements GetBroadcastListContract.IGetBroadcastListView {
    private RecyclerView broadcastRv;
    private BroadcastListAdapter broadcastListAdapter;

    @Override
    protected void initPresenter() {
        iHttpPresenter = new GetBroadcastListPresenterImpl();

    }

    @Override
    protected void initData() {
        iHttpPresenter.getBroadcastList();
    }

    @Override
    protected void initView() {
        broadcastRv = findViewById(R.id.broadcastRv);
        broadcastListAdapter = new BroadcastListAdapter();
        broadcastRv.setLayoutManager(new LinearLayoutManager(this));
        broadcastRv.setAdapter(broadcastListAdapter);

        broadcastListAdapter.setiRecyclerViewItemClickListener(new BaseRVAdapter.IRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //去一个页面去看直播
                String videoUrl = broadcastListAdapter.getItemData(position).getLivePlayUrl();
                printLog("rtmp playUrl: " + videoUrl);
                Bundle bundle = new Bundle();
                bundle.putString(KSConstant.PLAYER_VIDEO_URL, videoUrl);
                launchActivity(GsyPlayerActivity.class, bundle);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_broadcastlist;
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
    public void onGetBroadcastList(BroadcastListBean broadcastListBean) {
        broadcastListAdapter.updataData(broadcastListBean.getResult());
    }
}
