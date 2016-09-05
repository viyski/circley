package com.gm.circley.ui.activity;

import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by lgm on 2016/9/1.
 */
public class AboutActivity extends BaseActivity{

    @Bind(R.id.tv_about)
    TextView tvAbout;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        setStatusBarTintColor(R.color.darkpurple);
        showHomeBack(true,"关于");
        mToolbar.setBackgroundResource(R.color.darkpurple);
        tvAbout.setText(R.string.share_text);
    }

    @Override
    protected void initData() {

    }
}
