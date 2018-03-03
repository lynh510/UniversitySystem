<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/regis_form.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
	.form-control {
	    color: #fff !important;
	}
</style>
<script type="text/javascript">
	//VALIDATE EMAIL
	function validateForm() {
	    var x = document.forms["registration-form"]["email"].value;
	    var atpos = x.indexOf("@");
	    var dotpos = x.lastIndexOf(".");
	    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
	       document.getElementById("message").innerHTML ="Not a valid e-mail address" ;
	       return false;
	    }else{
	    	document.getElementById("message").innerHTML ="" ;
	    }
	    
	}
	//CONFIRM PASSWORD
	var check = function() {
		if (document.getElementById('password').value ==
			document.getElementById('password_confirmation').value) {
			document.getElementById('msgConfirm').innerHTML = "";
		} else {
			document.getElementById('msgConfirm').innerHTML = "Password doesn't match!";
		}
	}
	//VALIDATE PHONE NUMBER
	function phonenumber(inputtxt) {
		var phoneno = /^[0-9]+/;
		if(!inputtxt.value.match(phoneno)) {
			document.getElementById('msgPhone').innerHTML = "Phone number is invalid!";
			return false;
		}
		if(inputtxt.value == "") {
			document.getElementById('msgPhone').innerHTML = "";
			return false;
		}
		if(inputtxt.value.length < 10) {
			document.getElementById('msgPhone').innerHTML = "Phone number must be at least 10 character!";
			return false;
		}
		else {
			document.getElementById('msgPhone').innerHTML = "";
		    return true;
		}
		
	}
	
</script>
<title>Student Registration</title>
</head>
<body class="main">
<div class="login-screen">
		<div class="container">
    		<div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-5">
    			<form role="form"  name="registration-form" action="" method="post" class="registration-form">
					<h3><b>Student Registration Form</b></h3>
					<p>Fill in the form below to insert student details</p>
    				<hr>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group">
		                        <input type="text" name="first_name" id="first_name" class="form-control blur" placeholder="First Name" tabindex="1" required maxlength="20">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group">
								<input type="text" name="last_name" id="last_name" class="form-control blur" placeholder="Last Name" tabindex="2" required maxlength="20">
							</div>
						</div>
					</div>
					<div class="form-group">
                        <input type="text" name="user_name" id="user_name" class="form-control blur" placeholder="User Name" tabindex="3" required maxlength="10">
					</div>
					<div class="form-group">
						<label class="sr-only" for="form-email">Email</label> 
						<input type="text" name="email" id="email" onkeypress="validateForm()" required placeholder="Email Address" tabindex="4" required class="form-email form-control blur" id="form-email">
						<h6 style="color: red" id="message"></h6>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group">
								<input type="password" name="password" id="password" class="form-control blur" placeholder="Password" tabindex="5" required  maxlength="20">
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group">
								<input type="password" name="password_confirmation" id="password_confirmation" onkeyup='check();' class="form-control blur" required placeholder="Confirm Password" tabindex="6"  maxlength="20">
							</div>
						</div>
						<h6 style="color: red; margin-left: 5%;" id="msgConfirm"></h6>
						
					</div>
					<div class="form-group">
						<div>
							<label class="labeltxt">Date of birth</label>
						</div>
						<div class="date-col">
							<select class="form-control blur" required name="day">
								<option value="" selected="" disabled="disabled" hidden>Select a day</option>
								<c:forEach items="${days}" var="day">
									<option value="${day}">${day}</option>
								</c:forEach>
							</select>
						</div>
						<div class="date-col">
							<select name="month" required class="form-control datebox blur">
								<option value="" selected="" disabled="disabled" hidden>Select a month</option>
								<c:forEach items="${months}" var="month">
									<option value="${month}">${month}</option>
								</c:forEach>
							</select>
						</div>
						<div class="date-col">
							<select name="year" value="" required class="form-control datebox blur">
								<option selected="" disabled="disabled" hidden>Select a year</option>
								<c:forEach items="${years}" var="year">
									<option value="${year}">${year}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<div style="margin-top: 8%;">
								<label class="labeltxt">Gender</label><br/>
							<div>
	                            <input type="radio" name="gender" class="gender" id="male" tabindex="2" placeholder="Gender" value="1" title="Male"> Male
	                            <input type="radio" name="gender" class="gender" id="female" tabindex="2" placeholder="Gender" value="2" title="Female"> Female
	                            <input type="radio" name="gender" class="gender" id="unknown" tabindex="2" placeholder="Gender" value="3" title="Unknown"> Unknown
	                        </div>
						</div>
						<div class="form-group">
							<div style="margin-top: 2%;">
								<label class="labeltxt">Profile picture (Optional)</label> 
							</div>
							<input type="file" class="form-control blur" accept="image/*" name="profilepic" onchange="showMyImage(this)">
							<div class="profile-pic">
								<img id="thumbnail" height="100%" width="100%" />
							</div>
							<script>
								function showMyImage(fileInput) {
									var files = fileInput.files;
									for (var i = 0; i < files.length; i++) {
										var file = files[i];
										var imageType = /image.*/;
										if (!file.type.match(imageType)) {
											continue;
										}
										var img = document.getElementById("thumbnail");
										img.file = file;
										var reader = new FileReader();
										reader.onload = (function(aImg) {
											return function(e) {
												aImg.src = e.target.result;
											};
										})(img);
										reader.readAsDataURL(file);
									}
								}
							</script>
						</div>
						<div class="form-group">
							<label class="sr-only" for="form-phone">Phone number</label> 
							<input type="text" name="phone" required placeholder="Phone number..."
								class="form-control blur" id="form-phone"  maxlength="12" onkeypress="phonenumber(this)">
							<h6 style="color: red; margin-left: 5%;" id="msgPhone"></h6>
						</div>
						<div class="form-group">
							<label class="sr-only" for="form-address">Address</label> <input
								type="text" name="address" required placeholder="Address..."
								class="form-control blur" id="form-address"  maxlength="200">
						</div>
						
						<button type="submit" class="btn btn-reg">Sign up</button>
						
                        <h5 style="margin-top: 5%;">You have already account. <a href="/student/login" id="unflip-btn" class="login-link">Log in now!</a></h5>
    			</form>
    		</div>
   		</div>
</div>
</body>
</html>