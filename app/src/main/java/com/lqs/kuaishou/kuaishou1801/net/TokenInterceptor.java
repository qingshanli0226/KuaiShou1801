package com.lqs.kuaishou.kuaishou1801.net;

import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//通过拦截器，在网络请求头上添加一些自己的东西
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request newRequest = request.newBuilder().addHeader("token", KsUserManager.getInstance().getToken()).build();

        return chain.proceed(newRequest);
    }
}
