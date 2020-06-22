package com.lqs.kuaishou.kuaishou1801.player.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;

public class PlayerActivity extends BaseActivity implements View.OnClickListener {
    private PlayerView player;

    @Override
    protected void initData() {
    }


    @Override
    public void pause() {
        super.pause();
        if (player!=null) {
            player.pausePlay();
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (player!=null) {
            player.startPlay();
        }
    }

    @Override
    protected void destroy() {
        super.destroy();
        if (player!=null) {
            player.onDestroy();
        }
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String videoUrl = bundle.getString(KSConstant.PLAYER_VIDEO_URL);

        findViewById(R.id.likeBtn).setOnClickListener(this);

        player = new PlayerView(this)
                .hideHideTopBar(true)
                .hideBack(true)
                .hideMenu(true)
                .forbidTouch(false)
                .setPlaySource(videoUrl)
                .startPlay();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_player;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.likeBtn:
                showMessage("点赞了");
                break;
                default:
                    break;
        }
    }
}
