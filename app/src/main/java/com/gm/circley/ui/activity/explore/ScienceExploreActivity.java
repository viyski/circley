package com.gm.circley.ui.activity.explore;

import com.gm.circley.R;
import com.gm.circley.base.BaseListActivity;
import com.gm.circley.interf.UrlParams;

public class ScienceExploreActivity extends BaseListActivity {

    @Override
    protected void initView() {
        super.initView();
        showHomeBack(true,"科学探索");
    }

    @Override
    protected void onRefreshStart() {
        mControl.getNewsListData(UrlParams.CHANNEL_NEWS_KNOWNLEDGE);
    }

    @Override
    protected void onScrollLast() {
        mControl.getNewsListDataMore(UrlParams.CHANNEL_NEWS_KNOWNLEDGE,++currentPage);
    }

    @Override
    protected int emptyDataString() {
        return R.string.str_no_data;
    }
}
