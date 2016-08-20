package com.gm.circley.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by lgm on 2016/7/17.
 *
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private List<Fragment> fragmentList;
    private String[] titles;

    public TabPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.titles = titles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList == null ? null :fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles[position];
    }

    public void addFragmentList(List<Fragment> fragmentList){
        if (this.fragmentList != null){
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f:fragmentList) {
                ft.remove(f);
            }

            ft.commit();
            ft = null;
            this.fragmentList = fragmentList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
