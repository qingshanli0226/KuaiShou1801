package com.lqs.kuaishou.kuaishou1801.home.presenter;

import com.lqs.kuaishou.kuaishou1801.home.contract.LogoutContract;
import com.lqs.kuaishou.kuaishou1801.home.mode.LogoutBean;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LogoutPresenterImpl extends LogoutContract.LogoutPresenter {
    @Override
    public void logout() {


        RetroCreator.getKSApiServie().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LogoutBean logoutBean) {

                        if (logoutBean.getCode().equals("200")) {
                            iHttpView.onLogout(logoutBean);
                        }

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
