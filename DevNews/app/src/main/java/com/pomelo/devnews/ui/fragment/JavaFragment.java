package com.pomelo.devnews.ui.fragment;

import com.pomelo.devnews.cache.JavaCacheUtil;
import com.pomelo.devnews.model.BackNews;

import java.util.ArrayList;

public class JavaFragment extends BackNewsFragment {
    @Override
    BackNews.BackType RequestUrl() {
        return BackNews.BackType.JAVA;
    }

    @Override
    void clearAllCache() {
        JavaCacheUtil.getInstance(getActivity()).clearAllCache();
    }

    @Override
    void addResultCache(String cache, int page) {
        JavaCacheUtil.getInstance(getActivity()).addResultCache(cache, page);
    }

    @Override
    ArrayList<BackNews> getCacheByPage(int page) {
        return JavaCacheUtil.getInstance(getActivity()).getCacheByPage(page);
    }
}
