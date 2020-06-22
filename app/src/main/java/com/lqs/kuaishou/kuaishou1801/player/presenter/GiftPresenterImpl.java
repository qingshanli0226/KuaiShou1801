package com.lqs.kuaishou.kuaishou1801.player.presenter;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;
import com.lqs.kuaishou.kuaishou1801.player.GiftContract;
import com.lqs.kuaishou.kuaishou1801.player.mode.GiftBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.MoneyBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.OrderInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GiftPresenterImpl extends GiftContract.GiftPresenter {
    @Override
    public void getGift() {
        RetroCreator.getKSApiServie().getGift()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiftBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GiftBean giftBean) {
                        if (giftBean.getCode() == 200) {
                            iHttpView.onGift(giftBean);
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

    @Override
    public void updateMoney(String money) {

        HashMap<String,String> params = new HashMap<>();
        params.put("money", money);
        RetroCreator.getKSApiServie().updateMoney(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoneyBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        //代表虚拟币已经更新了
                        iHttpView.onMoney(moneyBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getOrderInfo(String totalPrice) {
        JSONArray jsonArray = new JSONArray();
        //用数组来存储购买的产品信息,使用JsonArray，是因为可能购买多个产品
        JSONObject object = new JSONObject();
        try {
            object.put("productName", "kuaishou");
            object.put("productId", "100010");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(object);//把信息添加到列表中

        //存放购买的原因，价格，以及上面生成的产品信息
        JSONObject subjectObject = new JSONObject();
        try {
            subjectObject.put("subject", "buy");
            subjectObject.put("totalPrice", totalPrice);
            subjectObject.put("body", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), subjectObject.toString());
        RetroCreator.getKSApiServie().getOrderInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderInfoBean orderInfoBean) {
                        iHttpView.onOrderInfo(orderInfoBean);
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
