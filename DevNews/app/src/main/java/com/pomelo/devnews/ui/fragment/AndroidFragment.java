package com.pomelo.devnews.ui.fragment;

import com.pomelo.devnews.cache.AndroidCacheUtil;
import com.pomelo.devnews.model.MobileNews;

import java.util.ArrayList;

public class AndroidFragment extends MobileNewsFragment {
    @Override
    MobileNews.MobileType RequestUrl() {
        return MobileNews.MobileType.ANDORID;
    }

    @Override
    void clearAllCache() {
        AndroidCacheUtil.getInstance(getActivity()).clearAllCache();
    }

    @Override
    void addResultCache(String cache, int page) {
        AndroidCacheUtil.getInstance(getActivity()).addResultCache(cache, page);
    }

    @Override
    ArrayList<MobileNews> getCacheByPage(int page) {
        return AndroidCacheUtil.getInstance(getActivity()).getCacheByPage(page);
    }

}

