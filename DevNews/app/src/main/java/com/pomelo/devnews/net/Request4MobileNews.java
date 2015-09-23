package com.pomelo.devnews.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.pomelo.devnews.model.MobileNews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 数据请求器
 */
public class Request4MobileNews extends Request<ArrayList<MobileNews>> {

    private Response.Listener<ArrayList<MobileNews>> listener;

    public Request4MobileNews(String url, Response.Listener<ArrayList<MobileNews>> listener,
                           Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<ArrayList<MobileNews>> parseNetworkResponse(NetworkResponse response) {
        try {
            String resultStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultObj = new JSONObject(resultStr);
            JSONArray postsArray = resultObj.optJSONArray("results");
            return Response.success(MobileNews.parse(postsArray), HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ArrayList<MobileNews> response) {
        listener.onResponse(response);
    }
}
