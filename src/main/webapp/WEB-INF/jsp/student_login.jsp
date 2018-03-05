<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/css/regis_form.css">
<title>Student Login</title>
<style type="text/css">
	.form-control {
	    color: #fff !important;
	}
</style>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id" content="273733011923-n5lqfqjb265s8s8k5mka9drdkr2t78e8.apps.googleusercontent.com">
</head>
<body class="main">
<div class="login-screen">
		<div class="container">
    		<div class="col-xs-12 col-sm-8 col-md-5 col-sm-offset-2 col-md-offset-6">
    			<form role="form"  name="login-form" action="" method="post" class="login-form">
					<h3><b>Student Login Form</b></h3>
					<div class="form-group">
                        <input type="text" name="user_name" id="user_name" class="form-control blur" placeholder="User Name" tabindex="1" required maxlength="10">
					</div>
					<div class="form-group">
						<input type="password" name="password" id="password" class="form-control blur" placeholder="Password" tabindex="2" required maxlength="20">
					</div>
					<h6 style="color: red" id="errors">${errors}</h6>
					<button type="submit" class="btn btn-reg">Login</button>
					<p style="margin-top: 5%;"><a href="#" class="forgot login-link">Forgotten your username or password?</a></p>
                    
					<div class="g-signin2" data-onsuccess="onSignIn" id="myP"></div>
					<img id="myImg">
					<br>
					<p id="name"></p>
					<div id="status"></div>
					<script type="text/javascript">
				      function onSignIn(googleUser) {
					      // window.location.href='success.jsp';
					      var profile = googleUser.getBasicProfile();
					      var imagurl=profile.getImageUrl();
					      var name=profile.getName();
					      var email=profile.getEmail();
					      document.getElementById("myImg").src = imagurl;
					      document.getElementById("name").innerHTML = name;
					      document.getElementById("myP").style.visibility = "hidden";
					   }
				   </script>
				   <p style="margin-top: 5%;"><strong>You haven't already account?</strong><br><a href="/student/registration" id="flip-btn" class="signup signup_link login-link">Sign up for a new account</a></p>
			   </form>
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