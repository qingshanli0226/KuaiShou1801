package com.lqs.kuaishou.kuaishou1801.search;

import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;
import com.lqs.kuaishou.kuaishou1801.search.contract.SearchContract;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchResultBean;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenterImpl extends SearchContract.SearchPresenter {
    @Override
    public void searchData(String rId) {


        HashMap<String,String> parmas = new HashMap<>();
        parmas.put("rId", rId);

        RetroCreator.getKSApiServie().searchRecommend(parmas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchResultBean searchResultBean) {
                        iHttpView.onSearch(searchResultBean);

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
