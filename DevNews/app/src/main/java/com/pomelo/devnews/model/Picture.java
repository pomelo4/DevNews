package com.pomelo.devnews.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Picture implements Serializable {

	public static final String URL_SISTER = "http://gank.avosapps.com/api/data/%E7%A6%8F%E5%88%A9/10/";


	private String who;
	private String publishedAt;
	private String desc;
	private String url;

	public Picture() {
	}

	public Picture(String who, String publishedAt, String desc, String url) {
		this.who = who;
		this.publishedAt = publishedAt;
		this.desc = desc;
		this.url = url;
	}

	public static ArrayList<Picture> parse(JSONArray resultsArray) {

		ArrayList<Picture> pictures = new ArrayList<>();

		for (int i = 0; i < resultsArray.length(); i++) {

			Picture picture = new Picture();
			JSONObject jsonObject = resultsArray.optJSONObject(i);

			picture.setWho(jsonObject.optString("who"));
			picture.setPublishedAt("publishedAt");
			picture.setDesc(jsonObject.optString("desc"));
			picture.setUrl(jsonObject.optString("url"));

			pictures.add(picture);

		}
		return pictures;
	}

	public static ArrayList<Picture> parseCache(JSONArray resultsArray) {

		ArrayList<Picture> pictures = new ArrayList<>();

		for (int i = 0; i < resultsArray.length(); i++) {

			Picture picture = new Picture();
			JSONObject jsonObject = resultsArray.optJSONObject(i);

			picture.setWho(jsonObject.optString("who"));
			picture.setPublishedAt("publishedAt");
			picture.setDesc(jsonObject.optString("desc"));
			picture.setUrl(jsonObject.optString("url"));

			pictures.add(picture);

		}
		return pictures;
	}

	public static String getUrlPictures(int page) {
		return URL_SISTER + page;
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
