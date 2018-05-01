package com.system.entity;

import java.sql.Date;

public class Person {
	private int id;
	private String person_picture;
	private String person_name;
	private int person_role;
	private Date birthdate;
	private int gender;
	private int status;
	private String phone;
	private Date enroll_date;
	private String address;
	private String email;
	private String description;
	private Department department;

	public Person() {
		super();
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Person(int id, String person_picture, String person_name, int person_role, Date birthdate, int gender,
			int status, String phone, Date enroll_date, String address, String email, String description) {
		super();
		this.id = id;
		this.person_picture = person_picture;
		this.person_name = person_name;
		this.person_role = person_role;
		this.birthdate = birthdate;
		this.gender = gender;
		this.status = status;
		this.phone = phone;
		this.enroll_date = enroll_date;
		this.address = address;
		this.email = email;
		this.description = description;
	}

	public Person(String person_picture, String person_name, int person_role, Date birthdate, int gender, int status,
			String phone, String address, String email, String description, Department department) {
		super();
		this.person_picture = person_picture;
		this.person_name = person_name;
		this.person_role = person_role;
		this.birthdate = birthdate;
		this.gender = gender;
		this.status = status;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.description = description;
		this.department = department;
	}
	
	

	public Person(int id, String person_picture, String person_name, Date birthdate, int gender, String phone,
			String address) {
		super();
		this.id = id;
		this.person_picture = person_picture;
		this.person_name = person_name;
		this.birthdate = birthdate;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPerson_picture() {
		return person_picture;
	}

	public void setPerson_picture(String person_picture) {
		this.person_picture = person_picture;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public int getPerson_role() {
		return person_role;
	}

	public void setPerson_role(int person_role) {
		this.person_role = person_role;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getEnroll_date() {
		return enroll_date;
	}

	public void setEnroll_date(Date enroll_date) {
		this.enroll_date = enroll_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
