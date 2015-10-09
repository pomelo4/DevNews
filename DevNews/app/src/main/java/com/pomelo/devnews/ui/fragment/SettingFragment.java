package com.pomelo.devnews.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.pomelo.devnews.R;
import com.pomelo.devnews.base.AppApplication;
import com.pomelo.devnews.loader.ImageLoader;
import com.pomelo.devnews.utils.AppInfoUtil;
import com.pomelo.devnews.utils.FileUtil;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 设置界面碎片
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

	public static final String CLEAR_CACHE = "clear_cache";
	public static final String ABOUT_APP = "about_app";
	public static final String APP_VERSION = "app_version";
	public static final String ENABLE_SISTER = "enable_sister";
	public static final String ENABLE_FRESH_BIG = "enable_fresh_big";

	Preference clearCache;
	Preference aboutApp;
	Preference appVersion;
	CheckBoxPreference enableSister;
	CheckBoxPreference enableBig;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		clearCache = findPreference(CLEAR_CACHE);
		aboutApp = findPreference(ABOUT_APP);
		appVersion = findPreference(APP_VERSION);
		enableSister = (CheckBoxPreference) findPreference(ENABLE_SISTER);
		enableBig = (CheckBoxPreference) findPreference(ENABLE_FRESH_BIG);

		appVersion.setTitle(AppInfoUtil.getVersionName(getActivity()));

        File cacheFile = AppApplication.getImageLoader().getDiskCacheDir(getActivity(), "bitmap");
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		clearCache.setSummary("缓存大小：" + decimalFormat.format(FileUtil.getDirSize(cacheFile)) + "M");


		clearCache.setOnPreferenceClickListener(this);
		aboutApp.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {

		String key = preference.getKey();

		if (key.equals(CLEAR_CACHE)) {
//			ImageLoader.getInstance().clearDiskCache();
//			ShowToast.Short("清除缓存成功");
//			clearCache.setSummary("缓存大小：0.00M");
		} else if (key.equals(ABOUT_APP)) {

		}

		return true;
	}


}
