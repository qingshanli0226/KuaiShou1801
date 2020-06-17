package com.lqs.kuaishou.kuaishou1801.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class KsService extends Service {

    public class KsBinder extends Binder {
        public KsService getService() {
            return KsService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new KsBinder();
    }


    public void autoLogin(String tokenValue) {
        HashMap<String,String> params = new HashMap<>();
        params.put("token", tokenValue);
        RetroCreator.getKSApiServie().autoLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getCode().equals("200")) {
                            KsUserManager.getInstance().setLoginBean(loginBean);
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
