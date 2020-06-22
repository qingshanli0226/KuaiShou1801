package com.lqs.kuaishou.kuaishou1801.home.presenter;

import com.lqs.kuaishou.kuaishou1801.home.contract.CityContract;
import com.lqs.kuaishou.kuaishou1801.home.contract.FocusContract;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.FocusBean;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FocusPresenterImpl extends FocusContract.FocusPrensenter {

    @Override
    public void getFocusData() {

        RetroCreator.getKSApiServie()
                .getFocusData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FocusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FocusBean focusBean) {
                        iHttpView.onFocusData(focusBean);
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
