package com.pomelo.devnews.utils;

import android.widget.Toast;

import com.pomelo.devnews.base.AppApplication;

/**
 * 显示Toast的工具类
 */
public class ShowToast {

	/**
	 * @param
	 * @return void
	 * @throws
	 * @Description: 显示短时间Toast
	 */
	public static void Short(CharSequence sequence) {
		Toast.makeText(AppApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
	}

	/**
	 * @param
	 * @return void
	 * @throws
	 * @Description: 显示长时间Toast
	 */
	public static void Long(CharSequence sequence) {
		Toast.makeText(AppApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
	}

}
