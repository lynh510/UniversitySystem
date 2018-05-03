<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<script
	src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<title>Manage User</title>
<style type="text/css">
.container {
	margin-top: 5%;
}

.form-group {
	margin-top: 3%;
}

.navbar-nav.navbar-right:last-child {
	margin-right: 3%;
}

.dropdown-toggle {
	padding: 13.5% 6% 13% 6%;
}

body {
	background-color: #fff;
}
</style>
</head>
<body>
	<jsp:include page="admin_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="col-md-12">
			<legend>Manage users</legend>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
			style="overflow-x: auto;">
			<a href="/admin/addAccounts">Add more users</a> | <a
				href="/admin/academic_year">Academic Management</a> | <a></a>
			<table class="table table-responsive table-hover table-bordered ">
				<thead>
					<tr class="info">
						<th>Profile Picture</th>
						<th>Status</th>
						<th>Action</th>
						<th>E-Mail</th>
						<th>Name</th>
						<th>Role</th>
						<th>Enroll Date</th>
						<th>Gender</th>
						<th>Birth date</th>
						<th>Address</th>
						<th>Phone number</th>
						<th>Department</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${persons}" var="p">
						<tr>
							<td><img alt="${p.person_name}" width="50px" height="50px"
								src="${p.person_picture}"></td>
							<c:choose>
								<c:when test="${p.status == 0}">
									<td class="success">Active</td>
								</c:when>
								<c:when test="${p.status == 1}">
									<td class="default">Inactive</td>
								</c:when>
								<c:when test="${p.status == 2}">
									<td class="error">Deleted</td>
								</c:when>
							</c:choose>
							<td>
								<div class="btn-group">
									<button type="button"
										class="btn btn-primary btn-sm dropdown-toggle"
										data-toggle="dropdown">
										Options <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<c:choose>
											<c:when test="${p.status == 0}">
												<li><a
													href="/admin/edit_account/${helper.encryptID(p.id)}">Edit</a></li>
												<li><a
													href="/admin/delete/${p.person_role}/${helper.encryptID(p.id)}">Delete</a></li>
											</c:when>
											<c:when test="${p.status == 1}">
												<li><a
													href="/admin/delete/${p.person_role}/${helper.encryptID(p.id)}">Delete</a></li>
												<li><a
													href="/admin/active/${p.person_role}/${helper.encryptID(p.id)}">Activate</a></li>
												<li><a
													href="/admin/resend_active/${p.person_role}/${helper.encryptID(p.id)}">Re-send
														Activation Mail</a></li>
											</c:when>
										</c:choose>
									</ul>
								</div>
							</td>
							<td><a href="#">${p.email}</a></td>
							<td>${p.person_name}</td>
							<td><c:choose>
									<c:when test="${p.person_role == 0}">Student</c:when>
									<c:when test="${p.person_role == 1}">Staff</c:when>
									<c:when test="${p.person_role == 3}">QA Manager</c:when>
									<c:when test="${p.person_role == 4}">QA Coordinator</c:when>
									<c:when test="${p.person_role == 5}">Administrator</c:when>
									<c:otherwise>Anonymous</c:otherwise>
								</c:choose></td>
							<td>${p.enroll_date}</td>
							<td><c:choose>
									<c:when test="${p.gender == 1}">Male</c:when>
									<c:otherwise>Female</c:otherwise>
								</c:choose></td>
							<td>${p.birthdate}</td>
							<td>${p.address }</td>
							<td>${p.phone}</td>
							<td>${p.department.dept_name}
								(${p.department.academic_year.year} -
								${p.department.academic_year.season})</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
</body>
</html>