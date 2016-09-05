package com.gm.circley.ui.activity.explore;

import com.gm.circley.R;
import com.gm.circley.adapter.BaseRecyclerAdapter;
import com.gm.circley.adapter.MusicRecyclerAdapter;
import com.gm.circley.base.BaseListActivity;
import com.gm.circley.model.MusicEntity;

public class MusicExploreActivity extends BaseListActivity<MusicEntity> {

    @Override
    protected void initView() {
        super.initView();
        showHomeBack(true, "音乐推荐");
        setStatusBarTintColor(R.color.crimson);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return new MusicRecyclerAdapter(this, mData);
    }

    @Override
    protected void onRefreshStart() {
        mControl.getMusicList(mContext,"流行音乐");
    }

    @Override
    protected void onScrollLast() {
        mControl.getMusicMoreList(mContext,"流行音乐",mData.size() + 1);
    }

    @Override
    protected int emptyDataString() {
        return R.string.str_no_data;
    }
}
