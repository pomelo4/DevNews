package com.pomelo.devnews.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.pomelo.devnews.model.BackNews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Request4BackNews extends Request<ArrayList<BackNews>> {

    private Response.Listener<ArrayList<BackNews>> listener;

    public Request4BackNews(String url, Response.Listener<ArrayList<BackNews>> listener,
                              Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<ArrayList<BackNews>> parseNetworkResponse(NetworkResponse response) {
        try {
            String resultStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultObj = new JSONObject(resultStr);
            JSONArray postsArray = resultObj.optJSONArray("posts");
            return Response.success(BackNews.parse(postsArray), HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ArrayList<BackNews> response) {
        listener.onResponse(response);
    }
}
