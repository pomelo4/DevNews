package com.pomelo.devnews.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.pomelo.devnews.constant.ToastMsg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

	public static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";


	public static boolean isEmail(String email) {
		Pattern p = Pattern.compile(REG_EMAIL);
		Matcher m = p.matcher(email);
		return m.matches();
	}


	/**
	 * 判断是否为null、空字符串或者是"null"
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNull(CharSequence... str) {

		for (CharSequence cha : str) {
			if (cha == null || cha.length() == 0 || cha.equals("null")) {
				return true;
			} else {
				return false;
			}
		}

		return true;
	}


}
