package com.lqs.kuaishou.kuaishou1801.login.presenter;

import com.lqs.kuaishou.kuaishou1801.common.KSConstant;
import com.lqs.kuaishou.kuaishou1801.login.contract.LoginContract;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.net.KSApiService;
import com.lqs.kuaishou.kuaishou1801.net.RetroCreator;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

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
                        if (loginBean.getCode().equals("200")) {//正确登录
                            iHttpView.onLogin(loginBean);
                        } else {
                            iHttpView.showError(loginBean.getCode(), loginBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof IllegalStateException) {
                            iHttpView.showError(KSConstant.JSCON_ERROR_CODE, KSConstant.JSON_ERROR_MESSAGE);
                        } else if (e instanceof HttpException) {
                            iHttpView.showError(KSConstant.HTTP_ERROR_CODE,KSConstant.HTTP_ERROR_MESSAGE);
                        } else if (e instanceof SecurityException) {
                            iHttpView.showError(KSConstant.SECURITY_ERROR_CODE,KSConstant.SECURITY_ERROR_MESSAGE);
                        } else if (e instanceof SocketTimeoutException) {
                            iHttpView.showError(KSConstant.SOCKET_TIMEOUT_ERROR_CODE,KSConstant.SOCKET_TIMEOUT_ERROR_MESSAGE);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
