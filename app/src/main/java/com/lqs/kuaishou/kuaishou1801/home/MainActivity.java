package com.lqs.kuaishou.kuaishou1801.home;



import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseActivity;
import com.lqs.kuaishou.kuaishou1801.base.BaseMVPActivity;
import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.cache.mode.KsMessage;
import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.history.HistoryActivity;
import com.lqs.kuaishou.kuaishou1801.home.contract.LogoutContract;
import com.lqs.kuaishou.kuaishou1801.home.mode.LogoutBean;
import com.lqs.kuaishou.kuaishou1801.home.presenter.LogoutPresenterImpl;
import com.lqs.kuaishou.kuaishou1801.home.view.MainFragmentAdapter;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.login.view.LoginRegisterActivity;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;

import java.util.List;

public class MainActivity extends BaseMVPActivity<LogoutPresenterImpl, LogoutContract.ILogoutView> implements View.OnClickListener, KsUserManager.ILoginStatusChangeListener, LogoutContract.ILogoutView, CacheManager.IMessageChanged {

    private TextView loginTv;
    private ImageView menuImage;
    private SlidingMenu slidingMenu;

    private ImageView avatarImg;
    private TextView nameTv;
    private TextView logoutTv;

    private TextView messageCountTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initViewPager();

        loginTv = findViewById(R.id.btnLogin);
        menuImage = findViewById((R.id.btnMenu));
        loginTv.setOnClickListener(this);
        menuImage.setOnClickListener(this);

        initSlideMenu();
        //判断是否登录
        updateUIAccordingLoginStatus();

        KsUserManager.getInstance().setLoginStatusChangeListener(this);

        CacheManager.getInstance().setiMessageChanged(this);
    }

    private void initSlideMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMenu(R.layout.slidemenu_main);

        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffset(300);

        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);

        avatarImg = slidingMenu.getMenu().findViewById(R.id.avatar);
        nameTv = slidingMenu.getMenu().findViewById(R.id.name);
        logoutTv = slidingMenu.getMenu().findViewById(R.id.logout);
        logoutTv.setOnClickListener(this);
        messageCountTv = slidingMenu.getMenu().findViewById(R.id.messageCount);
        slidingMenu.getMenu().findViewById(R.id.status).setOnClickListener(this);
        messageCountTv.setText("消息:"+CacheManager.getInstance().getKsMessageCount()+"");
    }

    //根据当前用户登录状态刷新UI
    private void updateUIAccordingLoginStatus() {
        //判断是否登录,如果登录
        if (KsUserManager.getInstance().isUserLogin()) {
            loginTv.setVisibility(View.GONE);
            menuImage.setVisibility(View.VISIBLE);
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            nameTv.setText(KsUserManager.getInstance().getUserName());
            Glide.with(this).load(KSConstant.BASE_RESOURCE_URL+(KsUserManager.getInstance().getUserAvatar()))
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatarImg);
        } else {
            loginTv.setVisibility(View.VISIBLE);
            menuImage.setVisibility(View.GONE);
            if (slidingMenu.isMenuShowing()) {
                slidingMenu.showContent();
            }
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    private void initViewPager() {
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);//默认显示首页
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
                break;
            case R.id.logout:
                //退出登录
                logout();
                break;
            case R.id.btnMenu:
                if (slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else {
                    slidingMenu.showMenu();
                }
                break;
            case R.id.status:
                launchActivity(HistoryActivity.class, null);
                break;
                default:
                    break;
        }
    }

    private void logout() {
        iHttpPresenter.logout();
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        updateUIAccordingLoginStatus();
    }

    @Override
    public void onLogoutSuccess() {
        updateUIAccordingLoginStatus();
    }

    @Override
    protected void destroy() {
        super.destroy();
        KsUserManager.getInstance().removeLoginStatusChangeListener(this);//避免内存泄漏
        CacheManager.getInstance().setiMessageChanged(null);
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new LogoutPresenterImpl();
    }

    @Override
    public void onLogout(LogoutBean logoutBean) {
        showMessage("成功退出登录");
        //缓存的处理
        //1,改变当前应用程序的在内存中的登录状态，登录状态，由已登录状态，变为未登录状态   2,把token清除，禁止下次应用程序冷启动时实现自动登录
        KsUserManager.getInstance().processLogout();
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoaing() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onMessageChange(List<KsMessage> ksMessageList, final int ksMessageCount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageCountTv.setText("消息:"+ksMessageCount);

            }
        });
    }
}
