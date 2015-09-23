package com.pomelo.devnews.cache;

import com.pomelo.greendao.DaoSession;

import java.util.ArrayList;


public abstract class BaseCacheUtil<T> {

	protected static DaoSession mDaoSession;

	/**
	 * 清楚所有缓存
	 */
	public abstract void clearAllCache();

	/**
	 * 读取缓存
	 * @param page
	 * @return
	 */
	public abstract ArrayList<T> getCacheByPage(int page);

	/**
	 * 根据页码获取缓存数据
	 * @param result
	 * @param page
	 */
	public abstract void addResultCache(String result, int page);

}
