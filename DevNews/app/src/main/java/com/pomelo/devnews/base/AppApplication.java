package com.pomelo.devnews.base;

import android.app.Application;
import android.content.Context;

public class AppApplication extends Application {

	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}

	public static Context getContext() {
		return mContext;
	}

}