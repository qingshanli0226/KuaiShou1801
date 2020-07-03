package com.lqs.kuaishou.kuaishou1801.live.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.live.mode.BroadcastListBean;

public class GetBroadcastListContract {

    public interface IGetBroadcastListView extends IView {
        void onGetBroadcastList(BroadcastListBean broadcastListBean);
    }


    public static abstract class GetBroadcastListPresenter extends BasePresenter<IGetBroadcastListView> {
        public abstract void getBroadcastList();
    }
}
