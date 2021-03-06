package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.system.entity.*;

public class PersonManagement {
	private DepartmentManagement dm;

	public PersonManagement() {
		dm = new DepartmentManagement();
	}

	public Person getPerson(int id) {
		String sqlQuery = "select * from Person p join Department d on d.dept_id = p.dept_id join AcademicYear ay on ay.academic_year_id = d.academic_year_id  where p.person_id = "
				+ id;
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				p.setId(id);
				p.setPerson_picture("/image/" + rs.getString(2));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
				p.setBirthdate(rs.getDate("birthdate"));
				p.setGender(rs.getInt("gender"));
				p.setStatus(rs.getInt("_status"));
				p.setPhone(rs.getString("phone_number"));
				p.setEnroll_date(rs.getDate("enroll_date"));
				p.setEmail(rs.getString("email"));
				p.setAddress(rs.getString("_address"));
				Department d = new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
				AcademicYear ay = new AcademicYear();
				ay.setSeason(rs.getString("season"));
				ay.setYear(rs.getInt("academic_year"));
				d.setAcademic_year(ay);
				p.setDepartment(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public Person getPerson2(int id) {
		String sqlQuery = "select * from Person where person_id = " + id;
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				p.setId(id);
				p.setPerson_picture("/image/" + rs.getString(2));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
				p.setBirthdate(rs.getDate("birthdate"));
				p.setGender(rs.getInt("gender"));
				p.setStatus(rs.getInt("_status"));
				p.setPhone(rs.getString("phone_number"));
				p.setEnroll_date(rs.getDate("enroll_date"));
				p.setEmail(rs.getString("email"));
				p.setAddress(rs.getString("_address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public Person getUserSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") == null) {
			throw new NullPointerException("Have to login first");
		} else {
			return (Person) session.getAttribute("user");
		}

	}

	public List<Person> getPerson() {
		List<Person> users = new ArrayList<>();
		try {
			String sqlQuery = "select * from Person where person_role != 3 and person_role != 4 and person_role != 5 order by person_role";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("person_id"));
				p.setPerson_picture("/image/" + rs.getString(2));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
				p.setBirthdate(rs.getDate("birthdate"));
				p.setGender(rs.getInt("gender"));
				p.setStatus(rs.getInt("_status"));
				p.setPhone(rs.getString("phone_number"));
				p.setEnroll_date(rs.getDate("enroll_date"));
				p.setAddress(rs.getString("_address"));
				p.setEmail(rs.getString("email"));
				p.setDepartment(dm.getDepartment(rs.getInt("dept_id")));
				users.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<Person> getStudent() {
		List<Person> users = new ArrayList<>();
		try {
			String sqlQuery = "select * from Person where person_role = 0  order by person_name";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("person_id"));
				p.setPerson_picture("/image/" + rs.getString(2));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
				p.setBirthdate(rs.getDate("birthdate"));
				p.setGender(rs.getInt("gender"));
				p.setStatus(rs.getInt("_status"));
				p.setPhone(rs.getString("phone_number"));
				p.setEnroll_date(rs.getDate("enroll_date"));
				p.setAddress(rs.getString("_address"));
				p.setEmail(rs.getString("email"));
				p.setDepartment(dm.getDepartment(rs.getInt("dept_id")));
				users.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public void manageUser(int status, int user_id) {
		String sql = "update Person set _status = ? where person_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, status);
			statement.setInt(2, user_id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete_user(int type, int id) {
		String sql = "";
		try {
			if (type == 0) {
				sql = "delete from Student where student_id = " + id;
			} else if (type == 1) {
				sql = "delete from Staff where staff_id = " + id;
			}
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete_person(int p_id) {
		String sql = "delete from Person where person_id = " + p_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			manageUser(3, p_id);
		}
	}

	public void delete_external_login(int id) {
		String sql = "delete from UserExternalLogin where userid = " + id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean edit_account(Person p) {
		String sqlQuery = "Update person set person_picture = ?, person_name =?, birthdate =?, gender =?, phone_number =?, _address =? where person_id=?";
		boolean flag = false;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, p.getPerson_picture());
			statement.setString(2, p.getPerson_name());
			statement.setDate(3, p.getBirthdate());
			statement.setInt(4, p.getGender());
			statement.setString(5, p.getPhone());
			statement.setString(6, p.getAddress());
			statement.setInt(7, p.getId());
			statement.executeUpdate();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
