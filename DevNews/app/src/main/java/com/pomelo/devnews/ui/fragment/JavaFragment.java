package com.pomelo.devnews.ui.fragment;

import com.pomelo.devnews.model.BackNews;

/**
 * Created by Administrator on 2015/9/23.
 */
public class JavaFragment extends BackNewsFragment {
    @Override
    BackNews.BackType RequestUrl() {
        return BackNews.BackType.JAVA;
    }
}
