package com.lqs.kuaishou.kuaishou1801.home.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lqs.kuaishou.kuaishou1801.R;
import com.lqs.kuaishou.kuaishou1801.base.BaseRVAdapter;
import com.lqs.kuaishou.kuaishou1801.home.mode.HomeBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

public class HomeAdapter extends BaseRVAdapter<HomeBean.ResultBean> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_home;
    }

    @Override
    protected void convert(final HomeBean.ResultBean itemData, BaseViewHolder baseViewHolder) {
        //获取ImageView
        final ImageView imageView = baseViewHolder.getView(R.id.homeImg);
        Glide.with(baseViewHolder.itemView.getContext()).load(itemData.getCoverImg()).into(imageView);

        baseViewHolder.getView(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现分享
                UMImage umImage = new UMImage(imageView.getContext(), itemData.getCoverImg());
                //实现分享一张图片和文字
                new ShareAction((Activity)(imageView.getContext()))
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withText("hello")//分享内容
                        .withMedia(umImage)
                        .share();
            }
        });
    }
}
