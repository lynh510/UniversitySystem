
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
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<title>${action} Academic Year</title>
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
		$("#${action}_academicYear_form").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/admin/${action}AcademicYear",
				data : $("#${action}_academicYear_form").serialize(),
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
	<jsp:include page="admin_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<form id="${action}_academicYear_form" class="form-horizontal"
				action="admin/${action}AcademicYear" method="post">
				<!-- Form Name -->
				<h3>${action} Academic Year</h3>
				<hr />
				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						year start date</label>

					<div class="col-md-5">
						<div id="startdatepicker" class="input-group date"
							data-date-format="yyyy-mm-dd">
							<input id="start_date" name="start_date" class="form-control"
								value="" readonly="" required type="text"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i></span>
						</div>
						<script type="text/javascript">
							$(function() {
								$("#startdatepicker").datepicker({
									autoclose : true,
									todayHighlight : true
								}).datepicker('update',
										"${academicYear.start_date}");
							});
						</script>
					</div>


				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						year end date</label>

					<div class="col-md-5">
						<div id="enddatepicker" class="input-group date"
							data-date-format="yyyy-mm-dd">
							<input id="end_date" name="end_date" class="form-control"
								readonly="" required type="text"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i></span>
						</div>
						<script type="text/javascript">
							$(function() {
								$("#enddatepicker").datepicker({
									autoclose : true,
									todayHighlight : true
								}).datepicker('update',
										"${academicYear.end_date}");
							});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						final closure date</label>

					<div class="col-md-5">
						<div id="finaldatepicker" class="input-group date"
							data-date-format="yyyy-mm-dd">
							<input id="final_date" name="final_date" class="form-control"
								readonly="" required type="text"> <span
								class="input-group-addon"><i
								class="glyphicon glyphicon-calendar"></i></span>
						</div>
						<script type="text/javascript">
							$(function() {
								$("#finaldatepicker").datepicker({
									autoclose : true,
									todayHighlight : true
								}).datepicker('update',
										"${academicYear.final_date}");
							});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						Year</label>

					<div class="col-md-5">
						<select id="year" name="year" class="form-control" required>\
							<option value="" selected="selected" disabled="disabled">Select
								a year</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
							<option value="2021">2021</option>
						</select>
						<script type="text/javascript">
							$(document)
									.ready(
											function() {
												$(
														"#year option[value='${academicYear.year}']")
														.prop('selected', true);
											});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Academic
						Season</label>

					<div class="col-md-5">
						<select id="season" name="season" class="form-control" required>
							<option value="" selected="selected" disabled="disabled">Select
								a season</option>
							<option value="Spring">Spring</option>
							<option value="Summer">Summer</option>
						</select>
						<script type="text/javascript">
							$(document)
									.ready(
											function() {
												$(
														"#season option[value='${academicYear.season}']")
														.prop('selected', true);
											});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="saveBtn"></label> <input
						type="hidden" name="id"
						value="${helper.encryptID(academicYear.id)}">
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
