package com.lqs.kuaishou.kuaishou1801.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//定义抽象的MVPFragment类，使用这个类代表的是该Fragment是需要网络请求数据.
public abstract class BaseMVPFragment<T extends IPresenter, V extends IView> extends BaseFragment {

    protected T ihttpPresenter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPresenter();
        ihttpPresenter.attachView((V)this);
        initHttpData();
    }

    protected abstract void initHttpData();

    protected abstract void initPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        ihttpPresenter.detachView();
    }
}
