package com.lqs.kuaishou.kuaishou1801.player;

import com.lqs.kuaishou.kuaishou1801.base.BasePresenter;
import com.lqs.kuaishou.kuaishou1801.base.IView;
import com.lqs.kuaishou.kuaishou1801.player.mode.GiftBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.MoneyBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.OrderInfoBean;

public class GiftContract {



    public interface IGiftView extends IView{
        void onGift(GiftBean giftBean);
        void onMoney(MoneyBean moneyBean);
        void onOrderInfo(OrderInfoBean orderInfoBean);
    }


    public static abstract class GiftPresenter extends BasePresenter<IGiftView> {
        public abstract void getGift();
        public abstract void updateMoney(String money);
        public abstract void getOrderInfo(String totalPrice);
    }
}
