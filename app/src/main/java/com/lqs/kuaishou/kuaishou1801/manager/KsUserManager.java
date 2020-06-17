package com.lqs.kuaishou.kuaishou1801.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.service.KsService;

import java.util.LinkedList;
import java.util.List;

//使用单例来存储当前用户的状态
public class KsUserManager {
    private static KsUserManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String ksSp = "ksSp";
    private String tokenName = "tokenSp";

    private LoginBean loginBean;

    private List<ILoginStatusChangeListener> loginStatusChangeListeners = new LinkedList<>();


    private KsUserManager() {

    }


    public static KsUserManager getInstance() {
        if (instance==null) {
            instance = new KsUserManager();
        }

        return instance;
    }

    public void init(Context context) {

        sharedPreferences = context.getSharedPreferences(ksSp, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //去实现自动登录
        Intent intent = new Intent();
        intent.setClass(context, KsService.class);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                KsService.KsBinder ksBinder = (KsService.KsBinder)service;
                if (!TextUtils.isEmpty(getToken())) {
                    ksBinder.getService().autoLogin(getToken());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }


    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
        //去通知当前用户成功登录
        if (loginStatusChangeListeners.size()>0) {
            for(ILoginStatusChangeListener listener:loginStatusChangeListeners) {
                listener.onLoginSuccess(loginBean);
            }
        }



        //存储token
        editor.putString(tokenName, loginBean.getResult().getToken());
        editor.commit();//注意提交
    }

    public String getToken() {
        return sharedPreferences.getString(tokenName, "");
    }

    public boolean isUserLogin() {
        return loginBean!=null;//只要loginBean不为空，就认为用户已经登录
    }


    public void setLoginStatusChangeListener(ILoginStatusChangeListener listener) {
        if (!loginStatusChangeListeners.contains(listener)) {
            loginStatusChangeListeners.add(listener);
        }
    }

    public void removeLoginStatusChangeListener(ILoginStatusChangeListener listener) {
        if (loginStatusChangeListeners.contains(listener)) {
            loginStatusChangeListeners.remove(listener);
        }
    }


    //添加一个通知接口，当用户登录或者退出登录，通知监听状态的页面
    public interface ILoginStatusChangeListener{
        void onLoginSuccess(LoginBean loginBean);
        void onLogoutSuccess();
    }



}
