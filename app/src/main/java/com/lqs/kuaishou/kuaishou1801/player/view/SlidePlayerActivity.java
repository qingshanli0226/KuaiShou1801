package com.lqs.kuaishou.kuaishou1801.player.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dou361.ijkplayer.widget.IjkVideoView;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.shuyu.gsyvideoplayer.GSYVideoADManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;


//滑动播放的页面
public class SlidePlayerActivity extends BaseActivity {

    private RecyclerView slideRv;
    private SlidePlayerAdapter slideVideoViewAdapter;
    private int currentPosition;//当前真正播放的位置信息
    private StandardGSYVideoPlayer currentStandardGSYVideoPlayer;

    private List<CityBean.ResultBean> beanList;
    @Override
    protected void initData() {
//不要去写
    }

    @Override
    protected void initView() {

        initSlideRv();
        Bundle bundle = getIntent().getExtras();
        beanList = bundle.getParcelableArrayList(KSConstant.PLAYER_VIDEO_LIST);//获取视频列表
        currentPosition = bundle.getInt(KSConstant.PLAYER_VIDEO_POSITION);//获取点击的位置
        slideVideoViewAdapter.updataData(beanList);//将数据放到列表中

        slideRv.scrollToPosition(currentPosition);
        slideRv.postDelayed(new Runnable() {
            @Override
            public void run() {
                playVideoByPosition(currentPosition);
            }
        },2000);//需要延迟，因为列表先滑动到指定的位置,滑动到指定的位置后进行播放

    }


    //初始化播放列表
    private void initSlideRv() {
        slideRv = findViewById(R.id.slideRV);
        slideRv.setLayoutManager(new LinearLayoutManager(this));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(slideRv);//将两者关联起来,确保列表滑动时，一次只能滑动一个Item
        slideVideoViewAdapter = new SlidePlayerAdapter();
        slideRv.setAdapter(slideVideoViewAdapter);

        //设置监听当前列表滚动的监听器,通过这个监听器，可以获取当前列表显示的位置信息，ItemView信息
        slideRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) slideRv.getLayoutManager();
                int newPosition = linearLayoutManager.findFirstVisibleItemPosition();//获取新滑动到的Item对应的位置
                if (newPosition != currentPosition) {//如果新滚动的位置大于当前播放的位置，这个代表的意思是往上滚动列表，滑动下一个位置：
                     if (newState == RecyclerView.SCROLL_STATE_IDLE) {//当你的列表停止滑动时,处于静止状态
                          playVideoByPosition(newPosition);
                     }
                }
            }
        });
    }

    //播放参数指定位置的视频
    private void playVideoByPosition(int playPosition) {

        //先停掉当前正在播放的视频
        if (currentStandardGSYVideoPlayer!=null) {
            GSYVideoADManager.releaseAllVideos();//停止播放
            currentStandardGSYVideoPlayer.setVisibility(View.GONE);//将当前的播放器隐藏显示
            currentStandardGSYVideoPlayer = null;
        }

        //第一步拿到播放器
        View itemView = slideRv.getLayoutManager().findViewByPosition(playPosition);//根据位置找到列表的itemView
        StandardGSYVideoPlayer gsyVideoPlayer = itemView.findViewById(R.id.gsyPlayer);//获取播放器
        gsyVideoPlayer.setVisibility(View.VISIBLE);
        String videoUrl = KSConstant.BASE_RESOURCE_URL+slideVideoViewAdapter.getItemData(playPosition).getVedioUrl();
        gsyVideoPlayer.setUp(videoUrl, true,"");
        gsyVideoPlayer.startPlayLogic();
        GSYVideoManager.instance().getCurPlayerManager().setNeedMute(true);

        currentStandardGSYVideoPlayer = gsyVideoPlayer;
        currentPosition = playPosition;

    }

    @Override
    public void pause() {
        super.pause();
        if (currentStandardGSYVideoPlayer!=null) {
            currentStandardGSYVideoPlayer.onVideoPause();
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (currentStandardGSYVideoPlayer!=null) {
            currentStandardGSYVideoPlayer.onVideoResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoADManager.releaseAllVideos();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slide_player;
    }



}
