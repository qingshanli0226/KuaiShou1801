package com.lqs.kuaishou.kuaishou1801.home.presenter;

import com.lqs.kuaishou.kuaishou1801.home.contract.CityContract;
import com.lqs.kuaishou.kuaishou1801.home.contract.HomeContract;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CityPresenterImpl extends CityContract.CityPrensenter {

    @Override
    public void getCityData() {

        RetroCreator.getKSApiServie()
                .getCityData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CityBean cityBean) {
                        iHttpView.onCityData(cityBean);
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
