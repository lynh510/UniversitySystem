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
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<title>System Statistics</title>
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

.chart {
	margin-left: 5%;
	margin-right: 5%;
	margin-bottom: 5%;
	margin-top: 10%;
}
</style>
</head>
<body>
	<jsp:include page="${navbar}"></jsp:include>
	<div
		style="margin: auto; margin-top: 10%; margin-bottom: 5%; width: 70%">
		<h2>Statistical reports ${message}</h2>
		<select class="form-control" name="forma"
			onchange="location = this.value;">
			<option selected="selected" disabled="disabled">Select an
				academic year</option>
			<c:forEach items="${academicYear}" var="a">
				<option value="/${role}/statistic/${helper.encryptID(a.id)}">${a.year}-
					${a.season}</option>
			</c:forEach>
		</select>
	</div>
	<div id="chart"></div>
	<jsp:include page="chart.jsp"></jsp:include>
</body>
</html>