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
<title>Manage User</title>
<style type="text/css">
.container {
	margin-top: 15%;
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
	<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#navbar-ex-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse" id="navbar-ex-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" aria-expanded="true"> <img alt=""
						class="img-circle" id="userpicture" src="${welcom.person_picture}"
						width="30" height="30"> <span class="hidden-xs"><b
							id="username">Admin </b></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/admin.html"><i class="fa fa-fw fa-suitcase"></i>
								Manage User</a></li>
						<li><a href="/statistics.html"><i
								class="fa fa-fw fa-bar-chart-o"></i> System Statistics</a></li>
						<li><a href="#"><i class="fa fa-fw fa-user"></i> Edit
								Profile</a></li>
						<li><a href="#"><i class="fa fa-fw fa-cog"></i> Change
								Password</a></li>
						<li class="divider"></li>
						<li><a href="/student/logout"><i
								class="fa fa-fw fa-power-off"></i> Logout</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
	<div class="container">
		<div class="col-md-12">
			<legend>Manage users</legend>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
			style="overflow-x: auto;">
			<a href="/admin/addStudents">Add more users</a> | <a
				href="/admin/academic_year">Academic Management</a> | <a></a>
			<table class="table table-responsive table-hover table-bordered ">
				<thead>
					<tr class="info">
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
							<c:choose>
								<c:when test="${p.status == 0}">
									<td class="success">Active</td>
								</c:when>
								<c:when test="${p.status == 1}">
									<td class="error">Block</td>
								</c:when>
								<c:when test="${p.status == 2}">
									<td class="default">Inactive</td>
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
										<li><a href="#">Delete</a></li>
										<li><a href="#">Activate</a></li>
										<li><a href="#">Block</a></li>
										<li><a href="#">Re-send Activation Mail</a></li>
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
							<td>${p.department.dept_name}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
</body>
</html>