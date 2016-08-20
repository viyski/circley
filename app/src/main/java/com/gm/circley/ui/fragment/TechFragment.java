package com.gm.circley.ui.fragment;

import com.gm.circley.R;
import com.gm.circley.adapter.BaseRecyclerAdapter;
import com.gm.circley.adapter.NewsRecyclerAdapter;
import com.gm.circley.base.BaseListFragment;
import com.gm.circley.interf.UrlParams;
import com.gm.circley.model.NewsEntity;

/**
 * Created by lgm on 2016/7/17.
 */
public class TechFragment extends BaseListFragment<NewsEntity>{

    @Override
    protected BaseRecyclerAdapter<NewsEntity> getRecyclerAdapter() {
        return new NewsRecyclerAdapter(mActivity,mData);
    }

    @Override
    protected void onRefreshStart() {
        mControl.getNewsListData(UrlParams.CHANNEL_NEWS_TECH);
    }

    @Override
    protected void onScrollLast() {
        mControl.getNewsListDataMore(UrlParams.CHANNEL_NEWS_TECH,++currentPage);
    }

    @Override
    protected int emptyDataString() {
        return R.string.str_no_data;
    }
}
