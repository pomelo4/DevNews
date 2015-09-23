package com.pomelo.devnews.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MobileNews {

    public static final String URL_ANDROID_NEWS = "http://gank.avosapps.com/api/data/Android/10/";
    public static final String URL_IOS_NEWS = "http://gank.avosapps.com/api/data/iOS/10/";

    public enum MobileType {
        ANDORID, ISO;
    }

    // 作者
    private String who;
    // 时间
    private String publishedAt;
    // 文章标题
    private String desc;
    // 文章地址
    private String url;

    public MobileNews() {
    }

    public MobileNews(String who, String publishedAt, String desc, String url) {
        this.who = who;
        this.publishedAt = publishedAt;
        this.desc = desc;
        this.url = url;
    }

    public static String getRequestUrl(MobileType type, int page) {

        switch (type) {
            case ANDORID:
                return URL_ANDROID_NEWS + page;
            case ISO:
                return URL_IOS_NEWS + page;
            default:
                return "";
        }
    }

    public static ArrayList<MobileNews> parse(JSONArray resultsArray) {

        ArrayList<MobileNews> mobileNewses = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {

            MobileNews mobileNews = new MobileNews();
            JSONObject jsonObject = resultsArray.optJSONObject(i);

            mobileNews.setWho(jsonObject.optString("who"));
            mobileNews.setPublishedAt("publishedAt");
            mobileNews.setDesc(jsonObject.optString("desc"));
            mobileNews.setUrl(jsonObject.optString("url"));

            mobileNewses.add(mobileNews);

        }
        return mobileNewses;
    }

    public static ArrayList<MobileNews> parseCache(JSONArray resultsArray) {

        ArrayList<MobileNews> mobileNewses = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {

            MobileNews mobileNews = new MobileNews();
            JSONObject jsonObject = resultsArray.optJSONObject(i);

            mobileNews.setWho(jsonObject.optString("who"));
            mobileNews.setPublishedAt("publishedAt");
            mobileNews.setDesc(jsonObject.optString("desc"));
            mobileNews.setUrl(jsonObject.optString("url"));

            mobileNewses.add(mobileNews);

        }
        return mobileNewses;
    }

    @Override
    public String toString() {
        return "MobileNews{" +
                "who='" + who + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", desc='" + desc + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
