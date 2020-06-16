package com.lqs.kuaishou.kuaishou1801.login.presenter;

import com.lqs.kuaishou.kuaishou1801.login.contract.LoginContract;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.net.KSApiService;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImpl extends LoginContract.LoginPresenter {


    @Override
    public void login(String userName, String password) {
        HashMap<String,String> params = new HashMap<>();
        params.put("name", userName);
        params.put("password", password);

        RetroCreator.getKSApiServie().login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        iHttpView.onLogin(loginBean);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
