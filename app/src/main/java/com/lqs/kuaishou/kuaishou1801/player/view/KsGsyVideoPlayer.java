package com.lqs.kuaishou.kuaishou1801.player.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.lqs.kuaishou.kuaishou1801.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class KsGsyVideoPlayer extends StandardGSYVideoPlayer {
    public KsGsyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public KsGsyVideoPlayer(Context context) {
        super(context);
    }

    public KsGsyVideoPlayer(final Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceView redSurfaceView = findViewById(R.id.redSurfaceView);
        redSurfaceView.setZOrderOnTop(true);
        redSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        redSurfaceView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击了红色", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test_player;
    }
}
