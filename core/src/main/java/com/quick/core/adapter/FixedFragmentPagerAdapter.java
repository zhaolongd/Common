package com.quick.core.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/5 15:32
 */

public class FixedFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments = null;
    private String[] mTitles = null;

    public void setFragmentList(Fragment... fragments) {
        mFragments = fragments;
        notifyDataSetChanged();
    }

    public void setTitles(String... titles) {
        mTitles = titles;
    }

    public FixedFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments[i];
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && mTitles.length == getCount()) {
            return mTitles[position];
        }
        Fragment fragment = mFragments[position];
        if (fragment instanceof PageTitle) {
            PageTitle pageTitle = (PageTitle) fragment;
            return pageTitle.getPageTitle();
        }
        return "";
    }

    public interface PageTitle {
        CharSequence getPageTitle();
    }
}
