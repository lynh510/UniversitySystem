package com.system.entity;

import java.util.Date;

public class AcademicYear {
	private int id;
	private Date start_date;
	private Date end_date;
	private Date final_date;
	private int year;
	private String season;

	public AcademicYear() {
		super();
	}

	public AcademicYear(int id) {
		super();
		this.id = id;
	}

	public AcademicYear(int id, Date start_date, Date end_date, Date final_date, int year, String season) {
		super();
		this.id = id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.final_date = final_date;
		this.year = year;
		this.season = season;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getFinal_date() {
		return final_date;
	}

	public void setFinal_date(Date final_date) {
		this.final_date = final_date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
