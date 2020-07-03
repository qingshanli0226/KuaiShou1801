package com.lqs.kuaishou.kuaishou1801.record;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.TextureView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;

import java.io.IOException;

public class TextureViewActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView textureView;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        textureView = findViewById(R.id.textureView);

        //类似于SurfaceView，它也是异步创建的，所以我们需要设置一个回调函数，等待它创建完毕
        textureView.setSurfaceTextureListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_textureview;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        //代表TextureView已经准备好了，可以使用了
        Camera camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        camera.setDisplayOrientation(90);
        //实现一个相机预览
        try {
            camera.setPreviewTexture(surface);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
