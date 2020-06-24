package com.lqs.kuaishou.kuaishou1801.player.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
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
    private Point screenSize;

    private RecyclerView commentRv;
    private CommentAdapter commentAdapter;
    private int count;
    private ImageView likeImage;

    private float[] currentPosition = new float[2];
    private PathMeasure pathMeasure;//通过它可以逐个找到曲线的坐标
    private ImageView likeAnimImage;

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

        //清屏的面积
        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(screenSize);

        commentRv = findViewById(R.id.commentRv);
        commentRv.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter();
        commentRv.setAdapter(commentAdapter);

        likeImage = findViewById(R.id.likeImage);
        rootView = findViewById(R.id.rootView);
    }


    //显示点赞动画，使用贝塞尔曲线来完成

    private void showLikeAnim() {

        likeAnimImage = new ImageView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(50, 50);
        likeAnimImage.setLayoutParams(layoutParams);
        likeAnimImage.setImageResource(R.mipmap.like);
        rootView.addView(likeAnimImage);//把它添加到根布局中


        //把点赞图片的位置作为贝塞尔曲线的起始坐标
        int[] startLocation = new int[2];
        likeImage.getLocationInWindow(startLocation);//获取控件在窗口的坐标

        //确定贝塞尔曲线的终点坐标
        int[] endLoacation = new int[2];
        endLoacation[0] = startLocation[0] - 200;
        endLoacation[1] = startLocation[1] - 600;

        //确定贝塞尔曲线的控制1点的坐标
        int[] control1 = new int[2];
        control1[0] = startLocation[0] - 400;
        control1[1] = startLocation[1] - 300;

        //确定贝塞尔曲线控制点2的坐标
        int[] control2 = new int[2];
        control2[0] = startLocation[0];
        control2[1] = startLocation[1] - 300;

        Path path = new Path();
        path.moveTo(startLocation[0], startLocation[1]);//从起始点开始
        path.cubicTo(control1[0], control1[1], control2[0],control2[1], endLoacation[0],endLoacation[1]);//使用三阶贝塞尔曲线

        pathMeasure = new PathMeasure(path, false);
        //使用属性动画，来产生组合动画，其中有平移，缩放，透明
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(10000);//持续5秒钟

        //动画执行时，该回调一直被被调用，直到动画运行结石手术。
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();//控件走的距离
                pathMeasure.getPosTan(value, currentPosition, null);//填充currentPostion
                likeAnimImage.setTranslationX(currentPosition[0]);//将控件的x坐标改为获取曲线的坐标
                likeAnimImage.setTranslationY(currentPosition[1]);

                //获取当前动画播放时间
                long time = valueAnimator.getCurrentPlayTime();
                //可以计算出当前动画播放的百分比
                float percent = (float) (time/10000.0);
                likeAnimImage.setScaleX(percent*2);
                likeAnimImage.setScaleY(percent*2);//根据这个百分比，将动画图片，逐渐变大，直到变为1为止
                likeAnimImage.setAlpha(1-percent);//开始动画图片是原色，动画结束时，动画图片变为透明
            }
        });

        valueAnimator.start();
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
                            handler.sendEmptyMessageDelayed(1, 500);//延迟200ms后，去停止播放

                        } else if (System.currentTimeMillis() - timeGap < 500) {//这是双击事件
                            //绘制红色的小红心
                            x = (int) event.getX();
                            y = (int) event.getY();
                            handler.removeMessages(1);//如果是双击则，不暂停播放
                            handler.sendEmptyMessage(3);
                            handler.sendEmptyMessageDelayed(2,1000);//500毫秒后，清空屏幕
                            showLikeAnim();

                            drawRedHeart();
                        } else {
                            timeGap = System.currentTimeMillis();
                            handler.sendEmptyMessageDelayed(1, 500);//延迟200ms后，去停止播放
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
                    break;
                case 3:
                    count++;
                    commentAdapter.addOneData(KsUserManager.getInstance().getUserName() + "给你点赞"+count+"了红心");
                    //列表在底部显示最新的一条消息,可以让列表向上滚动，并且显示最后一条数据
                    commentRv.smoothScrollToPosition(commentAdapter.getItemCount()-1);

                    break;
                    default:
                        break;
            }
        }
    };


    //清空屏幕的函数
    private void clearScreen() {
        //拿到画布
        CacheManager.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                Canvas canvas = redSufaceHolder.lockCanvas();
                Paint paint = new Paint();
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//请屏幕
                paint.setColor(Color.TRANSPARENT);

                //清屏的面积
                RectF rectF = new RectF(0, 0, screenSize.x, screenSize.y);
                canvas.drawRect(rectF,paint);//把屏幕清空
                redSufaceHolder.unlockCanvasAndPost(canvas);//释放画布
            }
        });
    }

    private void drawRedHeart() {
        CacheManager.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                Canvas canvas = redSufaceHolder.lockCanvas();
                Paint paint = new Paint();
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//请屏幕
                paint.setColor(Color.TRANSPARENT);

                RectF rectF = new RectF(0, 0, screenSize.x, screenSize.y);
                canvas.drawRect(rectF,paint);//把屏幕清空
                Bitmap redBitMap = BitmapFactory.decodeResource(FocusPlayerActivity.this.getResources(), R.mipmap.red);
                canvas.drawBitmap(redBitMap, x,y,null);//绘制红心
                redSufaceHolder.unlockCanvasAndPost(canvas);//释放画布
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
