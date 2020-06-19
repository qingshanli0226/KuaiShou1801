package com.lqs.kuaishou.kuaishou1801.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import com.lqs.kuaishou.kuaishou1801.cache.dao.DaoMaster;
import com.lqs.kuaishou.kuaishou1801.cache.dao.DaoSession;
import com.lqs.kuaishou.kuaishou1801.cache.dao.KsMessageDao;
import com.lqs.kuaishou.kuaishou1801.cache.mode.KsMessage;
import com.lqs.kuaishou.kuaishou1801.cache.mode.KsMessageBean;
import com.lqs.kuaishou.kuaishou1801.service.KsService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//管理该应用的所有缓存数据
public class CacheManager {
    private static CacheManager instance;
    private final String DB_NAME = "ks.db";
    private KsMessageDao ksMessageDao;//操作数据的表类
    private IMessageChanged iMessageChanged;

    //使用线程池来做耗时操作
    private ExecutorService executorService = Executors.newCachedThreadPool();

    //定义一个链表，在内存中缓存消息
    List<KsMessage> ksMessageList = new ArrayList<>();

    private int ksMessageCount=0;

    private CacheManager() {

    }

    public static CacheManager getInstance() {
        if (instance==null) {
            instance = new CacheManager();
        }
        return instance;
    }



    public void init(Context context) {
        //初始化GreenDao
        DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);//定义数据库的名字
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        ksMessageDao = daoSession.getKsMessageDao();//获取操作数据库表的实例

        //第一步从数据库中读取数据
        getKsMessageFromDb();


        //去实现自动登录
        Intent intent = new Intent();
        intent.setClass(context, KsService.class);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                KsService.KsBinder ksBinder = (KsService.KsBinder)service;
                KsService ksService = ksBinder.getService();
                //第三步从服务端获取数据.
                ksService.getKsMessage();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

    }

    private void getKsMessageFromDb() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //第二步，存入内存
                ksMessageList.addAll(ksMessageDao.queryBuilder().list());
                ksMessageCount = ksMessageList.size();
            }
        });

    }

    //第四步服务端返回数据往数据库中添加信息
    public void saveKsMessage(KsMessageBean ksMessageBean) {
        List<KsMessage> ksMessages = new ArrayList<>();
        for(KsMessageBean.ResultBean item:ksMessageBean.getResult()) {
            KsMessage ksMessage = new KsMessage();
            ksMessage.setUserId(item.getUserId());
            ksMessage.setAvatar(item.getAvatar());
            ksMessage.setContent(item.getContent());
            //第五步将消息使用Dao存储到数据库中
            ksMessageDao.insert(ksMessage);
            ksMessages.add(ksMessage);
        }

        //第六步，使用从服务端获取的数据，更新内存
        ksMessageList.addAll(ksMessages);

        //第七步更新UI
        ksMessageCount = ksMessageList.size();//把从服务端获取的消息数量存储起来
        if (iMessageChanged!=null) {
            iMessageChanged.onMessageChange(ksMessageList, ksMessageCount);
        }

    }


    public int getKsMessageCount() {
        return ksMessageCount;
    }

    public IMessageChanged getiMessageChanged() {
        return iMessageChanged;
    }

    public void setiMessageChanged(IMessageChanged iMessageChanged) {
        this.iMessageChanged = iMessageChanged;
    }


    public interface IMessageChanged{
        void onMessageChange(List<KsMessage> ksMessageList, int ksMessageCount);
    }
}