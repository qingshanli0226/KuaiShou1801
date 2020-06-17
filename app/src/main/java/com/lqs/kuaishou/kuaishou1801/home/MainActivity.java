package com.lqs.kuaishou.kuaishou1801.home;



import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.home.view.MainFragmentAdapter;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.login.view.LoginRegisterActivity;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;

public class MainActivity extends BaseActivity implements View.OnClickListener, KsUserManager.ILoginStatusChangeListener {

    private TextView loginTv;
    private ImageView menuImage;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initViewPager();

        findViewById(R.id.btnLogin).setOnClickListener(this);


        loginTv = findViewById(R.id.btnLogin);
        menuImage = findViewById((R.id.btnMenu));
        //判断是否登录
        if (KsUserManager.getInstance().isUserLogin()) {
            loginTv.setVisibility(View.GONE);
            menuImage.setVisibility(View.VISIBLE);
        } else {
            loginTv.setVisibility(View.VISIBLE);
            menuImage.setVisibility(View.GONE);
        }

        KsUserManager.getInstance().setLoginStatusChangeListener(this);
    }

    private void initViewPager() {
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                //跳转到登录页面
                launchActivity(LoginRegisterActivity.class, null);
                //这个地方调用后，MainActivity有可能在应用内存紧张的情况下，MainActivity被系统回收
                finish();
                break;
                default:
                    break;
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        loginTv.setVisibility(View.GONE);
        menuImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLogoutSuccess() {
        loginTv.setVisibility(View.VISIBLE);
        menuImage.setVisibility(View.GONE);
    }

    @Override
    protected void destroy() {
        super.destroy();
        KsUserManager.getInstance().removeLoginStatusChangeListener(this);//避免内存泄漏
    }
}
