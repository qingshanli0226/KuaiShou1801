package com.lqs.kuaishou.kuaishou1801.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.lqs.kuaishou.kuaishou1801.cache.dao.DaoMaster;
import com.lqs.kuaishou.kuaishou1801.cache.dao.DaoSession;
import com.lqs.kuaishou.kuaishou1801.cache.dao.HistoryEntityDao;
import com.lqs.kuaishou.kuaishou1801.cache.dao.KsMessageDao;
import com.lqs.kuaishou.kuaishou1801.cache.dao.SearchHistoryDao;
import com.lqs.kuaishou.kuaishou1801.cache.mode.HistoryEntity;
import com.lqs.kuaishou.kuaishou1801.cache.mode.KsMessage;
import com.lqs.kuaishou.kuaishou1801.cache.mode.KsMessageBean;
import com.lqs.kuaishou.kuaishou1801.cache.mode.SearchHistory;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchRecommendBean;
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

    private final String ksSpName = "cacheKsSp";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String adrTime = "adrTime";

    //使用线程池来做耗时操作
    private ExecutorService executorService = Executors.newCachedThreadPool();

    //定义一个链表，在内存中缓存消息
    List<KsMessage> ksMessageList = new ArrayList<>();

    private HistoryEntityDao historyEntityDao;
    private SearchHistoryDao searchHistoryDao;


    private List<SearchHistory> searchHistoryList = new ArrayList<>();

    private int ksMessageCount=0;

    private SearchRecommendBean searchRecommendBean;

    private CacheManager() {

    }

    public static CacheManager getInstance() {
        if (instance==null) {
            instance = new CacheManager();
        }
        return instance;
    }

    //返回线程池
    public ExecutorService getExecutorService() {
        return executorService;
    }



    public void init(Context context) {
        //初始化sp
        sharedPreferences = context.getSharedPreferences(ksSpName,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //初始化GreenDao
        DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);//定义数据库的名字
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        ksMessageDao = daoSession.getKsMessageDao();//获取操作数据库表的实例
        historyEntityDao = daoSession.getHistoryEntityDao();
        searchHistoryDao = daoSession.getSearchHistoryDao();

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

                //从数据库中获取历史搜索记录数据
                ksService.getSearchHistory();

                //将搜索页面的热搜榜数据获取到
                ksService.getSearchRecommend();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

    }

    //存储页面onPause时的时间
    public void saveAdrTime(long time) {
        editor.putLong(adrTime, time);
        editor.commit();
    }

    //获取广告时间,在页面onResume时读取的
    public long getAdrTime() {
        return sharedPreferences.getLong(adrTime, 0);
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

    public SearchRecommendBean getSearchRecommendBean() {
        return searchRecommendBean;
    }

    public void setSearchRecommendBean(SearchRecommendBean searchRecommendBean) {
        this.searchRecommendBean = searchRecommendBean;
    }


    public interface IMessageChanged{
        void onMessageChange(List<KsMessage> ksMessageList, int ksMessageCount);
    }

    //如果数据的图片地址相同，我们就认为两个数据相等
    private HistoryEntity getExistHistory(HistoryEntity historyEntity, List<HistoryEntity> historyEntityList) {
        for (HistoryEntity item:historyEntityList) {
            if (item.getImageUrl().equals(historyEntity.getImageUrl())) {
                return item;
            }
        }
        return null;
    }

    //增加一条历史记录.该方法是异步执行的方法，调用该方法时，没必要启动子线程
    public void addOneHistoryEntity(final HistoryEntity historyEntity, final IHistoryAddCallback iHistoryAddCallback) {
        //对数据库的操作时耗时操作
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<HistoryEntity> historyEntityList = historyEntityDao.queryBuilder().list();
                HistoryEntity existHistoryEntity = getExistHistory(historyEntity, historyEntityList);
                if (existHistoryEntity!=null) {//如果存在同样的数据,只是更新数据库中该条数据的时间
                    existHistoryEntity.setTime(historyEntity.getTime());
                    historyEntityDao.update(existHistoryEntity);
                } else {
                    //插入
                    historyEntityDao.insert(historyEntity);
                }
                if (iHistoryAddCallback!=null) {
                    iHistoryAddCallback.onAddOneHistoryCallback(historyEntity);
                }
            }
        });
    }

    //删除一条历史记录
    public void deleteOneHistory(final HistoryEntity historyEntity, final IHistoryDeleteCallback iHistoryDeleteCallback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                historyEntityDao.delete(historyEntity);
                if (iHistoryDeleteCallback!=null) {
                    iHistoryDeleteCallback.onDeleteOneHistoryCallback(historyEntity);
                }
            }
        });
    }

    //更新一条历史记录
    public void updateOneHistory(final HistoryEntity historyEntity, final IHistoryUpdateCallback iHistoryUpdateCallback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                historyEntityDao.update(historyEntity);
                if (iHistoryUpdateCallback!=null) {
                    iHistoryUpdateCallback.onUpdateOneHistoryCallback(historyEntity);
                }
            }
        });
    }

    //查找历史记录
    public void queryHistoryEntity(final IHistoryQueryCallback iHistoryQueryCallback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<HistoryEntity> historyEntityList = historyEntityDao.queryBuilder().orderDesc(HistoryEntityDao.Properties.Time).list();
                if (iHistoryQueryCallback!=null) {
                    iHistoryQueryCallback.onQueryCallback(historyEntityList);
                }
            }
        });
    }

    public interface IHistoryQueryCallback {
        void onQueryCallback(List<HistoryEntity> historyEntityList);
    }
    public interface IHistoryAddCallback {
        void onAddOneHistoryCallback(HistoryEntity historyEntity);
    }
    public interface IHistoryDeleteCallback {
        void onDeleteOneHistoryCallback(HistoryEntity historyEntity);
    }
    public interface IHistoryUpdateCallback {
        void onUpdateOneHistoryCallback(HistoryEntity historyEntity);
    }


    /*搜索页面的缓存数据操作。提供一些API，这些API,对页面和service提供操作数据库的接口， */
    //该方法是同步执行的方法，所以在页面调用方法时，需要使用在子线程中
    public void addOneSearchHistory(SearchHistory searchHistory) {
        searchHistoryDao.insert(searchHistory);
        //缓存也需要更新
        //在第0个位置添加搜索历史记录
        searchHistoryList.add(0,searchHistory);
    }

    public void deleteOneSearchHistory(SearchHistory searchHistory) {
        searchHistoryDao.delete(searchHistory);
        //需要在内存中也删除该条数据
        searchHistoryList.remove(searchHistory);
    }

    //因为这个历史记录在应用启动时，会将数据缓存到内存中，所以该方法不要在页面中调用，只在service中异步获取的，。
    public void querySearchHistory() {
        List<SearchHistory> searchHistories = searchHistoryDao.queryBuilder().orderDesc(SearchHistoryDao.Properties.Time).list();
        searchHistoryList.addAll(searchHistories);
    }

    //获取内存缓存的历史记录
    public List<SearchHistory> getSearchHistoryList(){
        return searchHistoryList;
    }
}
