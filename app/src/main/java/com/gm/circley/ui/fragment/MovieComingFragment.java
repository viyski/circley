package com.gm.circley.ui.fragment;

import android.view.View;

import com.gm.circley.R;
import com.gm.circley.adapter.BaseRecyclerAdapter;
import com.gm.circley.adapter.MovieRecyclerAdapter;
import com.gm.circley.base.BaseListFragment;
import com.gm.circley.interf.UrlParams;
import com.gm.circley.model.MovieEntity;

/**
 * Created by lgm on 2016/8/7.
 */
public class MovieComingFragment extends BaseListFragment<MovieEntity>{

    @Override
    protected View initView() {
        isSetThemeType(false);
        return super.initView();
    }

    @Override
    protected BaseRecyclerAdapter<MovieEntity> getRecyclerAdapter() {
        return new MovieRecyclerAdapter(mActivity,mData);
    }

    @Override
    protected void onRefreshStart() {
        mControl.getMovieList(mContext, UrlParams.MOVIE_COMING);
    }

    @Override
    protected void onScrollLast() {
        mControl.getMovieMoreList(mContext, UrlParams.MOVIE_COMING,mData.size() + 1);
    }

    @Override
    protected int emptyDataString() {
        return R.string.no_data;
    }
}
