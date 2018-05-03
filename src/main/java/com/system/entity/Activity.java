package com.system.entity;

public class Activity {
	private int id;
	private String activity_name;

	public Activity() {
		super();
	}

	public Activity(int id, String activity_name) {
		super();
		this.id = id;
		this.activity_name = activity_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

}
