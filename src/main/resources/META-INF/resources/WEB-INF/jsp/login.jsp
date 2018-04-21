<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/css/form_login.css" rel="stylesheet">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="/javascript/login.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id"
	content="273733011923-n5lqfqjb265s8s8k5mka9drdkr2t78e8.apps.googleusercontent.com">
<title>Login - Register</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#login-form").submit(function(e) {
			e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/${role}/authorization",
				data : $("#login-form").serialize(),
				success : function(response) {
					window.location.href = response.message;
				},
				error : function(xhr, response, error) {
					$('#myModal h4').text('Message');
					var err = JSON.parse(xhr.responseText);
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
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-12">
								<a href="#" class="active" id="login-form-link">${displayName}
									Login</a>
							</div>
							<!-- <div class="col-xs-6">
								<a href="#" id="register-form-link">Register</a>
							</div> -->
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" action="/${role}/authorization"
									method="post" role="form" style="display: block;">
									<div class="form-group">
										<label for="username">Username</label> <input type="text"
											name="username" id="username" tabindex="1"
											class="form-control" placeholder="Username" value="" required
											maxlength="10">
									</div>
									<div class="form-group">
										<label for="password">Password</label> <input type="password"
											name="password" id="password" tabindex="2"
											class="form-control" placeholder="Password" required
											maxlength="20">
									</div>
									<h5 style="color: red" id="errors"></h5>
									<div class="form-group">
										<div class="g-signin2" data-onsuccess="onSignIn" id="myP">
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit"
													tabindex="4" class="form-control btn btn-login"
													value="Log In">
											</div>
										</div>
									</div>

									<!-- <div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<a href="https://phpoll.com/recover" tabindex="5" class="forgot-password">Forgot Password?</a>
												</div>
											</div>
										</div>
									</div> -->

								</form>
								<form id="external_login" action="/${role}/external_login"
									method="post">
									<input type="hidden" id="email" name="email" value="">
								</form>
								<script type="text/javascript">
									function onSignIn(googleUser) {
										var profile = googleUser
												.getBasicProfile();
										var imagurl = profile.getImageUrl();
										var name = profile.getName();
										var email = profile.getEmail();
										document.getElementById("email").value = email;
										$.ajax({
													type : "Post",
													url : "/${role}/external_login",
													data : $("#external_login")
															.serialize(),
													success : function(response) {
														window.location.href = response.message;
													},
													error : function(xhr,
															response, error) {
														var err = JSON
																.parse(xhr.responseText);
														document
																.getElementById("errors").innerHTML = err.message;
													}
												});
										gapi.auth2.getAuthInstance()
												.disconnect();

									}
								</script>
								<!-- <form id="register-form" action="https://phpoll.com/register/process" method="post" role="form" style="display: none;">
									<div class="form-group">
										<label class="required" for="form-username">Username</label>
										<input type="text" name="username" id="regis_username" tabindex="1" class="form-control" placeholder="Username" value="" required maxlength="10">
									</div>
									<div class="form-group">
										<label class="required" for="form-email">Email</label>
										<input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="" required  onkeypress="validateForm()">
										<h6 style="color: red" id="message"></h6>
									</div>
									<div class="form-group">
										<label class="required" for="form-password">Password</label>
										<input type="password" name="password" id="regis_password" tabindex="2" class="form-control" placeholder="Password" required maxlength="20">
									</div>
									<div class="form-group">
										<label class="required" for="form-cfpass">Confirm Password</label>
										<input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password" onkeyup='check();' required maxlength="20">
									</div>
									<h6 style="color: red; margin-left: 5%;" id="msgConfirm"></h6>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
											</div>
										</div>
									</div>
								</form> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>