package com.gm.circley.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.framework.dialog.ToastTip;
import com.github.clans.fab.FloatingActionMenu;
import com.gm.circley.BuildConfig;
import com.gm.circley.R;
import com.gm.circley.permissions.PermissionsManager;
import com.gm.circley.permissions.PermissionsResultAction;
import com.gm.circley.ui.fragment.CollectListFragment;
import com.gm.circley.ui.fragment.ExploreFragment;
import com.gm.circley.ui.fragment.HomeFragment;
import com.gm.circley.ui.fragment.MeFragment;
import com.gm.circley.util.AppManager;
import com.gm.circley.util.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BadgeProvider;
import it.sephiroth.android.library.bottomnavigation.BottomBehavior;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.fab)
    FloatingActionMenu floatingActionMenu;
    @Bind(R.id.bottom_navigation)
    BottomNavigation bottomNavigation;
    private int mCurrentPosition;
    private String[] titles;
    private long lastTime;
    private Context mContext;
    private SystemBarTintManager systemBarTintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = MainActivity.this;
        initializeUI(savedInstanceState);
        initView();
        initData();
        initListeners();
    }

    private void initView() {
        setTranslucentStatus(true);
        setStatusBarTintColor(R.color.colorPrimary);
        BottomNavigation.DEBUG = BuildConfig.DEBUG;
        bottomNavigation.setOnMenuItemClickListener(this);
        titles = Utils.getStrArray(this,R.array.titles);
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

    private void setStatusBarTintColor(int color){
        if (systemBarTintManager == null) {
            systemBarTintManager = new SystemBarTintManager(this);
        }
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setStatusBarTintColor(getResources().getColor(color));
    }

    private void initializeUI(final Bundle savedInstanceState) {
        if (bottomNavigation != null && null == savedInstanceState) {
            bottomNavigation.setDefaultSelectedIndex(0);
            BadgeProvider provider = bottomNavigation.getBadgeProvider();
        }
    }

    private void showFloatMenu(boolean show) {
        if (show) {
            ((BottomBehavior) bottomNavigation.getBehavior()).setOnExpandStatusChangeListener(mOnExpandStatusChangeListener);
        }else{
            ((BottomBehavior) bottomNavigation.getBehavior()).setOnExpandStatusChangeListener(null);
        }
    }

    private BottomBehavior.OnExpandStatusChangeListener mOnExpandStatusChangeListener =  new BottomBehavior.OnExpandStatusChangeListener() {
        @Override
        public void onExpandStatusChanged(final boolean expanded, final boolean animate) {
            if (expanded) {
                floatingActionMenu.showMenu(animate);
            } else {
                floatingActionMenu.hideMenu(animate);
            }
        }
    };

    private void initData() {
        switchFragment(0);
    }

    private void initListeners() {
        floatingActionMenu.findViewById(R.id.fab_item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                startActivity(new Intent(mContext,EditNewBlogActivity.class));
            }
        });

        floatingActionMenu.findViewById(R.id.fab_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(v,"该功能暂未开发!",Snackbar.LENGTH_LONG);
                snackbar.setAction("NULL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        floatingActionMenu.close(true);
                    }
                });
                snackbar.show();
            }
        });
    }

    @Override
    public void onMenuItemSelect(@IdRes int itemId, int position) {
        bottomNavigation.getBadgeProvider().remove(itemId);
        if (position == 1) {
            hideFloatActionButton(false);
            showFloatMenu(true);
        }else {
            hideFloatActionButton(true);
            showFloatMenu(false);
        }

        switch (position){
            case 0:
                updateToolBarStatusBarColor(R.color.colorPrimary);
                switchFragment(0);
                break;
            case 1:
                updateToolBarStatusBarColor(R.color.teal);
                switchFragment(1);
                break;
            case 2:
                updateToolBarStatusBarColor(R.color.crimson);
                switchFragment(2);
                break;
            case 3:
                updateToolBarStatusBarColor(R.color.darkpurple);
                switchFragment(3);
                break;
        }
    }

    private void switchFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment oldFragment = fragmentManager.findFragmentByTag(makeTag(mCurrentPosition));

        if (oldFragment != null){
            fragmentTransaction.hide(oldFragment);
        }
        mCurrentPosition = position;
        Fragment currentFragment = fragmentManager.findFragmentByTag(makeTag(position));
        if (currentFragment != null){
            fragmentTransaction.show(currentFragment);
        }else{
            fragmentTransaction.add(R.id.fl_container,getFragment(position),makeTag(position));
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    private String makeTag(int position) {
        return R.id.fl_container + "" + position;
    }

    private Fragment getFragment(int position){
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CollectListFragment();
                break;
            case 2:
                fragment = new ExploreFragment();
                break;
            case 3:
                fragment = new MeFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public void onMenuItemReselect(@IdRes final int itemId, final int position) {

    }

    private void updateToolBarStatusBarColor(int colorResId){
        setStatusBarTintColor(colorResId);
        floatingActionMenu.setMenuButtonColorNormalResId(colorResId);
        floatingActionMenu.setMenuButtonColorPressedResId(colorResId);
    }

    public void hideFloatActionButton(boolean hide){
        floatingActionMenu.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime < 2000) {
            AppManager.getAppManager().AppExit();
            super.onBackPressed();
        } else {
            lastTime = System.currentTimeMillis();
            ToastTip.show(getString(R.string.toast_exit_tip));
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent != null && intent.getComponent() != null){
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    @TargetApi(23)
    private void reqeuestPermissions(){
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults);
    }
}
