package com.lqs.kuaishou.kuaishou1801.player.view;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationManager;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.home.MainActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class LittlePlayerActivity extends BaseActivity {

    private StandardGSYVideoPlayer standardGSYVideoPlayer;

    private WindowManager windowManager;//Android提供的管理窗口的窗口管理类

    private WindowManager.LayoutParams layoutParams;

    private View littleView;

    @Override
    protected void initData() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void initView() {
        standardGSYVideoPlayer = findViewById(R.id.gsyPlayer);
        standardGSYVideoPlayer.setUp("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8", true, "");
        standardGSYVideoPlayer.startPlayLogic();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);//实例化窗口管理类
    }

    @Override
    public void pause() {
        super.pause();
        standardGSYVideoPlayer.onVideoPause();
    }

    @Override
    public void resume() {
        super.resume();
        standardGSYVideoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //GSYVideoManager.releaseAllVideos();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_little_player;
    }




    //当页面点击返回键时，显示一个小窗口,
    private void showLittlePlayWindow() {
        //通过这个参数可以设置窗口的属性，包括它是那种类型的窗口
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//设置窗口的级别是系统级别，这样可以确保该窗口可以显示在任何应用的上方
        layoutParams.format = PixelFormat.TRANSPARENT;//背景透明色

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//这两个参数，可以确保我们当系统显示我们直播悬浮窗口时，后面的UI控件仍然可以点击.

        layoutParams.width  = 500;
        layoutParams.height = 300;

        littleView = getLayoutInflater().inflate(R.layout.window_little_player,null);
        //通过surfaceview来抢到事件处理权,如果不用surfaceView，播放器将处理事件，这个需求就无法实现了
        SurfaceView littleS = littleView.findViewById(R.id.littleS);
        littleS.setZOrderOnTop(true);
        littleS.getHolder().setFormat(PixelFormat.TRANSPARENT);
        littleS.setOnTouchListener(littleWindowTouchListener);//给小窗口的布局设置触摸事件的监听器

        windowManager.addView(littleView,layoutParams);//显示小窗口
        final StandardGSYVideoPlayer littlePlayer = littleView.findViewById(R.id.littleGsyPlayer);
        littlePlayer.setUp("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8", true,"");
        littlePlayer.startPlayLogic();

        littleView.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击叉号，将关闭小窗口
                GSYVideoManager.releaseAllVideos();//先停掉播放的直播
                windowManager.removeView(littleView);//关掉小窗口
            }
        });

    }

    //实现触摸事件的监听器，让我们的小窗口可以跟着手指滑动到固定的位置
    private View.OnTouchListener littleWindowTouchListener = new View.OnTouchListener() {
        float littleX=0, littleY = 0;
        float x = 0, y = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://当手指点击屏幕时，记录当前的手指点击的位置
                    x = event.getRawX();//记录当前手指点击位置的绝对坐标
                    y = event.getRawY();
                    littleX = layoutParams.x;
                    littleY = layoutParams.y;//记录当前窗口所在的坐标
                    printLog("ACTION_DOWN x " + x + " y " + y + " littlex " + littleX + " littleY " + littleY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //手指滑动时，产生一个x，y轴的移动的移动的距离值
                    float dx = event.getRawX() - x;//手指在x轴移动的距离
                    float dy = event.getRawY() - y;//手指在y轴上移动的距离
                    layoutParams.x = (int) (littleX+dx);//小窗口在x轴平移的距离和我们手指在x轴上平移的距离保持一致
                    layoutParams.y = (int) (littleY+dy);//小窗口在y轴平移的距离和我们手指在y轴上平移的距离保持一致
                    windowManager.updateViewLayout(littleView, layoutParams);//不停的刷新小窗口的位置
                    printLog("ACTION_DOWN x " + x + " y " + y + " layoutParams.x " + layoutParams.x + " layoutParams.y " + layoutParams.y);

                    break;
                case MotionEvent.ACTION_UP:
                    break;
                    default:
                        break;
            }


            return true;
        }
    };

    //当点击系统返回键时，调用显示小窗口的方法。那么点击系统返回键时需要重写那个方法?

    @Override
    public void onBackPressed() {
        showLittlePlayWindow();
        super.onBackPressed();
    }
}

