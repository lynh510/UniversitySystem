package com.system.entity;

public class Department {
	private int id;
	private String dept_name;
	private AcademicYear academic_year;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Department() {
		super();
	}

	public Department(int id, String dept_name, AcademicYear academic_year) {
		super();
		this.id = id;
		this.dept_name = dept_name;
		this.academic_year = academic_year;
	}

	public AcademicYear getAcademic_year() {
		return academic_year;
	}

	public void setAcademic_year(AcademicYear academic_year) {
		this.academic_year = academic_year;
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
