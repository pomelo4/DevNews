package com.pomelo.devnews.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pomelo.devnews.R;
import com.pomelo.devnews.base.BaseFragment;
import com.pomelo.devnews.ui.adapter.HappyFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HappyFragment extends BaseFragment {
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager();
    }

    private void setupViewPager() {
        HappyFragmentPagerAdapter adapter = new HappyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
}
