package com.lqs.kuaishou.kuaishou1801.home;



import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.home.view.MainFragmentAdapter;
import com.lqs.kuaishou.kuaishou1801.login.view.LoginRegisterActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        initViewPager();

        findViewById(R.id.btnLogin).setOnClickListener(this);
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
                break;
                default:
                    break;
        }
    }
}
