package com.pomelo.devnews.cache;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.pomelo.devnews.base.AppApplication;
import com.pomelo.devnews.model.BackNews;
import com.pomelo.devnews.net.JSONParser;
import com.pomelo.greendao.JavaCacheDao;
import com.pomelo.greendao.PythonCache;
import com.pomelo.greendao.PythonCacheDao;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;


public class PythonCacheUtil extends BaseCacheUtil {
    private static PythonCacheUtil instance;
    private static PythonCacheDao mPythonCacheDao;

    private PythonCacheUtil() {
    }

    public static PythonCacheUtil getInstance(Context context) {

        if (instance == null) {

            synchronized (PythonCacheUtil.class) {
                if (instance == null) {
                    instance = new PythonCacheUtil();
                }
            }

            mDaoSession = AppApplication.getDaoSession(context);
            mPythonCacheDao = mDaoSession.getPythonCacheDao();
        }
        return instance;
    }

    /**
     * 清楚全部缓存
     */
    public void clearAllCache() {
        mPythonCacheDao.deleteAll();
    }

    /**
     * 根据页码获取缓存数据
     *
     * @param page
     * @return
     */
    @Override
    public ArrayList<BackNews> getCacheByPage(int page) {

        QueryBuilder<PythonCache> query = mPythonCacheDao.queryBuilder().where(JavaCacheDao
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
        PythonCache pythonCache = new PythonCache();
        pythonCache.setResult(result);
        pythonCache.setPage(page);
        pythonCache.setTime(System.currentTimeMillis());

        mPythonCacheDao.insert(pythonCache);
    }
}
