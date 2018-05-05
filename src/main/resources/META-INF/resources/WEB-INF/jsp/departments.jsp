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
<link type="text/css" rel="stylesheet" href="/css/jquery-te-1.4.0.css">

<script type="text/javascript" src="/javascript/jquery-te-1.4.0.min.js"
	charset="utf-8"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<title>Idea Management</title>
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
	$(document).ready(function() {
		$("#acceptform").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/qacoordinator/approve",
				data : $("#acceptform").serialize(),
				success : function(response) {
					alert(response.message);
					jQuery('#ideas' ).load(' #ideas');
					$('#acceptModal').modal('hide');							
				},
				error : function(xhr, response, error) {

				}
			});
			return false;
		});
	});
	function onAccept(idea_id){
		document.getElementById("idea_id").value = idea_id;
		document.getElementById("message").innerHTML = "Are you sure want to approve ?";
		document.getElementById("action").value = 1;
	};
	function onDenied(idea_id){
		document.getElementById("idea_id").value = idea_id;
		document.getElementById("message").innerHTML = "Are you sure want to denied ?";
		document.getElementById("action").value = 2;
	}
</script>
</head>
<body>
	<jsp:include page="qacoordinator_navbar.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="list-group product-bread-a">
					<p href="#" class="list-group-item product-bread-a"
						style="background: #dededd">New Ideas</p>
				</div>
				<div id="ideas" style="overflow-x: auto;">
					<table class="table table-striped">
						<thead>
							<tr>
								<th></th>
							</tr>
						</thead>
						<c:forEach items="${ideas}" var="idea">
							<tbody>
								<tr>
									<td><strong>Student</strong><br />${idea.person.person_name}</td>
									<td><strong>Title</strong><br />${idea.title}</td>
									<td><strong>Content</strong><br />...</td>
									<td><strong>Post time</strong><br />${idea.post_date}</td>
									<td><strong>Review</strong><br /> <a
										href="/idea/${helper.encryptID(idea.id)}">View</a></td>
									<td><strong>Accept</strong><br /> <a data-toggle="modal"
										data-target="#acceptModal" onclick="onAccept(${idea.id})">Accept</a></td>
									<td><strong>Denied</strong><br /> <a data-toggle="modal"
										data-target="#acceptModal" onclick="onDenied(${idea.id})">Denied</a></td>
								</tr>
							</tbody>
						</c:forEach>

					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="acceptModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Confirmation</h4>
				</div>
				<div class="modal-body">
					<p id="message">Are you sure?</p>
					<form id="acceptform" action="" method="post">
						<textarea name="content" required="required" class="jqte-test">Write reasons below</textarea>
						<input type="hidden" id="idea_id" name="idea_id" value="" /> <input
							type="hidden" id="action" name="action" value=""> <input
							type="submit" class="btn" value="Yes" />
						<script>
				$('.jqte-test').jqte();
			</script>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
</body>
</html>