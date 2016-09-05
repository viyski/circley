package com.gm.circley.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.framework.base.BaseAsyncActivity;
import com.framework.base.BaseControl;
import com.framework.dialog.LoadingDialog;
import com.gm.circley.R;
import com.gm.circley.control.manager.ImageManager;
import com.gm.circley.model.UserEntity;
import com.gm.circley.model.sharedpreferences.AccountSharedPreferences;
import com.gm.circley.model.sharedpreferences.SettingsSharedPreferences;
import com.gm.circley.ui.activity.LoginActivity;
import com.gm.circley.util.AppManager;
import com.gm.circley.util.DisplayUtil;
import com.gm.circley.util.GlobalParams;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import de.devland.esperandro.Esperandro;

/**
 * Created by lgm on 2016/7/13.
 *
 * Activity 基类
 */
public abstract class BaseActivity <T extends BaseControl> extends BaseAsyncActivity<T> implements View.OnClickListener{

    private SystemBarTintManager mSystemBarTint;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ImageManager mImageManager;
    protected UserEntity mUserEntity;
    protected Toolbar mToolbar;
    private boolean homeBackVisible = true;
    private TextView toolBarTitle;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        mContext = BaseActivity.this;
        mInflater = getLayoutInflater();
        init();
        initView();
        initData();
        initListeners();
    }

    protected abstract int getLayoutRes();
    protected abstract void initView();
    protected abstract void initData();
    protected void initListeners(){}

    @Override
    public void onClick(View v) {

    }

    private void init(){
        setTranslucentStatus(true);
        mImageManager = new ImageManager(mContext);
        GlobalParams.screenWidth = DisplayUtil.getWindowWidth(this);
        GlobalParams.screenHeight = DisplayUtil.getWindowHeight(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        loadingDialog = new LoadingDialog(this);
    }

    public void showHomeBack(boolean homeBackVisible,String title) {
        this.homeBackVisible = homeBackVisible;
        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            if (homeBackVisible) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setHomeButtonEnabled(true);
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowTitleEnabled(false);
                }
            }

            if (toolBarTitle != null) toolBarTitle.setText(title);
        }
    }

    public SystemBarTintManager getSystemBarTint(){
        if (mSystemBarTint == null) {
            mSystemBarTint = new SystemBarTintManager(this);
        }
        return mSystemBarTint;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setTranslucentStatus(boolean on){
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            winParams.flags |= bits;
        }else{
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    public void setStatusBarTintColor(int colorResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getSystemBarTint().setStatusBarTintEnabled(true);
            getSystemBarTint().setStatusBarTintColor(getResources().getColor(colorResId));
        }
    }

    protected <P> P getSharedPreferences(Class<P> clz){
        return Esperandro.getPreferences(clz,mContext);
    }

    protected SettingsSharedPreferences getSettingsSharedPreferences() {
        return getSharedPreferences(SettingsSharedPreferences.class);
    }

    protected AccountSharedPreferences getAccountSharedPreferences() {
        return getSharedPreferences(AccountSharedPreferences.class);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (DisplayUtil.isShouldHideInput(v, ev)) {
                DisplayUtil.hideSoftInput(mContext,v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent != null && intent.getComponent() != null){
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (intent != null && intent.getComponent() != null){
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (!((Object) this).getClass().equals(LoginActivity.class)) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
