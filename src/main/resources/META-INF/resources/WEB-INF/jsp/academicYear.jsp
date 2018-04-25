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
<title>Department Manager</title>
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
</head>
<body>
	<div id="navbar"
		class="navbar navbar-default navbar-fixed-top navbar-inverse">
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
							id="username"> <%-- ${welcom.person_name} --%>QA Coordinator
						</b></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/addDepartment.html"><i
								class="fa fa-fw fa-plus"></i> Add Department</a></li>
						<li><a href="/departments.html"><i
								class="fa fa-fw fa-suitcase"></i> Department Manager</a></li>
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
	<!-- <script type="text/javascript">
	$( "#navbar" ).load( "/navbar.html" );
</script> -->

	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<a href="/admin/add_academic_year">Add new academic year</a>
				<div class="list-group product-bread-a">
					<p href="#" class="list-group-item product-bread-a"
						style="background: #dededd">
						Academic Year
						<button class="btn" style="float: right;">
							<i class="fa fa-trash-o"></i>
						</button>
					</p>
				</div>
				<div id="ideas" style="overflow-x: auto;">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Final Closure</th>
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
									<td>${a.year}</td>
									<td>${a.season}</td>
									<td><a href="/admin/view_department/${helper.encryptID(a.id)}">View
											Departments</a></td>
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