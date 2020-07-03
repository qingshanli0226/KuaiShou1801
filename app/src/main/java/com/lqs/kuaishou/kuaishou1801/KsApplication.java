package com.lqs.kuaishou.kuaishou1801;

import android.app.Application;

import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.rtmp.TXLiveBase;

public class KsApplication extends Application {
    private RefWatcher refWatcher;

    public static KsApplication instance;//作为项目一个全局的上下文
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //使用leakcannary来检测当前应用是否有内存泄漏.
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            //https://www.liaohuqiu.net/cn/posts/leak-canary-read-me/
            refWatcher = LeakCanary.install(this);
        }

        KsUserManager.getInstance().init(this);
        CacheManager.getInstance().init(this);


        //配置腾讯直播的license
        String licenceURL ="http://license.vod2.myqcloud.com/license/v1/5234ff71d69bed4d9a2485556d66a1c1/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "ec0ff288bdd479cc573bd49a725bffac"; // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);

    }
}
