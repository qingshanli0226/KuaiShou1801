package com.lqs.kuaishou.kuaishou1801.player.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPActivity;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.lqs.kuaishou.kuaishou1801.player.GiftContract;
import com.lqs.kuaishou.kuaishou1801.player.mode.GiftBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.MoneyBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.OrderInfoBean;
import com.lqs.kuaishou.kuaishou1801.player.presenter.GiftPresenterImpl;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Map;

public class GsyPlayerActivity extends BaseMVPActivity<GiftPresenterImpl, GiftContract.IGiftView> implements GiftContract.IGiftView, View.OnClickListener {

    private StandardGSYVideoPlayer videoPlayer;
    private String videoUrl;

    private GiftBean giftBean;
    private PopupWindow ksGiftPopupWindow;
    private RecyclerView giftRv;
    private GiftAdapter giftAdapter;
    private ImageView giftAnimImg;

    private RelativeLayout rootView;
    private int addMoneyValue;

    @Override
    protected void initData() {
        iHttpPresenter.getGift();
    }

    @Override
    protected void initView() {
        videoPlayer = findViewById(R.id.video_player);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        videoUrl = bundle.getString(KSConstant.PLAYER_VIDEO_URL);
        videoPlayer.setUp(videoUrl, true, "");
        videoPlayer.getBackButton().setVisibility(View.GONE);

        videoPlayer.startPlayLogic();
        
        findViewById(R.id.showGift).setOnClickListener(this);
        giftAnimImg = findViewById(R.id.animImg);
        rootView = findViewById(R.id.rootView);

        initGiftPopupWindow();

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//设置沙箱环境.


        //初始化surfaceView
        SurfaceView redS = findViewById(R.id.redS);
        redS.setZOrderOnTop(true);//将surfaceView放在上边
        redS.getHolder().setFormat(PixelFormat.TRANSPARENT);//背景透明色
        redS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("你点击了surfaceView");
            }
        });
    }

    private void initGiftPopupWindow() {

        View giftPopupView = getLayoutInflater().inflate(R.layout.popupwindow_gift, null, false);
        ksGiftPopupWindow = new PopupWindow(giftPopupView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        giftRv = giftPopupView.findViewById(R.id.giftRv);
        giftAdapter = new GiftAdapter();
        giftRv.setLayoutManager(new GridLayoutManager(this, 4));
        giftRv.setAdapter(giftAdapter);

        //获取焦点
        ksGiftPopupWindow.setFocusable(true);
        ksGiftPopupWindow.setOutsideTouchable(true);//代表popup显示时，点击界面其他地方，会关闭PopupWindow
        ksGiftPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//将背景设置成透明

        giftAdapter.setiRecyclerViewItemClickListener(new BaseRVAdapter.IRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //展示礼物动画
                //showGiftAnim(position);
                //第一步检查虚拟币是否充足
                int value = checkHasEnoughMoney(position);
                if (value>=0) {//虚拟币充足
                    showMessage("虚拟充足，调用服务端扣除礼物的价格");
                    iHttpPresenter.updateMoney(String.valueOf(value));
                    showGiftAnim(position);//播放礼物动画
                } else {
                    showMessage("虚拟不充足，调用支付宝发起支付请求,先生成订单");
                    addMoneyValue = value*-1;
                    String totalPrice = String.valueOf(value*-1);
                    iHttpPresenter.getOrderInfo(totalPrice);
                }

            }
        });

    }

    //当返回值大于0时，代表的是钱充足，否则不充足
    private int checkHasEnoughMoney(int position) {
        int giftValue = Integer.valueOf(giftAdapter.getItemData(position).getPrice());
        int moneyValue = KsUserManager.getInstance().getMoneyValue();
        return moneyValue-giftValue;
    }

    //去播放发送礼物的礼物动画
    private void showGiftAnim(int position) {
        String giftAnimUrl = giftBean.getResult().get(position).getGif_file();

        giftAnimImg.setVisibility(View.VISIBLE);
        ksGiftPopupWindow.dismiss();

        Glide.with(this).load(KSConstant.BASE_RESOURCE_URL+giftAnimUrl).into(giftAnimImg);

        handler.sendEmptyMessageDelayed(1, 10000);//动画只显示5秒钟

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    giftAnimImg.setVisibility(View.GONE);
                    break;

                    default:
                        break;
            }


        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gsy_player;
    }

    @Override
    public void pause() {
        super.pause();
        videoPlayer.onVideoPause();
    }

    @Override
    public void resume() {
        super.resume();
        videoPlayer.onVideoResume();
    }

    @Override
    public void destroy() {
        super.destroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new GiftPresenterImpl();
    }

    @Override
    public void onGift(GiftBean giftBean) {
        showMessage("获取到礼物");
        this.giftBean = giftBean;
        giftAdapter.updataData(giftBean.getResult());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showGift:
                showGiftPopupWindow();
                break;
                default:
                    break;
        }
    }

    private void showGiftPopupWindow() {
        ksGiftPopupWindow.showAtLocation(rootView,Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onMoney(MoneyBean moneyBean) {
        //先把缓存的数据更新一下
        KsUserManager.getInstance().updateMoney(moneyBean.getResult());
        showMessage("虚拟币已跟新,最新的虚拟币是:" + moneyBean.getResult());
    }


    //订单信息服务端返回，使用支付宝API，发起购买
    @Override
    public void onOrderInfo(final OrderInfoBean orderInfoBean) {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 PayTask payTask = new PayTask(GsyPlayerActivity.this);
                 Map<String,String> result = payTask.payV2(orderInfoBean.getResult().getOrderInfo(),true);
                 if (result.get("resultStatus").equals("9000")) {//购买成功
                     //再次更新虚拟币
                     int oldMoneyValue = KsUserManager.getInstance().getMoneyValue();
                     iHttpPresenter.updateMoney(String.valueOf(oldMoneyValue+addMoneyValue));
                 }
             }
         }).start();
    }
}
