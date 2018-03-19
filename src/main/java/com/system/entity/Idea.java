package com.system.entity;

import java.util.Date;

public class Idea {
	private int id;
	private String title;
	private String content;
	private Person person;
	private Date post_date;
	private Date close_date;
	private int mode;
	private int views;
	private int status;

	public Idea() {
		super();
	}

	public Idea(int id) {
		super();
		this.id = id;
	}

	public Idea(int id, String title, String content, Person person, Date post_date, Date close_date, int mode,
			int views, int status) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.person = person;
		this.post_date = post_date;
		this.close_date = close_date;
		this.mode = mode;
		this.views = views;
		this.status = status;
	}

	
	
	public Idea(int id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getPost_date() {
		return post_date;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}

	public Date getClose_date() {
		return close_date;
	}

	public void setClose_date(Date close_date) {
		this.close_date = close_date;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
