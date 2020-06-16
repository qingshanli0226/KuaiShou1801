package com.lqs.kuaishou.kuaishou1801.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//定义一个万能适配器，可以适配不同类型的数据，又能适配不同ItemView
public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<BaseRVAdapter.BaseViewHolder> {
    private IRecyclerViewItemClickListener iRecyclerViewItemClickListener;
    private List<T> dataList = new ArrayList<>();

    public void updataData(List<T> datas) {
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //因为需要适配不同的布局，所以需要提供一个抽象方法，让子类把布局文件传递过来
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(), viewGroup, false);
        return new BaseViewHolder(rootView);
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, final int position) {
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iRecyclerViewItemClickListener!=null) {
                    iRecyclerViewItemClickListener.onItemClick(position);//设置Item的点击事件
                }
            }
        });

        T itemData = getItemData(position);
        convert(itemData, baseViewHolder);

    }
    //需要子类来，渲染UI
    protected abstract void convert(T itemData, BaseViewHolder baseViewHolder);

    public T getItemData(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public IRecyclerViewItemClickListener getiRecyclerViewItemClickListener() {
        return iRecyclerViewItemClickListener;
    }

    public void setiRecyclerViewItemClickListener(IRecyclerViewItemClickListener iRecyclerViewItemClickListener) {
        this.iRecyclerViewItemClickListener = iRecyclerViewItemClickListener;
    }

    //定义一个ViewHolder，可以适配不同的UI
    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        //Integer是id，View是控件，所有的控件的父类都是View,使用HashMap来存储这些Item里的所有View.findViewById,是比较耗时的
        HashMap<Integer, View> viewHashMap = new HashMap<>();

        public BaseViewHolder(View rootView) {
            super(rootView);
        }

        //泛型方法，可以通过它获取view，并且强制转换成需要的view类型,可以参考系统的findViewById
        public <V extends View> V getView(@IdRes int id) {
            View view = viewHashMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewHashMap.put(id, view);
            }

            return (V)view;
        }
    }

    public interface IRecyclerViewItemClickListener {
        void onItemClick(int position);
    }
}
