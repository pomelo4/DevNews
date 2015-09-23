package com.pomelo.devnews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pomelo.devnews.ui.fragment.PictureFragment;
import com.pomelo.devnews.ui.fragment.VideoFragment;

public class HappyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabs = {"妹子图", "小视频"};

    public HappyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new PictureFragment();
            case 1:
                return new VideoFragment();
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
