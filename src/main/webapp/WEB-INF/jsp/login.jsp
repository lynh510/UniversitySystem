<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/css/form_login.css" rel="stylesheet">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="/javascript/login.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id"
	content="273733011923-n5lqfqjb265s8s8k5mka9drdkr2t78e8.apps.googleusercontent.com">
<title>Login - Register</title>
</head>
<body>
	<div class="container">
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
								<a href="#" class="active" id="login-form-link">Login</a>
							</div>
							<div class="col-xs-6">
								<a href="#" id="register-form-link">Register</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" action="https://phpoll.com/login/process" method="post" role="form" style="display: block;">
									<div class="form-group">
										<label for="username">Username</label>
										<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="" required maxlength="10">
									</div>
									<div class="form-group">
										<label for="password">Password</label>
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" required maxlength="20">
									</div>
									<div class="form-group">
										<div class="g-signin2" data-onsuccess="onSignIn" id="myP"></div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
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
								<form id="register-form" action="https://phpoll.com/register/process" method="post" role="form" style="display: none;">
									<div class="form-group">
										<label class="required" for="form-username">Username</label>
										<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="" required maxlength="10">
									</div>
									<div class="form-group">
										<label class="required" for="form-email">Email</label>
										<input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="" required  onkeypress="validateForm()">
										<h6 style="color: red" id="message"></h6>
									</div>
									<div class="form-group">
										<label class="required" for="form-password">Password</label>
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" required maxlength="20">
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
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>