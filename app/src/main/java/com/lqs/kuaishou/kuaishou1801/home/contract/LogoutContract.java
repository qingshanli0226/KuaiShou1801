package com.lqs.kuaishou.kuaishou1801.home.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.home.mode.LogoutBean;

public class LogoutContract {

    public interface ILogoutView extends IView {
        void onLogout(LogoutBean logoutBean);
    }


    public static abstract class LogoutPresenter extends BasePresenter<ILogoutView> {

        public abstract void logout();

    }
}
