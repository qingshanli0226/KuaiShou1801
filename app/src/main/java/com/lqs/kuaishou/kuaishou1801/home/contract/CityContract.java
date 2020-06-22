package com.lqs.kuaishou.kuaishou1801.home.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;

public class CityContract {

    public interface ICityView extends IView {
        void onCityData(CityBean cityBean);
    }

    public static abstract class CityPrensenter extends BasePresenter<ICityView> {
        protected abstract void getCityData();//定义home页面的获取数据的方法
    }
}
