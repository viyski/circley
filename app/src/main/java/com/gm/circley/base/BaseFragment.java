package com.gm.circley.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.base.BaseAsyncFragment;
import com.framework.base.BaseControl;
import com.gm.circley.control.manager.ImageManager;

import butterknife.ButterKnife;

/**
 * Created by lgm on 2016/7/13.
 *
 * Fragment基类
 */
public abstract class BaseFragment<T extends BaseControl> extends BaseAsyncFragment<T> implements View.OnClickListener{

    protected Context mContext;
    protected Activity mActivity;
    protected LayoutInflater mInflater;
    protected View mRootView;
    protected ImageManager mImageManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init(){
        mContext = getActivity();
        mActivity = getActivity();
        mImageManager = new ImageManager(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mRootView = initView();
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListeners();
    }

    protected abstract View initView();

    protected void initData(){
    }

    protected void initListeners(){}

    @Override
    public void onClick(View v) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}