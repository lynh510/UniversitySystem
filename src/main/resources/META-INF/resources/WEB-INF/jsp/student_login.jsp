<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/css/regis_form.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Student Login</title>
<style type="text/css">
.form-control {
	color: #fff !important;
}
</style>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id"
	content="273733011923-n5lqfqjb265s8s8k5mka9drdkr2t78e8.apps.googleusercontent.com">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	 $("#login_form").submit(function(e){
		 e.preventDefault();
			$.ajax({
				type : "Post",
				url : "/student/login",
				data : $("#login_form").serialize(),
				success : function(response) {
					window.location.href = "/idea/page/1";
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
<body class="main">
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
	<div class="login-screen">
		<div class="container">
			<div
				class="col-xs-12 col-sm-12 col-md-5 col-md-offset-6">
				<form role="form" name="login-form" id="login_form"
					action="/student/login" method="post" class="login-form">
					<h2>
						<b>Student Login Form</b>
					</h2>
					<div class="form-group">
						<label class="required" for="form-username">Username</label> <input
							type="text" name="user_name" id="user_name"
							class="form-control blur" placeholder="User Name" tabindex="1"
							required maxlength="10">
					</div>
					<div class="form-group">
						<label class="required" for="form-pasword">Password</label> <input
							type="password" name="password" id="password"
							class="form-control blur" placeholder="Password" tabindex="2"
							required maxlength="20">
					</div>
					<h6 style="color: red" id="errors"></h6>
					<button type="submit" class="btn btn-reg">Login</button>
					<p style="margin-top: 5%;">
						<a href="#" class="forgot login-link">Forgotten your username
							or password?</a>
					</p>

					<div class="g-signin2" data-onsuccess="onSignIn" id="myP"></div>

					<p style="margin-top: 5%;">
					</p>
				</form>
				<form id="external_login" action="/student/external_login"
					method="post">
					<input type="hidden" id="email" name="email" value="">
				</form>
				<script type="text/javascript">
					function onSignIn(googleUser) {
						var profile = googleUser.getBasicProfile();
						var imagurl = profile.getImageUrl();
						var name = profile.getName();
						var email = profile.getEmail();
						document.getElementById("email").value = email;
						//document.getElementById("external_login").submit();
						$
								.ajax({
									type : "Post",
									url : "/student/external_login",
									data : $("#external_login").serialize(),
									success : function(response) {
										window.location.href = "/student/submit_idea";
									},
									error : function(xhr, response, error) {
										var err = JSON.parse(xhr.responseText);
										document.getElementById("errors").innerHTML = err.message;
									}
								});
						gapi.auth2.getAuthInstance().disconnect();

					}
				</script>
			</div>
		</div>
	</div>
	<!-- <button onclick="myFunction()">Sign Out</button>
	<script>
      function myFunction() {
      gapi.auth2.getAuthInstance().disconnect();
      location.reload();
   }
   </script> -->
</body>
</html>