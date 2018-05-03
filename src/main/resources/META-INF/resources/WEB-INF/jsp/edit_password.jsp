
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
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<title>${action}AcademicYear</title>
<style type="text/css">
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
		$("#edit_password").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/${role}/change_password",
				data : $("#edit_password").serialize(),
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
	<jsp:include page="${navbar}"></jsp:include>
	<div class="container">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<form id="edit_password" class="form-horizontal"
				action="${role}/change_password" method="post">
				<!-- Form Name -->
				<h3>${displayName} Change Password</h3>
				<hr />
				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Old
						password</label>
					<div class="col-md-5">
						<input type="password" required="" name="old_password">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">New
						password</label>

					<div class="col-md-5">
						<input type="password" required="" name="new_password">
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Confirm
						password</label>

					<div class="col-md-5">
						<input type="password" required="" name="confirm_password">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="saveBtn"></label> <input
						type="hidden" name="id" value="${helper.encryptID(user.id)}">
					<div class="col-md-8">
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
