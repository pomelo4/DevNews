package com.pomelo.devnews.cache;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.pomelo.devnews.base.AppApplication;
import com.pomelo.devnews.model.MobileNews;
import com.pomelo.devnews.net.JSONParser;
import com.pomelo.greendao.iOSCache;
import com.pomelo.greendao.iOSCacheDao;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

public class iOSCacheUtil extends BaseCacheUtil {
    private static iOSCacheUtil instance;
    private static iOSCacheDao mIOSCacheDao;

    private iOSCacheUtil() {
    }

    public static iOSCacheUtil getInstance(Context context) {

        if (instance == null) {

            synchronized (iOSCacheUtil.class) {
                if (instance == null) {
                    instance = new iOSCacheUtil();
                }
            }

            mDaoSession = AppApplication.getDaoSession(context);
            mIOSCacheDao = mDaoSession.getIOSCacheDao();
        }
        return instance;
    }

    /**
     * 清除全部缓存
     */
    public void clearAllCache() {
        mIOSCacheDao.deleteAll();
    }

    /**
     * 根据页码获取缓存数据
     *
     * @param page
     * @return
     */
    @Override
    public ArrayList<MobileNews> getCacheByPage(int page) {

        QueryBuilder<iOSCache> query = mIOSCacheDao.queryBuilder().where(iOSCacheDao
                .Properties.Page.eq("" + page));

        if (query.list().size() > 0) {
            return (ArrayList<MobileNews>) JSONParser.toObject(query.list().get(0).getResult(),
                    new TypeToken<ArrayList<MobileNews>>() {
                    }.getType());
        } else {
            return new ArrayList<MobileNews>();
        }

    }

    /**
     * 添加缓存
     *
     * @param result
     * @param page
     */
    @Override
    public void addResultCache(String result, int page) {
        iOSCache iosCache = new iOSCache();
        iosCache.setResult(result);
        iosCache.setPage(page);
        iosCache.setTime(System.currentTimeMillis());

        mIOSCacheDao.insert(iosCache);
    }

}
