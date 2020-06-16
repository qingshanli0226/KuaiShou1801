package com.lqs.kuaishou.kuaishou1801.login.view;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;

public class LoginRegisterActivity extends BaseActivity {
    private ViewPager viewPager;
    private LoginRegisterAdapter loginRegisterAdapter;



    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        loginRegisterAdapter = new LoginRegisterAdapter(getSupportFragmentManager());
        viewPager.setAdapter(loginRegisterAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }


    //Activity提供方法，让Fragment调用，实现Fragment之间的跳转
    public void switchFragment(int index, String name) {
        viewPager.setCurrentItem(index);
        ((INameInterface)loginRegisterAdapter.getItem(index)).setName(name);
    }

    public interface INameInterface {
        void setName(String name);
    }

}
