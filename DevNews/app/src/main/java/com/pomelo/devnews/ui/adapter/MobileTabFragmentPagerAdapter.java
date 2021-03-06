package com.pomelo.devnews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pomelo.devnews.ui.fragment.AndroidFragment;
import com.pomelo.devnews.ui.fragment.iOSFragment;

public class MobileTabFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabs = {"ANDROID", "IOS"};

    public MobileTabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AndroidFragment();
            case 1:
                return new iOSFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
