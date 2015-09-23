package com.pomelo.devnews.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.pomelo.devnews.model.Picture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 无聊图数据请求器
 */
public class Request4Picture extends Request<ArrayList<Picture>> {

	private Response.Listener<ArrayList<Picture>> listener;

	public Request4Picture(String url, Response.Listener<ArrayList<Picture>> listener,
	                       Response.ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.listener = listener;
	}

	@Override
	protected Response<ArrayList<Picture>> parseNetworkResponse(NetworkResponse response) {

		try {
			String resultStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			JSONObject resultObj = new JSONObject(resultStr);
			JSONArray postsArray = resultObj.optJSONArray("results");
			return Response.success(Picture.parse(postsArray), HttpHeaderParser.parseCacheHeaders(response));

		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(ArrayList<Picture> response) {
		listener.onResponse(response);
	}

}
