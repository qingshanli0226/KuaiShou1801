package com.lqs.kuaishou.kuaishou1801;

import android.app.Application;

import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

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

    }
}
