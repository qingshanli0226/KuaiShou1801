package com.lqs.kuaishou.kuaishou1801.player.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;

public class KsPlayerActivity extends AppCompatActivity {
    private PlayerView player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = getLayoutInflater().from(this).inflate(R.layout.simple_player_view_player, null);
        setContentView(rootView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String videoUrl = bundle.getString(KSConstant.PLAYER_VIDEO_URL);

        player = new PlayerView(this)
                .hideHideTopBar(true)
                .hideBack(true)
                .hideRotation(true)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .forbidTouch(false)
                .setPlaySource(videoUrl)
                .startPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pausePlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.onDestroy();
    }
}
