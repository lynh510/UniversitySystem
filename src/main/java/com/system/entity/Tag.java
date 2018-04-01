package com.system.entity;

import java.util.Date;

public class Tag {
	private int id;
	private String description;
	private Date create_time;
	private Date close_time;
	private int status;

	public Tag() {
		super();
	}


	public Tag(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}


	public Tag(int id, String description, Date create_time, Date close_time, int status) {
		super();
		this.id = id;
		this.description = description;
		this.create_time = create_time;
		this.close_time = close_time;
		this.status = status;
	}


	public Date getCreate_time() {
		return create_time;
	}


	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


	public Date getClose_time() {
		return close_time;
	}


	public void setClose_time(Date close_time) {
		this.close_time = close_time;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
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

}
