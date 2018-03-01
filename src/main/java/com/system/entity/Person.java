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
	private String city;
	private String country;
	private String description;

	public Person() {
		super();
	}

	public Person(String person_picture, String person_name, int person_role, Date birthdate, int gender, int status,
			String phone, Date enroll_date, String city, String country, String description) {
		super();
		this.person_picture = person_picture;
		this.person_name = person_name;
		this.person_role = person_role;
		this.birthdate = birthdate;
		this.gender = gender;
		this.status = status;
		this.phone = phone;
		this.enroll_date = enroll_date;
		this.city = city;
		this.country = country;
		this.description = description;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
