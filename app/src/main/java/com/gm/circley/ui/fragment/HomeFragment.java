package com.gm.circley.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gm.circley.R;
import com.gm.circley.adapter.TabPagerAdapter;
import com.gm.circley.base.BaseFragment;
import com.gm.circley.base.BaseListFragment;
import com.gm.circley.util.Utils;
import com.gm.circley.widget.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lgm on 2016/7/17.
 * </p>
 * 首页Fragment
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPagerFixed viewPager;
    private List<Fragment> fragmentList;

    @Override
    protected View initView() {
        return mInflater.inflate(R.layout.layout_tab_pager, null);
    }

    @Override
    protected void initData() {
        String[] titles = Utils.getStrArray(mContext,R.array.home_titles);
        fragmentList = new ArrayList<>();
        fragmentList.add(new HotFragment());
        fragmentList.add(new SportsFragment());
        fragmentList.add(new AutoFragment());
        fragmentList.add(new TechFragment());
        fragmentList.add(new FinanceFragment());
        TabPagerAdapter adapter = new TabPagerAdapter(getChildFragmentManager(), titles, fragmentList);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
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
