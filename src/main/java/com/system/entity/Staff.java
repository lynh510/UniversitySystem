package com.system.entity;

public class Staff {
	private Person staff_id;
	private String username;
	private String staff_password;
	
	
	public Staff(Person staff_id, String username, String staff_password) {
		super();
		this.staff_id = staff_id;
		this.username = username;
		this.staff_password = staff_password;
	}
	public Person getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(Person staff_id) {
		this.staff_id = staff_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStaff_password() {
		return staff_password;
	}
	public void setStaff_password(String staff_password) {
		this.staff_password = staff_password;
	}
	
	
}
