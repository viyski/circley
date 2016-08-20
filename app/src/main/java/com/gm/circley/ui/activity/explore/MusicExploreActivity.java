package com.gm.circley.ui.activity.explore;

import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;

public class MusicExploreActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_weibo_explore;
    }

    @Override
    protected void initView() {
        showHomeBack(true,"音乐推荐");
        setStatusBarTintColor(R.color.crimson);
    }

    @Override
    protected void initData() {

    }
}
