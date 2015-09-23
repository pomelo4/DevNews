package com.pomelo.devnews.cache;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.pomelo.devnews.base.AppApplication;
import com.pomelo.devnews.model.BackNews;
import com.pomelo.devnews.net.JSONParser;
import com.pomelo.greendao.JavaCache;
import com.pomelo.greendao.JavaCacheDao;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

public class JavaCacheUtil extends BaseCacheUtil {
    private static JavaCacheUtil instance;
    private static JavaCacheDao mJavaCacheDao;

    private JavaCacheUtil() {
    }

    public static JavaCacheUtil getInstance(Context context) {

        if (instance == null) {

            synchronized (JavaCacheUtil.class) {
                if (instance == null) {
                    instance = new JavaCacheUtil();
                }
            }

            mDaoSession = AppApplication.getDaoSession(context);
            mJavaCacheDao = mDaoSession.getJavaCacheDao();
        }
        return instance;
    }

    /**
     * 清楚全部缓存
     */
    public void clearAllCache() {
        mJavaCacheDao.deleteAll();
    }

    /**
     * 根据页码获取缓存数据
     *
     * @param page
     * @return
     */
    @Override
    public ArrayList<BackNews> getCacheByPage(int page) {

        QueryBuilder<JavaCache> query = mJavaCacheDao.queryBuilder().where(JavaCacheDao
                .Properties.Page.eq("" + page));

        if (query.list().size() > 0) {
            return (ArrayList<BackNews>) JSONParser.toObject(query.list().get(0).getResult(),
                    new TypeToken<ArrayList<BackNews>>() {
                    }.getType());
        } else {
            return new ArrayList<BackNews>();
        }

    }

    /**
     * 添加Jokes缓存
     *
     * @param result
     * @param page
     */
    @Override
    public void addResultCache(String result, int page) {
        JavaCache javaCache = new JavaCache();
        javaCache.setResult(result);
        javaCache.setPage(page);
        javaCache.setTime(System.currentTimeMillis());

        mJavaCacheDao.insert(javaCache);
    }
}
