package com.grouphadel.dlsaa.models;

import java.util.Date;

public class NewsArticle extends DatabaseObject {
	private String title;
	private String lead;
	private String body;
	private Date datePosted;

	public NewsArticle() {
		setTitle("Quick brown fox jumps over lazy dog, 5 dead, 23 missing");
		setLead("Lorem ipsum dolor sit amet, consectetur adipisicing elit, "
				+ "sed do eiusmod tempor incididunt ut labore et dolore "
				+ "magna aliqua.");
		setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit, "
				+ "sed do eiusmod tempor incididunt ut labore et dolore "
				+ "magna aliqua. Ut enim ad minim veniam, quis nostrud "
				+ "exercitation ullamco laboris nisi ut aliquip ex ea commodo "
				+ "consequat. Duis aute irure dolor in reprehenderit in "
				+ "voluptate velit esse cillum dolore eu fugiat nulla "
				+ "pariatur. Excepteur sint occaecat cupidatat non proident, "
				+ "sunt in culpa qui officia deserunt mollit anim id est laborum.");
		setDatePosted(new Date());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

}
