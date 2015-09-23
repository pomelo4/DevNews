package com.pomelo.devnews.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BackNews {
    public static final String URL_JAVA_NEWS = "http://api.foofish.net/items/java?l=";
    public static final String URL_PYTHON_NEWS = "http://api.foofish.net/items/python?l=";

    public enum BackType {
        JAVA, PYTHON;
    }

    static final DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    private  int id;
    private String url;
    private String title;
    private String description;
    private String cover;
    private String user;
    private Date date;
    private String content;

    public BackNews() {
    }

    public BackNews(int id, String url, String title, String cover, String user, String description) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.cover = cover;
        this.user = user;
        this.description = description;
    }

    public BackNews(int id, String url, String title, String cover, String user, String date, String description, String content) {
        this(id, url, title, cover, user, description);
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            this.date = new Date();
            e.printStackTrace();
        }
        this.content = content;
    }

    public BackNews(int id, String url, String title, String cover, String user, Date date, String description) {
        this(id, url, title, cover, user, description);
        this.date = date;
    }

    public static String getRequestUrl(BackType type, int page) {

        switch (type) {
            case JAVA:
                return URL_JAVA_NEWS + page;
            case PYTHON:
                return URL_PYTHON_NEWS + page;
            default:
                return "";
        }
    }

    public static ArrayList<BackNews> parse(JSONArray resultsArray) {

        ArrayList<BackNews> backNewses = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {

            BackNews backNews = new BackNews();
            JSONObject jsonObject = resultsArray.optJSONObject(i);

            backNews.setId(jsonObject.optInt("id"));
            backNews.setTitle(jsonObject.optString("title"));
            backNews.setDescription(jsonObject.optString("description"));
            backNews.setUrl(jsonObject.optString("url"));
            backNews.setCover(jsonObject.optString("cover"));

            try {
                backNews.setDate(dateFormat.parse(jsonObject.optString("create_at")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            backNews.setUrl(jsonObject.optString("author"));

            backNewses.add(backNews);

        }
        return backNewses;
    }


    public static ArrayList<BackNews> parseCache(JSONArray resultsArray) {

        ArrayList<BackNews> backNewses = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {

            BackNews backNews = new BackNews();
            JSONObject jsonObject = resultsArray.optJSONObject(i);

            backNews.setId(jsonObject.optInt("id"));
            backNews.setTitle("title");
            backNews.setDescription(jsonObject.optString("description"));
            backNews.setUrl(jsonObject.optString("url"));

            backNews.setCover(jsonObject.optString("cover"));

            try {
                backNews.setDate(dateFormat.parse(jsonObject.optString("create_at")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            backNews.setUrl(jsonObject.optString("author"));

            backNewses.add(backNews);

        }
        return backNewses;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public String getCover() {
        return cover;
    }

    public String getUser() {
        return user;
    }

    public Date getDate() {
        return this.date;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFormatDate() {
        return dateFormat.format(date);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='"+id+'\''+
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cover='" + cover + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}
