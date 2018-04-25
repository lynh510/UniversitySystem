package com.system.entity;

public class Tag {
	private int id;
	private String description;
	private Department department;
	private int status;

	public Tag() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Tag(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}


	public Tag(String description, Department department, int status) {
		super();
		this.description = description;
		this.department = department;
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
