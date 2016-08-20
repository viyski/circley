package com.gm.circley.ui.activity;

import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;

/**
 * Created by lgm on 2016/8/2.
 */
public class SettingActivity extends BaseActivity{

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        showHomeBack(true,"设置");
        setStatusBarTintColor(R.color.darkpurple);
        mToolbar.setBackgroundResource(R.color.darkpurple);
    }

    @Override
    protected void initData() {

    }
}
