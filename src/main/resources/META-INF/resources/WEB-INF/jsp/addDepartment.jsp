<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/list_idea.css">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />ss
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript">
	$(document).ready(function() {
		$("#add_department_form").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/admin/addDepartment",
				data : $("#add_department_form").serialize(),
				success : function(response) {
					jQuery('#lstDepartments').load(' #lstDepartments');
					alert(response.message);
				},
				error : function(xhr, response, error) {
					var err = JSON.parse(xhr.responseText);
					alert(err.message);
				}
			});
			return false;
		});
	});
	function onDelete(id) {
		$.ajax({
			type : "Post",
			url : "/admin/removeDepartment",
			data : $("#delete_dept_form_" + id).serialize(),
			success : function(response) {
				jQuery('#lstDepartments').load(' #lstDepartments');
				alert(response.message);
			},
			error : function(xhr, response, error) {
				var err = JSON.parse(xhr.responseText);
				alert(err.message);
			}
		});
	}
</script>
<title>Add Department</title>
<style type="text/css">
.container {
	margin-top: 10%;
}

.form-group {
	margin-top: 3%;
}

.navbar-nav.navbar-right:last-child {
	margin-right: 3%;
}

body {
	background-color: #fff;
}
</style>
</head>
<body>
	<jsp:include page="admin_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<a href="/admin/academic_year">Back to Academic Year</a>
			<c:choose>
				<c:when test="${academicYear.status == 0}">
					<form id="add_department_form" class="form-horizontal"
						action="/admin/addDepartment" method="post">
						<!-- Form Name -->
						<h3>Add new department for ${academicYear.season}
							${academicYear.year}</h3>
						<hr />
						<!-- Text input-->
						<div class="form-group">
							<label class="col-md-4 control-label" for="title">Department
								Name</label>

							<div class="col-md-5">
								<input id="title" name="dept_name" type="text"
									placeholder="Department name" class="form-control input-md"
									required> <input type="hidden" name="academic_year_id"
									value="${academicYear.id}">
							</div>
						</div>

						<!-- Button (Double) -->
						<div class="form-group">
							<label class="col-md-4 control-label" for="saveBtn"></label>

							<div class="col-md-8">
								<h6 style="color: red" id="errors">${error}</h6>
								<button id="saveBtn" type="submit" name="saveBtn"
									class="btn btn-primary">Save</button>
								<button id="cancelBtn" type="reset" name="cancelBtn"
									class="btn btn-inverse">Cancel</button>
							</div>
						</div>
					</form>
				</c:when>
			</c:choose>
		</div>
	</div>
	<div class="container">
		<div id="lstDepartments"
			class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<form class="form-horizontal" action="/admin/addDepartment"
				method="post">
				<!-- Form Name -->
				<h3>All departments of ${academicYear.season}
					${academicYear.year}</h3>
				<hr />
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Department Name</th>
							<th>Year</th>
							<th>Season</th>
							<th>Action</th>
						</tr>
					</thead>
					<c:forEach items="${departments}" var="d">
						<tbody>
							<tr>
								<td>${d.dept_name}</td>
								<td>${d.academic_year.year}</td>
								<td>${d.academic_year.season}</td>
								<td><c:choose>
										<c:when test="${academicYear.status == 0}">
											<form id="delete_dept_form_${d.id}" method="post" action="">
												<input type="hidden" name="dept_id"
													value="${helper.encryptID(d.id)}">
											</form>
											<a onclick="onDelete(${d.id})">Remove</a>
										</c:when>
									</c:choose></td>
							</tr>
						</tbody>
					</c:forEach>

				</table>
			</form>
		</div>
	</div>
</body>
</html>