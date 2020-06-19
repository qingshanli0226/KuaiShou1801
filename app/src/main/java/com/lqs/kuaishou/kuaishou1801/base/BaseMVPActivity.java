package com.lqs.kuaishou.kuaishou1801.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseMVPActivity<T extends IPresenter, V extends IView> extends BaseActivity {

    protected T iHttpPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();
        iHttpPresenter.attachView((V)this);
        initData();
    }

    protected abstract void initPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        iHttpPresenter.detachView();
    }
}
