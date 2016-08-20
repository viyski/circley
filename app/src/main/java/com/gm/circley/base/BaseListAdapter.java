package com.gm.circley.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgm on 2016/7/13.
 *
 * Adapter 基类
 */
public abstract class BaseListAdapter <E> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<E> mData;

    public BaseListAdapter(Context context){
        this(context,null);
    }

    public BaseListAdapter(Context context,List<E> data){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
        mData.addAll(data);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public E getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<E> data){
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(List<E> data){
        if (data != null){
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    public void removeDataByPosition(int position){
        if (position >= 0 && position < mData.size()){
            mData.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeData(E data){
        if (data != null){
            mData.remove(data);
            notifyDataSetChanged();
        }
    }
}
