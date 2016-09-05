package com.gm.circley.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gm.circley.control.manager.ImageManager;

import java.util.List;

/**
 * Created by lgm on 2016/8/4.
 */
public abstract class BaseRecyclerAdapter<E> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected ImageManager mImageManager;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected Activity mActivity;
    protected List<E> mData;

    public BaseRecyclerAdapter(Context context,List<E> data){
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
        mImageManager = new ImageManager(context);
    }

    public BaseRecyclerAdapter(Activity activity,List<E> data){
        mActivity = activity;
        mData = data;
        mInflater = LayoutInflater.from(activity);
        mImageManager = new ImageManager(activity);
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public void setLoadMoreViewVisible(int visible) {
    }

    public void setLoadViewText(String text) {
    }

    public boolean isLoadMoreShown() {
        return false;
    }

    public String getLoadMoreViewText() {
        return null;
    }

    public void setLoadViewVisible(boolean visible) {
    }

    public void setThemeType(int themeTypeRed) {

    }
}
