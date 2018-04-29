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
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Add new category</title>
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
	var check = function() {
		var title = document.getElementById('title');
		var splChars = "*|,\":<>[]{}`\';()@&$#%";
		for (var i = 0; i < title.value.length; i++) {
			if (splChars.indexOf(title.value.charAt(i)) != -1) {
				document.getElementById('msgCheck').innerHTML = "The category name mustn't have special character!";
				title.focus();
				return false;
			}
			if (splChars.indexOf(title.value.charAt(i)) == -1) {
				document.getElementById('msgCheck').innerHTML = "";
				title.focus();
				return true;
			}
		}
	}
</script>
</head>
<body>
	<jsp:include page="qamanager_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<form id="new_tag_form" class="form-horizontal" action="/tag/add" method="post">
				<!-- Form Name -->
				<h3>Add new tag</h3>
				<hr />
				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Tag Name</label>

					<div class="col-md-5">
						<input id="title" name="tag_name" type="text"
							placeholder="Category name" class="form-control input-md"
							required onkeyup='check();'>
						<h6 style="color: red; margin-left: 5%;" id="msgCheck"></h6>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="title">Department
						Name</label>

					<div class="col-md-5">
						<select class="form-control" name="dept_id" required>
							<option selected="selected" value="" disabled="disabled">Choose
								department</option>
							<c:forEach items="${departments}" var="d">
								<option value="${d.id }">${d.dept_name}
									(${d.academic_year.season} - ${d.academic_year.year})</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<!-- Button (Double) -->
				<div class="form-group">
					<label class="col-md-4 control-label" for="saveBtn"></label>

					<div class="col-md-8">
						<h6 style="color: red" id="errors">${error}</h6>
						<button id="saveBtn" type="submit" name="saveBtn" class="btn btn-primary">Save</button>
						<button id="cancelBtn" type="reset" name="cancelBtn" class="btn btn-inverse">Cancel</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>