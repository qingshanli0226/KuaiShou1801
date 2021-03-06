package com.lqs.kuaishou.kuaishou1801.net;

import com.lqs.kuaishou.kuaishou1801.cache.mode.KsMessageBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.CityBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.FocusBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;
import com.lqs.kuaishou.kuaishou1801.home.mode.LogoutBean;
import com.lqs.kuaishou.kuaishou1801.live.mode.BroadcastListBean;
import com.lqs.kuaishou.kuaishou1801.live.mode.PushRtmpBean;
import com.lqs.kuaishou.kuaishou1801.login.mode.LoginBean;
import com.lqs.kuaishou.kuaishou1801.login.mode.RegisterBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.GiftBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.MoneyBean;
import com.lqs.kuaishou.kuaishou1801.player.mode.OrderInfoBean;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchRecommendBean;
import com.lqs.kuaishou.kuaishou1801.search.mode.SearchResultBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

//认为是IMode
public interface KSApiService {

    @GET("findVideo")
    Observable<HomeBean> getHomeData();

    @GET("findCityVideo")
    Observable<CityBean> getCityData();

    @GET("findFocusVideo")
    Observable<FocusBean> getFocusData();


    @POST("login")
    @FormUrlEncoded
    Observable<LoginBean> login(@FieldMap HashMap<String,String> params);


    @POST("register")
    @FormUrlEncoded
    Observable<RegisterBean> register(@FieldMap HashMap<String,String> params);

    @POST("autoLogin")
    @FormUrlEncoded
    Observable<LoginBean> autoLogin(@FieldMap HashMap<String,String> params);

    @POST("logout")
    Observable<LogoutBean> logout();

    @GET("getSecurityMessage")
    Observable<KsMessageBean> getKsMessage();


    @GET("atguigu/json/gif.json")
    Observable<GiftBean> getGift();


    @POST("updateMoney")
    @FormUrlEncoded
    Observable<MoneyBean> updateMoney(@FieldMap HashMap<String ,String> params);

    //请求参数是json形式
    @POST("getOrderInfo")
    Observable<OrderInfoBean> getOrderInfo(@Body RequestBody requestBody);


    @GET("getRecommendVideos")
    Observable<SearchRecommendBean> getSearchRecommend();


    @GET("searchRecommendVideo")
    Observable<SearchResultBean> searchRecommend(@QueryMap HashMap<String ,String> params);

    //获取推流地址
    @GET("getRtmpPushUrl")
    Observable<PushRtmpBean> getRtmpPushUrl(@QueryMap HashMap<String,String> params);

    @GET("getLiveUser")
    Observable<BroadcastListBean> getBroadcastList();


    @GET
    Observable<List<String>> getWeekTestVideo(@Url String url);
}
