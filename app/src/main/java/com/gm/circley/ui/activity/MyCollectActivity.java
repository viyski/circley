package com.gm.circley.ui.activity;

import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.ui.fragment.CollectListFragment;

/**
 * Created by lgm on 2016/8/2.
 */
public class MyCollectActivity extends BaseActivity{
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void initView() {
        showHomeBack(true,"我的收藏");
        setStatusBarTintColor(R.color.darkpurple);
        mToolbar.setBackgroundResource(R.color.darkpurple);
    }

    @Override
    protected void initData() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,new CollectListFragment(),"MyCollectList")
                .commitAllowingStateLoss();
    }
}
