package com.lqs.kuaishou.kuaishou1801.live.presenter;

import com.lqs.kuaishou.kuaishou1801.live.contract.GetRtmpPushUrlContract;
import com.lqs.kuaishou.kuaishou1801.live.mode.PushRtmpBean;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetRtmpPushUrlPresenterImpl extends GetRtmpPushUrlContract.GetRtmpPushUrlPresenter {
    @Override
    public void getRtmpPushUrl(String userName) {

        HashMap<String, String> params = new HashMap<>();
        params.put("userName", userName);

        RetroCreator.getKSApiServie().getRtmpPushUrl(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PushRtmpBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PushRtmpBean pushRtmpBean) {
                        iHttpView.onGetRtmpPushUrl(pushRtmpBean);

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
