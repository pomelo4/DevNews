package com.pomelo.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.pomelo.greendao.AndroidCache;
import com.pomelo.greendao.iOSCache;
import com.pomelo.greendao.JavaCache;
import com.pomelo.greendao.PythonCache;
import com.pomelo.greendao.PictureCache;
import com.pomelo.greendao.VideoCache;

import com.pomelo.greendao.AndroidCacheDao;
import com.pomelo.greendao.iOSCacheDao;
import com.pomelo.greendao.JavaCacheDao;
import com.pomelo.greendao.PythonCacheDao;
import com.pomelo.greendao.PictureCacheDao;
import com.pomelo.greendao.VideoCacheDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig androidCacheDaoConfig;
    private final DaoConfig iOSCacheDaoConfig;
    private final DaoConfig javaCacheDaoConfig;
    private final DaoConfig pythonCacheDaoConfig;
    private final DaoConfig pictureCacheDaoConfig;
    private final DaoConfig videoCacheDaoConfig;

    private final AndroidCacheDao androidCacheDao;
    private final iOSCacheDao iOSCacheDao;
    private final JavaCacheDao javaCacheDao;
    private final PythonCacheDao pythonCacheDao;
    private final PictureCacheDao pictureCacheDao;
    private final VideoCacheDao videoCacheDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        androidCacheDaoConfig = daoConfigMap.get(AndroidCacheDao.class).clone();
        androidCacheDaoConfig.initIdentityScope(type);

        iOSCacheDaoConfig = daoConfigMap.get(iOSCacheDao.class).clone();
        iOSCacheDaoConfig.initIdentityScope(type);

        javaCacheDaoConfig = daoConfigMap.get(JavaCacheDao.class).clone();
        javaCacheDaoConfig.initIdentityScope(type);

        pythonCacheDaoConfig = daoConfigMap.get(PythonCacheDao.class).clone();
        pythonCacheDaoConfig.initIdentityScope(type);

        pictureCacheDaoConfig = daoConfigMap.get(PictureCacheDao.class).clone();
        pictureCacheDaoConfig.initIdentityScope(type);

        videoCacheDaoConfig = daoConfigMap.get(VideoCacheDao.class).clone();
        videoCacheDaoConfig.initIdentityScope(type);

        androidCacheDao = new AndroidCacheDao(androidCacheDaoConfig, this);
        iOSCacheDao = new iOSCacheDao(iOSCacheDaoConfig, this);
        javaCacheDao = new JavaCacheDao(javaCacheDaoConfig, this);
        pythonCacheDao = new PythonCacheDao(pythonCacheDaoConfig, this);
        pictureCacheDao = new PictureCacheDao(pictureCacheDaoConfig, this);
        videoCacheDao = new VideoCacheDao(videoCacheDaoConfig, this);

        registerDao(AndroidCache.class, androidCacheDao);
        registerDao(iOSCache.class, iOSCacheDao);
        registerDao(JavaCache.class, javaCacheDao);
        registerDao(PythonCache.class, pythonCacheDao);
        registerDao(PictureCache.class, pictureCacheDao);
        registerDao(VideoCache.class, videoCacheDao);
    }
    
    public void clear() {
        androidCacheDaoConfig.getIdentityScope().clear();
        iOSCacheDaoConfig.getIdentityScope().clear();
        javaCacheDaoConfig.getIdentityScope().clear();
        pythonCacheDaoConfig.getIdentityScope().clear();
        pictureCacheDaoConfig.getIdentityScope().clear();
        videoCacheDaoConfig.getIdentityScope().clear();
    }

    public AndroidCacheDao getAndroidCacheDao() {
        return androidCacheDao;
    }

    public iOSCacheDao getIOSCacheDao() {
        return iOSCacheDao;
    }

    public JavaCacheDao getJavaCacheDao() {
        return javaCacheDao;
    }

    public PythonCacheDao getPythonCacheDao() {
        return pythonCacheDao;
    }

    public PictureCacheDao getPictureCacheDao() {
        return pictureCacheDao;
    }

    public VideoCacheDao getVideoCacheDao() {
        return videoCacheDao;
    }

}