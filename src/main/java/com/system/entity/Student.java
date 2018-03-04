package com.system.entity;

public class Student {
	private Person student_id;
	private String username;
	private String student_password;

	public Student(Person student_id, String username, String student_password) {
		super();
		this.student_id = student_id;
		this.username = username;
		this.student_password = student_password;
	}

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Person getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Person student_id) {
		this.student_id = student_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStudent_password() {
		return student_password;
	}

	public void setStudent_password(String student_password) {
		this.student_password = student_password;
	}

}
