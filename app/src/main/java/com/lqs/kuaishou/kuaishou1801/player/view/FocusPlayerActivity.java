package com.lqs.kuaishou.kuaishou1801.player.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
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

public class FocusPlayerActivity extends BaseMVPActivity<GiftPresenterImpl, GiftContract.IGiftView> implements View.OnClickListener,GiftContract.IGiftView, SurfaceHolder.Callback {

    private KsGsyVideoPlayer videoPlayer;
    private String videoUrl;

    private RelativeLayout rootView;
    private SurfaceHolder redSufaceHolder;
    private SurfaceView redS;
    private long timeGap = 0;
    private int x, y;

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

        redS = videoPlayer.getRedSurfaceView();
        redS.getHolder().addCallback(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_focus_gsy_player;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showGift:
                break;
                default:
                    break;
        }
    }

    @Override
    public void onGift(GiftBean giftBean) {

    }

    @Override
    public void onMoney(MoneyBean moneyBean) {

    }

    @Override
    public void onOrderInfo(OrderInfoBean orderInfoBean) {

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
    public void surfaceCreated(SurfaceHolder holder) {
        redSufaceHolder = holder;

        //获取点击的坐标
        redS.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (timeGap == 0) {
                            timeGap = System.currentTimeMillis();
                            handler.sendEmptyMessageDelayed(1, 200);//延迟200ms后，去停止播放

                        } else if (System.currentTimeMillis() - timeGap < 200) {//这是双击事件
                            //绘制红色的小红心
                            x = (int) event.getX();
                            y = (int) event.getY();
                            handler.removeMessages(1);//如果是双击则，不暂停播放
                            handler.sendEmptyMessageDelayed(2,500);//500毫秒后，清空屏幕

                            drawRedHeart();
                        } else {
                            timeGap = System.currentTimeMillis();
                            handler.sendEmptyMessageDelayed(1, 200);//延迟200ms后，去停止播放
                        }

                        break;
                }


                return true;
            }
        });


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    videoPlayer.onVideoPause();
                    break;
                case 2:
                    clearScreen();
                    default:
                        break;
            }
        }
    };


    //清空屏幕的函数
    private void clearScreen() {
        //拿到画布
        Canvas canvas = redSufaceHolder.lockCanvas();
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//请屏幕
        paint.setColor(Color.TRANSPARENT);

        //清屏的面积
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(screenSize);
        RectF rectF = new RectF(0, 0, screenSize.x, screenSize.y);
        canvas.drawRect(rectF,paint);//把屏幕清空
        redSufaceHolder.unlockCanvasAndPost(canvas);//释放画布
    }

    private void drawRedHeart() {
        Canvas canvas = redSufaceHolder.lockCanvas();
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//请屏幕
        paint.setColor(Color.TRANSPARENT);

        //清屏的面积
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(screenSize);
        RectF rectF = new RectF(0, 0, screenSize.x, screenSize.y);
        canvas.drawRect(rectF,paint);//把屏幕清空
        Bitmap redBitMap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.red);
        canvas.drawBitmap(redBitMap, x,y,null);//绘制红心
        redSufaceHolder.unlockCanvasAndPost(canvas);//释放画布
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
