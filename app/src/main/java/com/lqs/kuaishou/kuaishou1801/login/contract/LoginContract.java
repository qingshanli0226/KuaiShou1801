package com.lqs.kuaishou.kuaishou1801.login.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;

public class LoginContract {

    public interface ILoginView extends IView {
        void onLogin(LoginBean loginBean);
    }


    public static abstract class LoginPresenter extends BasePresenter<ILoginView> {
        public abstract void login(String userName, String password);
    }
}
