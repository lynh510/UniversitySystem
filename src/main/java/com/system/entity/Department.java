package com.system.entity;

public class Department {
	private int id;
	private String dept_name;

	public Department() {
		super();
	}

	public Department(int id, String dept_name) {
		super();
		this.id = id;
		this.dept_name = dept_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

}
