<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit account</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/css/regis_form.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/css/stu_submit_idea.css">
<style type="text/css">
.form-control {
	border: 1px solid black;
}

.navbar>.container {
	margin-top: 0 !important;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#edit_account").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/account/perform_edit_account",
				enctype : 'multipart/form-data',
				processData : false,
				contentType : false,
				cache : false,
				data : new FormData($("#edit_account")[0]),
				success : function(response) {
					$('#myModal h4').text('Message');
					$('#myModal #message').css('color', 'green');
					$('#myModal #message').text('Edit account successfully');
					$('#myModal').modal('show');
				},
				error : function(xhr, response, error) {
					var err = JSON.parse(xhr.responseText);
					$('#myModal h4').text('Message');
					$('#myModal #message').css('color', 'red');
					$('#myModal #message').text(err.message);
					$('#myModal').modal('show');
				}
			});
			return false;
		});
	});
</script>
</head>

<body>
	<div class="screen">
		<div class="navbar navbar-default navbar-fixed-top">
			<header id="header-site">
				<!--Begin::Header right-->
				<jsp:include page="${navbar}"></jsp:include>
				<!--End::Header Right-->


				<!--Begin::Nav-->
				<!-- <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-left">
				<li><a id="home" href="#">Home</a></li>
				<li><a id="about" href="#">About</a></li>
				<li><a id="" href="#">Service</a></li>
				<li><a id="" href="#">Contact</a></li>
			</ul>
		</div> -->
				<!--End::Nav-->

			</header>
		</div>
		<!-- CONTENT -->
		<div class="container" style="float: initial;">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<div class="panel-group" id="accordion">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseOne"><span class="glyphicon glyphicon-file">
									</span>Edit account</a>
								</h4>
							</div>
							<div id="collapseOne" class="panel-collapse collapse in">
								<form role="form" id="edit_account" action="" method="post"
									enctype="multipart/form-data">
									<input type="text" hidden="true" value="${welcome.id}"
										name="person_id" />
									<div class="panel-body">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-6">
												<div class="form-group">
													<label class="required" for="form-firstname">Full
														Name</label> <input type="text" name="full_name" id="full_name"
														class="form-control blur" placeholder="Full Name"
														value="${welcome.person_name}" tabindex="1" required
														maxlength="20">
												</div>
											</div>
										</div>
										<div class="form-group">
											<div>
												<label class="labeltxt required">Date of birth</label>
											</div>
											<div class="date-col">
												<select class="form-control blur" required name="day">
													<option value="${day_of_user}" selected="">${day_of_user}</option>
													<c:forEach items="${days}" var="day">
														<option value="${day}">${day}</option>
													</c:forEach>
												</select>
											</div>
											<div class="date-col">
												<select name="month" required
													class="form-control datebox blur">
													<option value="${month_of_user}" selected="">${month_of_user}</option>
													<c:forEach items="${months}" var="month">
														<option value="${month}">${month}</option>
													</c:forEach>
												</select>
											</div>
											<div class="date-col">
												<select name="year" value="" required
													class="form-control datebox blur">
													<option value="${year_of_user}" selected="">${year_of_user}</option>
													<c:forEach items="${years}" var="year">
														<option value="${year}">${year}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<div style="margin-top: 8%;">
												<label class="labeltxt required">Gender</label><br />
												<div>
													<input type="radio" name="gender" class="gender" id="male"
														tabindex="2" placeholder="Gender" value="1" title="Male">
													Male <input type="radio" name="gender" class="gender"
														id="female" tabindex="2" placeholder="Gender" value="2"
														title="Female"> Female <input type="radio"
														name="gender" class="gender" id="unknown" tabindex="2"
														placeholder="Gender" value="3" title="Unknown">
													Unknown
												</div>
											</div>
										</div>
										<script type="text/javascript">
											var gender = '${welcome.gender}';
											if (gender == 1) {
												document.getElementById("male").checked = true;
											}
											if (gender == 2) {
												document
														.getElementById("female").checked = true;
											}
											if (gender == 3) {
												document
														.getElementById("unknown").checked = true;
											}
										</script>
										<div class="form-group">
											<div style="margin-top: 2%;">
												<label class="labeltxt">Profile picture (Optional)</label>
											</div>
											<input type="file" class="form-control blur" accept="image/*"
												name="profilepic" onchange="showMyImage(this)">
											<div class="profile-pic">
												<img id="thumbnail" src="${welcome.person_picture}"
													height="100px" width="100px" />
											</div>
											<script>
												function showMyImage(fileInput) {
													var files = fileInput.files;
													for (var i = 0; i < files.length; i++) {
														var file = files[i];
														var imageType = /image.*/;
														if (!file.type
																.match(imageType)) {
															continue;
														}
														var img = document
																.getElementById("thumbnail");
														img.file = file;
														var reader = new FileReader();
														reader.onload = (function(
																aImg) {
															return function(e) {
																aImg.src = e.target.result;
															};
														})(img);
														reader
																.readAsDataURL(file);
													}
												}
											</script>
										</div>
										<div class="form-group">
											<label class="required" for="form-phone">Phone number</label>
											<input value="${welcome.phone}" type="text" name="phone"
												required placeholder="Phone number..."
												class="form-control blur" id="form-phone" maxlength="12"
												onkeypress="phonenumber(this)">
											<script type="text/javascript">
												function phonenumber(inputtxt) {
													var phoneno = /^[0-9]+/;
													if (!inputtxt.value
															.match(phoneno)) {
														document
																.getElementById('msgPhone').innerHTML = "Phone number is invalid!";
														return false;
													}
													if (inputtxt.value == "") {
														document
																.getElementById('msgPhone').innerHTML = "";
														return false;
													}
													if (inputtxt.value.length < 10) {
														document
																.getElementById('msgPhone').innerHTML = "Phone number must be at least 10 character!";
														return false;
													} else {
														document
																.getElementById('msgPhone').innerHTML = "";
														return true;
													}

												}
											</script>
											<h6 style="color: red; margin-left: 5%;" id="msgPhone"></h6>
										</div>
										<div class="form-group">
											<label class="required" for="form-address">Address</label> <input
												value="${welcome.address}" type="text" name="address"
												required placeholder="Address..." class="form-control blur"
												id="form-address" maxlength="200">
										</div>
										<button type="submit" class="btn btn-reg">Update</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<p id="message"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
</body>
</html>