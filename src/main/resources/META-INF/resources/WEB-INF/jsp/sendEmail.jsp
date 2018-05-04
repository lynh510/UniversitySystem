<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<script
	src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Send email to student</title>

<link type="text/css" rel="stylesheet" href="/css/jquery-te-1.4.0.css">

<script type="text/javascript"
	src="http://code.jquery.com/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/javascript/jquery-te-1.4.0.min.js"
	charset="utf-8"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#send_mail_form").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/qacoordinator/send_email",
				data : $("#send_mail_form").serialize(),
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
	<jsp:include page="qacoordinator_navbar.jsp"></jsp:include>
	<form id="send_mail_form" action="/qacoordinator/send_email"
		method="post" class="form-horizontal">
		<div class="container">
			<table class="table table-responsive table-hover table-bordered ">
				<thead>
					<tr class="info">
						<th></th>
						<th>Profile Picture</th>
						<th>Status</th>
						<th>E-Mail</th>
						<th>Name</th>
						<th>Enroll Date</th>
						<th>Gender</th>
						<th>Birth date</th>
						<th>Address</th>
						<th>Phone number</th>
						<th>Department</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${students}" var="p">
						<tr>
							<td><img alt="${p.person_name}" width="50px" height="50px"
								src="${p.person_picture}"></td>
							<td><input type="radio" name="emails" value="${p.email}" /></td>
							<c:choose>
								<c:when test="${p.status == 0}">
									<td class="success">Active</td>
								</c:when>
								<c:when test="${p.status == 1}">
									<td class="default">Inactive</td>
								</c:when>
								<c:when test="${p.status == 2}">
									<td class="error">Deleted</td>
								</c:when>
							</c:choose>
							<td><a href="#">${p.email}</a></td>
							<td>${p.person_name}</td>
							<td>${p.enroll_date}</td>
							<td><c:choose>
									<c:when test="${p.gender == 1}">Male</c:when>
									<c:otherwise>Female</c:otherwise>
								</c:choose></td>
							<td>${p.birthdate}</td>
							<td>${p.address }</td>
							<td>${p.phone}</td>
							<td>${p.department.dept_name}
								(${p.department.academic_year.year} -
								${p.department.academic_year.season})</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
			<div class="form-group">
				<label class="col-md-4 control-label" for="title">Title</label>
				<div class="col-md-5">
					<input type="text" required="" name="title">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label" for="title">Content</label>
				<div class="col-md-5">
					<textarea name="content" required="required" class="jqte-test">Write something</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label" for="saveBtn"></label> <input
					type="hidden" name="id" value="${helper.encryptID(user.id)}">
				<div class="col-md-8">
					<button id="saveBtn" type="submit" name="saveBtn"
						class="btn btn-primary">Send</button>
					<button id="cancelBtn" type="reset" name="cancelBtn"
						class="btn btn-inverse">Cancel</button>
				</div>
			</div>
			<script>
				$('.jqte-test').jqte();
			</script>
		</div>
	</form>
</body>
</html>