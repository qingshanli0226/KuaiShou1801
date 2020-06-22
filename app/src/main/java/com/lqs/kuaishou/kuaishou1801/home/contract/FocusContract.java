package com.lqs.kuaishou.kuaishou1801.home.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.FocusBean;

public class FocusContract {

    public interface IFocusView extends IView {
        void onFocusData(FocusBean focusBean);
    }

    public static abstract class FocusPrensenter extends BasePresenter<IFocusView> {
        protected abstract void getFocusData();//定义home页面的获取数据的方法
    }
}
