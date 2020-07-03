package com.lqs.kuaishou.kuaishou1801.live.presenter;

import com.lqs.kuaishou.kuaishou1801.live.contract.GetBroadcastListContract;
import com.lqs.kuaishou.kuaishou1801.live.mode.BroadcastListBean;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetBroadcastListPresenterImpl extends GetBroadcastListContract.GetBroadcastListPresenter {
    @Override
    public void getBroadcastList() {
        RetroCreator.getKSApiServie().getBroadcastList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BroadcastListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BroadcastListBean broadcastListBean) {
                        iHttpView.onGetBroadcastList(broadcastListBean);

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
