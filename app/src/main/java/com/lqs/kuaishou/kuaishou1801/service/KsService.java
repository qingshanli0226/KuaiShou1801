package com.lqs.kuaishou.kuaishou1801.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.cache.mode.KsMessageBean;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchRecommendBean;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
                            PopupWindow popupWindow = new PopupWindow(new TextView(KsService.this), 100,100);
                            Toast.makeText(KsService.this, "service 弹吐司:自动登录成功",Toast.LENGTH_SHORT).show();
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

    public void getKsMessage() {
        RetroCreator.getKSApiServie().getKsMessage()
                .delay(5,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<KsMessageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KsMessageBean ksMessageBean) {
                        if (ksMessageBean.getCode() == 200) {
                            CacheManager.getInstance().saveKsMessage(ksMessageBean);
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


    //起异步线程来从数据库中获取历史搜索记录
    public void getSearchHistory() {
        CacheManager.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                 CacheManager.getInstance().querySearchHistory();
            }
        });
    }

    //从服务端获取搜索页面的推荐列表数据
    public void getSearchRecommend() {

        RetroCreator.getKSApiServie().getSearchRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<SearchRecommendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchRecommendBean searchRecommendBean) {
                        //在内存中缓存下
                        CacheManager.getInstance().setSearchRecommendBean(searchRecommendBean);
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
