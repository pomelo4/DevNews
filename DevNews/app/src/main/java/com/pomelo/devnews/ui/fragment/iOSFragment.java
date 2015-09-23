package com.pomelo.devnews.ui.fragment;

import com.pomelo.devnews.cache.iOSCacheUtil;
import com.pomelo.devnews.model.MobileNews;

import java.util.ArrayList;

public class iOSFragment extends MobileNewsFragment {

    @Override
    MobileNews.MobileType RequestUrl() {
        return MobileNews.MobileType.ISO;
    }

    @Override
    void clearAllCache() {
        iOSCacheUtil.getInstance(getActivity()).clearAllCache();
    }

    @Override
    void addResultCache(String cache, int page) {
        iOSCacheUtil.getInstance(getActivity()).addResultCache(cache, page);
    }

    @Override
    ArrayList<MobileNews> getCacheByPage(int page) {
        return iOSCacheUtil.getInstance(getActivity()).getCacheByPage(page);
    }

}
