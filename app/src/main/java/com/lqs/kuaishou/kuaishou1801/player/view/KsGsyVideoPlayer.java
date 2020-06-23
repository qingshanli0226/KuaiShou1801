package com.lqs.kuaishou.kuaishou1801.player.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.lqs.kuaishou.kuaishou1801.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

//集成库的播放器，改变里面的布局文件
public class KsGsyVideoPlayer extends StandardGSYVideoPlayer {
    private SurfaceView redSurfaceView;

    public KsGsyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public KsGsyVideoPlayer(Context context) {
        super(context);
    }

    public KsGsyVideoPlayer(final Context context, AttributeSet attrs) {
        super(context, attrs);
        redSurfaceView = findViewById(R.id.redSurfaceView);
        redSurfaceView.setZOrderOnTop(true);
        redSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    public SurfaceView getRedSurfaceView() {
        return redSurfaceView;
    }


    @Override
    public int getLayoutId() {
        return R.layout.player_ks_gsy;//复制库的布局文件，添加surfaceView
    }
}
