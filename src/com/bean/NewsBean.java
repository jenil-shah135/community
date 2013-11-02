package com.bean;

import java.util.Date;

public class NewsBean {
	private Integer newsID;
	private String newsTitle;
	private String newsDesc;
	private Date newsAddedDate;
	public Integer getNewsID() {
		return newsID;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public String getNewsDesc() {
		return newsDesc;
	}
	public Date getNewsAddedDate() {
		return newsAddedDate;
	}
	public void setNewsID(Integer newsID) {
		this.newsID = newsID;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
	}
	public void setNewsAddedDate(Date newsAddedDate) {
		this.newsAddedDate = newsAddedDate;
	}	
}