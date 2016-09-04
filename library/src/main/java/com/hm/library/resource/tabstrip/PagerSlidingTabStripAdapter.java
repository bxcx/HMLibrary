package com.hm.library.resource.tabstrip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * PagerSlidingTabStripAdapter
 * <p/>
 * himi on 2016-03-23 22:16
 * version V1.0
 */
public class PagerSlidingTabStripAdapter extends FragmentPagerAdapter {


    List<String> mTitles;
    List<Fragment> mFragments;

    public PagerSlidingTabStripAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}