package com.pomelo.devnews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pomelo.devnews.ui.fragment.JavaFragment;
import com.pomelo.devnews.ui.fragment.PythonFragment;

public class BackTabFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabs = {"JAVA", "PYTHON"};

    public BackTabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new JavaFragment();
            case 1:
                return new PythonFragment();
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
