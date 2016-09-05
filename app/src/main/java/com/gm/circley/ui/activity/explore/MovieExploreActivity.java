package com.gm.circley.ui.activity.explore;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.gm.circley.R;
import com.gm.circley.adapter.TabPagerAdapter;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.base.BaseListFragment;
import com.gm.circley.ui.fragment.MovieComingFragment;
import com.gm.circley.ui.fragment.MoviePlayingFragment;
import com.gm.circley.ui.fragment.MovieTopListFragment;
import com.gm.circley.util.Utils;
import com.gm.circley.widget.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MovieExploreActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPagerFixed viewPager;
    private String[] titles;
    private List<Fragment> fragmentList;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_movie_explore;
    }

    @Override
    protected void initView() {
        showHomeBack(true,"电影推荐");
        setStatusBarTintColor(R.color.crimson);

        titles = Utils.getStrArray(this, R.array.movie_titles);
        fragmentList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        MovieComingFragment fragment1 = new MovieComingFragment();
        MoviePlayingFragment fragment2 = new MoviePlayingFragment();
        MovieTopListFragment fragment3 = new MovieTopListFragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);

        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),titles,fragmentList));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager,true);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            BaseListFragment fragment = (BaseListFragment) fragmentList.get(position);
            fragment.setLoadingViewVisible();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
