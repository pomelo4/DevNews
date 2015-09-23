package com.pomelo.devnews.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pomelo.devnews.base.AppApplication;

public class RequestManager {

	public static RequestQueue mRequestQueue = Volley.newRequestQueue(AppApplication.getContext());

	private RequestManager() {
	}

	public static void addRequest(Request<?> request, Object tag) {
		if (tag != null) {
			request.setTag(tag);
		}

		mRequestQueue.add(request);
	}

	public static void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
}
