package com.system.entity;

import java.util.Date;

public class Tag {
	private int id;
	private String description;

	public Tag() {
		super();
	}


	public Tag(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}


	public Tag(String description) {
		super();
		this.description = description;

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
