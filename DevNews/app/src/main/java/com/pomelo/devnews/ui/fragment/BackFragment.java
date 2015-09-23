package com.pomelo.devnews.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pomelo.devnews.R;
import com.pomelo.devnews.base.BaseFragment;
import com.pomelo.devnews.ui.adapter.BackTabFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BackFragment extends BaseFragment {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewPager();
    }

    private void setupViewPager() {
        BackTabFragmentPagerAdapter adapter = new BackTabFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
}
