package com.pomelo.devnews.ui.fragment;

import com.pomelo.devnews.cache.PythonCacheUtil;
import com.pomelo.devnews.model.BackNews;

import java.util.ArrayList;

public class PythonFragment extends BackNewsFragment {
    @Override
    BackNews.BackType RequestUrl() {
        return BackNews.BackType.PYTHON;
    }

    @Override
    void clearAllCache() {
        PythonCacheUtil.getInstance(getActivity()).clearAllCache();
    }

    @Override
    void addResultCache(String cache, int page) {
        PythonCacheUtil.getInstance(getActivity()).addResultCache(cache, page);
    }

    @Override
    ArrayList<BackNews> getCacheByPage(int page) {
        return PythonCacheUtil.getInstance(getActivity()).getCacheByPage(page);
    }
}
