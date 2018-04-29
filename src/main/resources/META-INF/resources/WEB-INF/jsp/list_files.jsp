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
<jsp:useBean id="iafManager"
	class="com.system.models.IdeaAttachFileManagement" scope="page" />
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Department Manager</title>
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
	<jsp:include page="qamanager_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<h3 style="color: red">${message}</h3>
				<c:forEach items="${departments}" var="dept">
					<form id="download_form_${dept.id}" action="/qamanager/zip"
						method="post">
						<div class="list-group product-bread-a">
							<p href="#" class="list-group-item product-bread-a"
								style="background: #dededd">
								${dept.dept_name} ( ${dept.academic_year.year} -
								${dept.academic_year.season})
								<button class="btn" style="float: right;">
									<i class="fa fa-download"></i>
								</button>
							</p>
						</div>
						<div id="" style="overflow-x: auto;">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>Idea</th>
										<th>File Name</th>
										<th>Type</th>
										<th>Download</th>
									</tr>
								</thead>
								<c:forEach items="${iafManager.getbyDepartment(dept.id)}"
									var="attach">
									<tbody>
										<tr>
											<td><a href="/idea/${attach.idea.id}">View Idea</a></td>
											<td>${attach.old_name}</td>
											<td>${attach.file_type}</td>
											<td><a
												href="/qamanager/downloadfile/${helper.encryptID(attach.id)}">Download</a></td>
											<td><input name="file_id"
												value="${helper.encryptID(attach.id)}" type="checkbox"></td>
										</tr>
									</tbody>
								</c:forEach>
							</table>
						</div>
					</form>
				</c:forEach>

			</div>
		</div>
	</div>
</body>
</html>