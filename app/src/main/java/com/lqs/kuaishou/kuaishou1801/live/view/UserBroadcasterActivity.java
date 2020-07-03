package com.lqs.kuaishou.kuaishou1801.live.view;

import android.view.View;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPActivity;
import com.lqs.kuaishou.kuaishou1801.live.contract.GetRtmpPushUrlContract;
import com.lqs.kuaishou.kuaishou1801.live.mode.PushRtmpBean;
import com.lqs.kuaishou.kuaishou1801.live.presenter.GetRtmpPushUrlPresenterImpl;
import com.lqs.kuaishou.kuaishou1801.login.view.LoginRegisterActivity;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class UserBroadcasterActivity extends BaseMVPActivity<GetRtmpPushUrlPresenterImpl, GetRtmpPushUrlContract.IGetRtmpPushUrlView> implements GetRtmpPushUrlContract.IGetRtmpPushUrlView,View.OnClickListener {
    private TXLivePusher txLivePusher;//腾讯框架提供的控件，用它可以实现推流直播
    private TXLivePushConfig txLivePushConfig;
    private TXCloudVideoView txCloudVideoView;
    private String rtmpPushUrl;



    @Override
    protected void initPresenter() {
        iHttpPresenter = new GetRtmpPushUrlPresenterImpl();

    }

    @Override
    protected void initData() {
        //先去判断有没有登录
        if (KsUserManager.getInstance().isUserLogin()) {
            iHttpPresenter.getRtmpPushUrl(KsUserManager.getInstance().getUserName());
        } else {
            launchActivity(LoginRegisterActivity.class, null);
        }
    }

    @Override
    protected void initView() {
        txCloudVideoView = findViewById(R.id.pusher_tx_cloud_view);//相机预览用的
        txLivePushConfig = new TXLivePushConfig();
        txLivePusher = new TXLivePusher(this);
        txLivePusher.setConfig(txLivePushConfig);//用config来初始化txPusher

        txLivePusher.startCameraPreview(txCloudVideoView);//开启预览

        findViewById(R.id.startLive).setOnClickListener(this);
        findViewById(R.id.stopLive).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_broadcaste;
    }

    @Override
    public void onGetRtmpPushUrl(PushRtmpBean pushRtmpBean) {
        rtmpPushUrl = pushRtmpBean.getResult();
        showMessage("获取推流地址成功" + rtmpPushUrl);
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
            case R.id.startLive:
                startLive();
                break;
            case R.id.stopLive:
                stopLive();
                break;
                default:
                    break;
        }
    }

    private void stopLive() {
        txLivePusher.stopPusher();
        txLivePusher.stopCameraPreview(true); //如果已经启动了摄像头预览，请在结束推流时将其关闭。
        showMessage("退出直播");
    }

    private void startLive() {
        if (rtmpPushUrl == null) {
            showMessage("当前推流地址为空");
        }

        int ret = txLivePusher.startPusher(rtmpPushUrl.trim());
        if (ret == -5) {
            showMessage("startRTMPPush: license 校验失败");
        } else {
            showMessage("直播成功");
        }
    }
}
