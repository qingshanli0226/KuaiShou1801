package com.lqs.kuaishou.kuaishou1801.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

//定义Activity的基类，在里面定义抽象方法，抽象方法按照一定时序调用。并且在基类中定义方法，让子类复用
public abstract class BaseActivity extends AppCompatActivity {

    private String TAG;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏是白底黑色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(getLayoutId());

        initView();
        TAG = "LQS:" + getClass().getSimpleName();

    }

    //子类需要实现的抽象方法
    protected abstract void initData();
    protected abstract void initView();
    protected abstract int getLayoutId();

    protected void printLog(String message) {
        Log.d(TAG, message);
    }

    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void launchActivity(Class launcActivityClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra("param", bundle);
        intent.setClass(this, launcActivityClass);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
        printLog("onDestroy..............");
    }

    protected void destroy() {

    }
}
