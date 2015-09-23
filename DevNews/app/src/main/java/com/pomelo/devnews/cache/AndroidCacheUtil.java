package com.pomelo.devnews.cache;

import android.content.Context;

import com.pomelo.devnews.base.AppApplication;
import com.pomelo.devnews.model.MobileNews;
import com.pomelo.greendao.AndroidCache;
import com.pomelo.greendao.AndroidCacheDao;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

public class AndroidCacheUtil extends BaseCacheUtil {

    private static AndroidCacheUtil instance;
    private static AndroidCacheDao mAndroidCacheDao;

    private AndroidCacheUtil() {
    }

    public static AndroidCacheUtil getInstance(Context context) {

        if (instance == null) {

            synchronized (AndroidCacheUtil.class) {
                if (instance == null) {
                    instance = new AndroidCacheUtil();
                }
            }

            mDaoSession = AppApplication.getDaoSession(context);
            mAndroidCacheDao = mDaoSession.getAndroidCacheDao();
        }
        return instance;
    }

    /**
     * 清楚全部缓存
     */
    @Override
    public void clearAllCache() {
        mAndroidCacheDao.deleteAll();
    }

    /**
     * 根据页码获取缓存数据
     * @param page
     * @return
     */
    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<AndroidCache> query = mAndroidCacheDao.queryBuilder().where(AndroidCacheDao
                .Properties.Page.eq("" + page));

        if (query.list().size() > 0) {
            try {
                return MobileNews.parseCache(new JSONArray(query.list().get(0)
                        .getResult()));
            } catch (JSONException e) {
                e.printStackTrace();
                return new ArrayList<MobileNews>();
            }
        } else {
            return new ArrayList<MobileNews>();
        }
    }

    /**
     * 添加News缓存
     * @param result
     * @param page
     */
    @Override
    public void addResultCache(String result, int page) {
        AndroidCache freshNewsCache = new AndroidCache();
        freshNewsCache.setResult(result);
        freshNewsCache.setPage(page);
        freshNewsCache.setTime(System.currentTimeMillis());

        mAndroidCacheDao.insert(freshNewsCache);
    }
}
