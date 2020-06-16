package com.lqs.kuaishou.kuaishou1801.home.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;

public class HomeContract {

    public interface IHomeView extends IView {
        void onHomeData(HomeBean homeBean);
    }

    public static abstract class HomePrensenter extends BasePresenter<IHomeView> {
        protected abstract void getHomeData();//定义home页面的获取数据的方法
    }
}
