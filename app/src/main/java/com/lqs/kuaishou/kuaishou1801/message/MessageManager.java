package com.lqs.kuaishou.kuaishou1801.message;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.lqs.kuaishou.kuaishou1801.KsApplication;
import com.lqs.kuaishou.kuaishou1801.cache.CacheManager;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.message.inapp.InAppMessageManager;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {


    private static MessageManager instance;
    private List<UMessage> uMessageList = new ArrayList<>();//应用程序启动时，就要在数据库中把数据读到该列表中


    private MessageManager() {

    }

    public static MessageManager getInstance() {
        if (instance==null) {
            instance = new MessageManager();
        }

        return instance;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    UMessage uMessage = (UMessage) msg.obj;
                    Toast.makeText(KsApplication.instance, "收到的通知:" + uMessage.text, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    UMessage uMessage2 = (UMessage) msg.obj;
                    Toast.makeText(KsApplication.instance, "收到的自定义消息:" + uMessage2.title, Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        break;
            }

        }
    };

    public void init(Context context) {
        //
        readUmssageFromDb();


        PushAgent pushAgent = PushAgent.getInstance(context);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            //通过该方法获取发送的消息通知内容
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                //运行在子线程，需要把它切到主线程中，显示
                Message message = new Message();
                message.what = 1;
                message.obj = uMessage;
                handler.sendMessage(message);

                //把消息插入到数据库中
                //在缓存中添加一条消息
                uMessageList.add(uMessage);

                //发送广播

            }

            //接收自定义消息的方法
            @Override
            public void dealWithCustomMessage(Context context, UMessage uMessage) {
                super.dealWithCustomMessage(context, uMessage);
                //运行在子线程，需要把它切到主线程中，显示
                Message message = new Message();
                message.what = 2;
                message.obj = uMessage;
                handler.sendMessage(message);

                //把消息插入到数据库中
                //在缓存中添加一条消息
                uMessageList.add(uMessage);
            }
        };
        //自己定义消息通知的处理放射式，如果我们自己定义了处理方式，那么系统默认的处理方式将不再起作用
        pushAgent.setMessageHandler(messageHandler);
    }

    private void readUmssageFromDb() {
        CacheManager.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public List<UMessage> getuMessageList() {
        return uMessageList;
    }

    public int getUnReadMessageCount() {
        //在列表中去遍历，将未读的数量返回
        return 0;
    }
}
