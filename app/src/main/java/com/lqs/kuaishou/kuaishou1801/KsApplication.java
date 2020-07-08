package com.lqs.kuaishou.kuaishou1801;

import android.app.Application;
import android.util.Log;

import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.lqs.kuaishou.kuaishou1801.manager.KsUserManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.rtmp.TXLiveBase;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

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


        //配置腾讯直播的license
        String licenceURL ="http://license.vod2.myqcloud.com/license/v1/5234ff71d69bed4d9a2485556d66a1c1/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "ec0ff288bdd479cc573bd49a725bffac"; // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);


        //ch初始化友盟
        UMConfigure.init(this, "5ed6eda5895cca71ee00007c", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "f69626329c33fae4b9a8a3d242dd986c");
//获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.d("LQS","注册成功：deviceToken：-------->  " + deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
                Log.e("LQS","注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });



        initSharePlatform();


        //初始化统计
        //在Application中添加代码  在公司叫打点或者叫埋点
        //可以统计应用程序使用时长
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
        //统计错误
        MobclickAgent.setCatchUncaughtExceptions(true);
        //友盟配置end
    }
    /*
        Application类中添加
    */
    //初始化分享
    private void initSharePlatform() {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setQQFileProvider("com.example.kuaishou.demokuaishou.fileprovider");
    }
}
