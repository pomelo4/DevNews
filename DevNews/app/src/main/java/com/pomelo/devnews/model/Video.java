package com.pomelo.devnews.model;

public class Video {

	public static final String URL_VIDEOS = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_video_comments&page=";

	private String title;
	private String video_source;
	private String url;
	private String desc;
	private String imgUrl;
	private String imgUrl4Big;

	public Video() {
	}

	public Video(String title, String video_source, String url, String desc, String imgUrl, String imgUrl4Big) {
		this.title = title;
		this.video_source = video_source;
		this.url = url;
		this.desc = desc;
		this.imgUrl = imgUrl;
		this.imgUrl4Big = imgUrl4Big;
	}

	public static String getUrlVideos(int page) {
		return URL_VIDEOS + page;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideo_source() {
		return video_source;
	}

	public void setVideo_source(String video_source) {
		this.video_source = video_source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl4Big() {
		return imgUrl4Big;
	}

	public void setImgUrl4Big(String imgUrl4Big) {
		this.imgUrl4Big = imgUrl4Big;
	}

	@Override
	public String toString() {
		return "Video{" +
				"title='" + title + '\'' +
				", video_source='" + video_source + '\'' +
				", url='" + url + '\'' +
				", desc='" + desc + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", imgUrl4Big='" + imgUrl4Big + '\'' +
				'}';
	}
}
