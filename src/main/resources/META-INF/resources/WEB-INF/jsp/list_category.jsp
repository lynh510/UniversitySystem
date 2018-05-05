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
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/drilldown.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

<jsp:useBean id="tag" class="com.system.models.TagManagement"
	scope="page" />
<title>List Category</title>
<style type="text/css">
.container {
	margin-top: 5%;
}

.single {
	padding: 30px 15px;
	margin-top: 40px;
	background: #fcfcfc;
	border: 1px solid #f0f0f0;
}

.single h3.side-title {
	margin: 0;
	margin-bottom: 10px;
	padding: 0;
	font-size: 20px;
	color: #333;
	text-transform: uppercase;
}

.single h3.side-title:after {
	content: '';
	width: 100%;
	height: 1px;
	background: #ff173c;
	display: block;
	margin-top: 6px;
}

.single ul {
	margin-bottom: 0;
}

.single li {
	color: #666;
	font-size: 14px;
	text-transform: uppercase;
	border-bottom: 1px solid #f0f0f0;
	line-height: 40px;
	display: block;
	text-decoration: none;
}

.single li a:hover {
	color: #ff173c;
}

.single li a {
	float: right;
}

.single li:last-child a {
	border-bottom: 0;
}

.count {
	background-color: silver;
	border-radius: 20px;
	padding: 1%;
	margin-left: 5%;
}

.navbar-nav.navbar-right:last-child {
	margin-right: 3%;
}
</style>
</head>
<body>
	<jsp:include page="qamanager_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<!-- Category -->

				<div class="single category">
					<h3 class="side-title">Tags</h3>
					<a href="/tag/addCategory">Add new category</a>
					<ul class="list-unstyled">
						<c:forEach items="${tags}" var="t">
							<c:set var="count" value="${tag.count_tag_being_used(t.id) }"
								scope="page" />
							<li>${t.description}(Department:${t.department.dept_name})<span
								class="count">Mentioned <c:out value="${count}" />
									time(s)
							</span><a href="/tag/delete/${t.id}">Delete</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<h2>${message}</h2>
			</div>
		</div>
	</div>
</body>
</html>