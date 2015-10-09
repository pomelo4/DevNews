package com.pomelo.devnews.base;

import android.app.Application;
import android.content.Context;
import android.media.Image;

import com.pomelo.devnews.cache.DateBaseInfo;
import com.pomelo.devnews.loader.ImageLoader;
import com.pomelo.greendao.DaoMaster;
import com.pomelo.greendao.DaoSession;

public class AppApplication extends Application {

	private static Context mContext;
	private static ImageLoader imageLoader;
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		imageLoader = ImageLoader.build(this);
	}

	public static Context getContext() {
		return mContext;
	}

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	public static DaoMaster getDaoMaster(Context context) {
		if (daoMaster == null) {
			DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DateBaseInfo.DB_NAME, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	public static DaoSession getDaoSession(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
}