package com.lqs.kuaishou.kuaishou1801.record;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.security.Permission;

public class KsRecordActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private MediaRecorder mediaRecorder;//Android提供一个录制视频的类
    private Camera camera;//操作摄像头硬件的类,手机上的相机应用也是使用这个类来开发的
    private SurfaceView previewSurface;//预览录制的视频内容
    private SurfaceHolder surfaceHolder;
    private File outFile;//输出文件
    private final String OUT_FILE_PAATH = "/sdcard/1801.mp4";

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        previewSurface = findViewById(R.id.previewSurfaceView);
        surfaceHolder = previewSurface.getHolder();
        surfaceHolder.addCallback(this);

        showMessage("surfaceCreated");

        findViewById(R.id.startRecord).setOnClickListener(this);
        findViewById(R.id.stopRecord).setOnClickListener(this);
        findViewById(R.id.playRecord).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ksrecord;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        showMessage("surfaceCreated");
        surfaceHolder = holder;//当页面显示时，我们先通过Camera类，实现相机预览功能
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//打开后置摄像头
        camera.setDisplayOrientation(90);//让摄像头按照竖屏感光
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startRecord:
                startRecord();
                break;
            case R.id.stopRecord:
                stopRecord();
                break;
            case R.id.playRecord:
                break;
                default:
                    break;
        }

    }
    private void stopRecord() {
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        camera.lock();//将相机资源还给系统

        camera.release();
        camera = null;

    }

    //开始录制
    private void startRecord() {
        if (camera!=null) {
            camera.lock();//当开始录制视频时，我们先把预览的相机功能先给它停掉
            camera.release();
        }
        mediaRecorder = new MediaRecorder();//实例化录制视频的类
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//打开后置摄像头
        camera.setDisplayOrientation(90);//让摄像头按照竖屏感光
        camera.unlock();//获取相机的使用权限
        mediaRecorder.setCamera(camera);//将录制视频的类和操作摄像头硬件的Camera类关联起来，之后录制视频时，会从Camera类中拿图像数据

        //初始化mediaRecorder
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置声音源
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//设置图像源
        //设置输出文件的格式
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        //设置音频编码器
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //设置视频编码器
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

        //设置产生视频的一帧图像大小
        mediaRecorder.setVideoSize(640, 480);
        //设置录制视频的帧率
        mediaRecorder.setVideoFrameRate(20);
        //设置码率
        mediaRecorder.setVideoEncodingBitRate(1*1024*1024);
        //设置录制预览翻转也是90度，按照竖屏预览
        mediaRecorder.setOrientationHint(90);
        //设置预览的surface
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());

        //设置产生文件的路径
        outFile = new File(OUT_FILE_PAATH);
        if (outFile.exists()) {
            outFile.delete();
        }
        try {
            outFile.createNewFile();//创建一个空白文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将文件路径放进去
        mediaRecorder.setOutputFile(outFile.getPath());


        //准备
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
