package com.gm.circley.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.adapter.NewsRecyclerAdapter;
import com.gm.circley.control.PageControl;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.model.NewsEntity;
import com.mingle.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lgm on 2016/8/4.
 */
public abstract class BaseListActivity extends BaseActivity<PageControl> implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.loadingView)
    LoadingView loadingView;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.fl_status)
    FrameLayout flStatus;

    protected int currentPage = 1;
    protected Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private List<NewsEntity> mData;
    private NewsRecyclerAdapter mAdapter;
    private int lastVisibleItemPosition;
    private boolean isCompletedRefresh;

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_recycler_list;
    }

    protected void initView() {
        mData = new ArrayList<>();
        mContext = BaseListActivity.this;

        setStatusBarTintColor(R.color.crimson);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue,R.color.orange,R.color.teal,R.color.red);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    protected void initData(){
        if (mAdapter == null) {
            mAdapter = new NewsRecyclerAdapter(this, mData);
            mAdapter.setThemeType(ConstantsParams.THEME_TYPE_RED);
            recyclerView.setAdapter(mAdapter);
        }else{
            recyclerView.setAdapter(mAdapter);
        }

        onRefresh();
    }

    protected void initListeners(){
        recyclerView.addOnScrollListener(new PauseScrollListener());
        swipeRefreshLayout.setOnRefreshListener(this);

        tvStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_status){
            v.setVisibility(View.GONE);
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        setSwipeRefreshLoadingState();
        messageProxy.postRunnableDelay(new Runnable() {
            @Override
            public void run() {
                onRefreshStart();
            }
        }, 500);
    }

    private class PauseScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch(newState){
                case RecyclerView.SCROLL_STATE_IDLE:
                    int size = recyclerView.getAdapter().getItemCount();
                    if (lastVisibleItemPosition + 1 == size && mAdapter.isLoadMoreShown() &&
                            !mAdapter.getLoadMoreViewText().equals(getString(R.string.load_data_adequate))){
                        onScrollLast();
                    }
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        }
    }

    // 数据为空
    public void getDataEmpty(){
        completeRefresh();
        flStatus.setVisibility(View.VISIBLE);
        mAdapter.setLoadMoreViewVisible(View.GONE);
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(getString(emptyDataString()));

        mData.clear();
        mAdapter.notifyDataSetChanged();
    }
    // 数据加载失败
    public void getDataFailed(){
        completeRefresh();
        flStatus.setVisibility(View.VISIBLE);
        mAdapter.setLoadMoreViewVisible(View.GONE);

        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(getString(R.string.load_data_failed));

        mData.clear();
        mAdapter.notifyDataSetChanged();

    }
    // 数据足够PAGE_SIZE
    public void getDataAdequate(){
        completeRefresh();
        flStatus.setVisibility(View.GONE);
        mAdapter.setLoadMoreViewVisible(View.VISIBLE);
        mAdapter.setLoadViewText(getString(R.string.loading_data));


        List<NewsEntity> entities = mModel.getList(1);
        mData.clear();
        mData.addAll(entities);
        mAdapter.notifyDataSetChanged();
    }
    // 数据不足PAGE_SIZE
    public void getDataInadequate(){
        completeRefresh();
        flStatus.setVisibility(View.GONE);
        mAdapter.setLoadMoreViewVisible(View.GONE);

        List<NewsEntity> entities = mModel.getList(1);
        mData.clear();
        mData.addAll(entities);
        mAdapter.notifyDataSetChanged();
    }

    // 数据为空 (More)
    public void getMoreDataEmpty(){
        mAdapter.setLoadMoreViewVisible(View.GONE);
    }
    // 数据足够PAGE_SIZE (More)
    public void getMoreDataAdequate(){
        mAdapter.setLoadMoreViewVisible(View.VISIBLE);
        List<NewsEntity> entities = mModel.getList(2);
        mData.addAll(entities);
        mAdapter.notifyDataSetChanged();
    }
    // 数据加载失败 (More)
    public void getMoreDataFailed(){
        mAdapter.setLoadMoreViewVisible(View.VISIBLE);
        mAdapter.setLoadViewText(getString(R.string.load_data_failed));
    }
    // 数据不足PAGE_SIZE (More)
    public void getMoreDataInadequate(){
        mAdapter.setLoadMoreViewVisible(View.VISIBLE);
        mAdapter.setLoadViewText(getString(R.string.load_data_adequate));
        List<NewsEntity> entities = mModel.getList(2);
        mData.addAll(entities);
        mAdapter.notifyDataSetChanged();
    }

    private void completeRefresh() {
        setSwipeRefreshLoadedState();

        if (loadingView != null && loadingView.getVisibility() == View.VISIBLE) {
            loadingView.setVisibility(View.GONE);
            isCompletedRefresh = true;
        }
    }

    private void setSwipeRefreshLoadingState(){
        if (swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setEnabled(false);
        }
    }

    private void setSwipeRefreshLoadedState(){
        if (swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(true);
        }
    }

    public void setLoadingViewVisible(){
        if (loadingView != null) {
            if (isCompletedRefresh) {
                loadingView.setVisibility(View.GONE);
            } else {
                loadingView.setVisibility(View.VISIBLE);
            }
        }
    }

    protected abstract void onRefreshStart();
    protected abstract void onScrollLast();
    protected abstract int emptyDataString();
}
