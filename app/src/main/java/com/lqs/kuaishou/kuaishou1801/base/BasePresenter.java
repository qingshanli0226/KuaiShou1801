package com.lqs.kuaishou.kuaishou1801.base;

//该基类的作用：管理UI的回调接口的。定义它，可以避免写大量重复代码
public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected V iHttpView;

    @Override
    public void attachView(V iHttpView) {
        this.iHttpView = iHttpView;
    }

    @Override
    public void detachView() {
        this.iHttpView = null;
    }
}
