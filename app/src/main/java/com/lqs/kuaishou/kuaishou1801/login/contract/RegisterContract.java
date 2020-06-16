package com.lqs.kuaishou.kuaishou1801.login.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.login.mode.RegisterBean;

public class RegisterContract {

    public interface IRegisterView extends IView {
        void onRegister(RegisterBean registerBean);
    }

    public static abstract class ReigsterPresenter extends BasePresenter<IRegisterView> {
        public abstract void register(String name, String password);
    }
}
