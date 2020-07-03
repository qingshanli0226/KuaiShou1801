package com.lqs.kuaishou.kuaishou1801.live.contract;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.live.mode.PushRtmpBean;

public class GetRtmpPushUrlContract {

    public interface IGetRtmpPushUrlView extends IView {
        void onGetRtmpPushUrl(PushRtmpBean pushRtmpBean);
    }

    public abstract static class GetRtmpPushUrlPresenter extends BasePresenter<IGetRtmpPushUrlView> {
        public abstract void getRtmpPushUrl(String userName);
    }
}
