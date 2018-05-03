<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1"
	crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<title>Academic Year</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
.container {
	margin-top: 10%;
}

/* Dates */
.agenda .agenda-date {
	width: 170px;
}

.agenda .agenda-date .dayofmonth {
	width: 40px;
	font-size: 36px;
	line-height: 36px;
	float: left;
	text-align: right;
	margin-right: 10px;
}

.agenda .agenda-date .shortdate {
	font-size: 0.75em;
}

.navbar-nav.navbar-right:last-child {
	margin-right: 3%;
}

/* Times */
.agenda .agenda-time {
	width: 140px;
}

.product-bread-a {
	-webkit-border-radius: 0px !important;
	-moz-border-radius: 0px !important;
	border-radius: 0px !important;
}

.product-bread-a a {
	color: #718ac3;
}

table {
	margin-left: 2%;
	width: 95% !important;
	overflow-x: auto;
}
</style>
<script type="text/javascript">
	function onDelete(id) {
			$.ajax({
				type : "Post",
				url : "/admin/removeAcademicYear",
				data : $("#delete-form-" + id ).serialize(),
				success : function(response) {
					jQuery('#academic' ).load(' #academic');		
					alert(response.message);
				},
				error : function(xhr, response, error) {
					var err = JSON.parse(xhr.responseText);
					alert(err.message);
				}
			});
	}
</script>
</head>
<body>
	<jsp:include page="admin_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<a href="/admin/add_academic_year">Add new academic year</a>
				<div class="list-group product-bread-a">
					<p href="#" class="list-group-item product-bread-a"
						style="background: #dededd">Academic Year</p>
				</div>
				<div id="academic" style="overflow-x: auto;">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Final Closure</th>
								<th>Status</th>
								<th>Year</th>
								<th>Season</th>
								<th></th>
							</tr>
						</thead>
						<c:forEach items="${academicYear}" var="a">
							<tbody>
								<tr>
									<td>${a.start_date}</td>
									<td>${a.end_date}</td>
									<td>${a.final_date}</td>
									<td><c:choose>
											<c:when test="${a.status  == 0}">Opening</c:when>
											<c:when test="${a.status  == 1}">Closing</c:when>
											<c:when test="${a.status  == 2}">Closed</c:when>
										</c:choose></td>
									<td>${a.year}</td>
									<td>${a.season}</td>
									<td><a
										href="/admin/view_department/${helper.encryptID(a.id)}">View
											Departments</a></td>
									<td><c:choose>
											<c:when test="${a.status  != 2}">
												<a
													href="/admin/edit_academic_year/${helper.encryptID(a.id)}">Edit</a>
											</c:when>
										</c:choose></td>

									<td><c:choose>
											<c:when test="${a.status  == 0}">
												<form id="delete-form-${a.id}"
													action="/admin/removeAcademicYear" method="post">
													<input type="hidden" name="id"
														value="${helper.encryptID(a.id)}">
												</form>
												<a onclick="onDelete(${a.id})">Delete</a>
											</c:when>
										</c:choose></td>
								</tr>
							</tbody>
						</c:forEach>

					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>