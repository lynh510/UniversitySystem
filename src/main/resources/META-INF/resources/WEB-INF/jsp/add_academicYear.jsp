
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
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<link rel="stylesheet prefetch"
	href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css">
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Add Academic Year</title>
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
<script type="text/javascript">
	$(document).ready(function() {
		$("#add_academicYear_form").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/admin/addAcademicYear",
				data : $("#add_academicYear_form").serialize(),
				success : function(response) {
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
</script>
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
							id="username">QA Coordinator </b></span>
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
	<div class="container">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<form id="add_academicYear_form" class="form-horizontal"
				action="admin/addAcademicYear" method="post">
				<!-- Form Name -->
				<h3>Add new academic year</h3>
				<hr />
				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						year start date</label>

					<div class="col-md-5">
						<div id="startdatepicker" class="input-group date"
							data-date-format="dd-mm-yyyy">
							<input id="start_date" name="start_date" class="form-control"
								readonly="" required="" type="text"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i></span>
						</div>
						<script type="text/javascript">
							$(function() {
								$("#startdatepicker").datepicker({
									autoclose : true,
									todayHighlight : true
								}).datepicker('update', new Date());
							});
						</script>
					</div>


				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						year end date</label>

					<div class="col-md-5">
						<div id="enddatepicker" class="input-group date"
							data-date-format="dd-mm-yyyy">
							<input id="end_date" name="end_date" class="form-control"
								readonly="" required="" type="text"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i></span>
						</div>
						<script type="text/javascript">
							$(function() {
								$("#enddatepicker").datepicker({
									autoclose : true,
									todayHighlight : true
								}).datepicker('update', new Date());
							});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						final closure date</label>

					<div class="col-md-5">
						<div id="finaldatepicker" class="input-group date"
							data-date-format="dd-mm-yyyy">
							<input id="final_date" name="final_date" class="form-control"
								readonly="" required="" type="text"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i></span>
						</div>
						<script type="text/javascript">
							$(function() {
								$("#finaldatepicker").datepicker({
									autoclose : true,
									todayHighlight : true
								}).datepicker('update', new Date());
							});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						Year</label>

					<div class="col-md-5">
						<select name="year" class="form-control" required>\
							<option value="" selected="selected" disabled="disabled">Select
								a year</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
							<option value="2021">2021</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						Season</label>

					<div class="col-md-5">
						<select name="season" class="form-control" required>
							<option value="" selected="selected" disabled="disabled">Select
								a season</option>
							<option value="Spring">Spring</option>
							<option value="Summer">Summer</option>
						</select>
					</div>
				</div>
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
		</div>
	</div>
</body>
</html>
