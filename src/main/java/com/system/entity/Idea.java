package com.system.entity;

import java.util.Date;

public class Idea {
	private int id;
	private String description;
	private Person person;
	private Date post_date;
	private Date close_date;
	private int views;
	private int status;

	public Idea() {
		super();
	}

	public Idea(int id, String description, Person person, Date post_date, Date close_date, int views, int status) {
		super();
		this.id = id;
		this.description = description;
		this.person = person;
		this.post_date = post_date;
		this.close_date = close_date;
		this.views = views;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
