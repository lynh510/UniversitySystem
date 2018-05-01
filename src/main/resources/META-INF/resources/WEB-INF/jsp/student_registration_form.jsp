<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/css/regis_form.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
.form-control {
	color: #fff !important;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#registration_form").submit(function(e) {
		e.preventDefault();
		$.ajax({
			type : "Post",
			url : "/admin/registration",
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			data : new FormData($("#registration_form")[0]),
			success : function(response) {
				$('#myModal h4').text('Message');
				$('#myModal #message').css('color', 'green');
				$('#myModal #message').text('Registration successfully');
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
	//VALIDATE EMAIL
	function validateForm() {
	    var x = document.forms["registration-form"]["email"].value;
	    var atpos = x.indexOf("@");
	    var dotpos = x.lastIndexOf(".");
	    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 1 >= x.length) {
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
			return true;
		} 
		if (document.getElementById('password').value == "") {
			document.getElementById('msgConfirm').innerHTML = "";
			return false;
		}
		else {
			document.getElementById('msgConfirm').innerHTML = "Password doesn't match!";
			return false;
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
<title>Registration</title>
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
			<div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-5">
				<form role="form" id="registration_form" name="registration-form"
					action="" method="post" enctype="multipart/form-data"
					class="registration-form">
					<h3>
						<b>Registration Form</b>
					</h3>
					<p>Fill in the form below to insert user details</p>
					<hr>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6">
							<div class="form-group">
								<label class="required" for="form-firstname">First Name</label>
								<input type="text" name="first_name" id="first_name"
									class="form-control blur" placeholder="First Name" tabindex="1"
									required maxlength="20">
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-6">
							<div class="form-group">
								<label class="required" for="form-lastname">Last Name</label> <input
									type="text" name="last_name" id="last_name"
									class="form-control blur" placeholder="Last Name" tabindex="2"
									required maxlength="20">
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="required" for="form-username">Username</label> <input
							type="text" name="user_name" id="user_name"
							class="form-control blur" placeholder="User Name" tabindex="3"
							required maxlength="10">
					</div>
					<div class="form-group">
						<label class="required" for="form-email">Email</label> <input
							type="text" name="email" id="email" onkeypress="validateForm()"
							required placeholder="Email Address" tabindex="4" required
							class="form-email form-control blur" id="form-email">
						<!-- <h6 style="color: red" id="message"></h6> -->
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6">
							<div class="form-group">
								<label class="required" for="form-password">Password</label> <input
									type="password" name="password" id="password"
									class="form-control blur" placeholder="Password" tabindex="5"
									required maxlength="20">
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-6">
							<div class="form-group">
								<label class="required" for="form-cfpass">Confirm
									Password</label> <input type="password" name="password_confirmation"
									id="password_confirmation" onkeyup='check();'
									class="form-control blur" required
									placeholder="Confirm Password" tabindex="6" maxlength="20">
							</div>
						</div>
						<h3 style="color: red; margin-left: 5%;" id="msgConfirm"></h3>

					</div>
					<div class="form-group">
						<div>
							<label class="labeltxt required">Date of birth</label>
						</div>
						<div class="date-col">
							<select class="form-control blur" required name="day">
								<option value="" selected="" disabled="disabled" hidden>--
									Day --</option>
								<c:forEach items="${days}" var="day">
									<option value="${day}">${day}</option>
								</c:forEach>
							</select>
						</div>
						<div class="date-col">
							<select name="month" required class="form-control datebox blur">
								<option value="" selected="" disabled="disabled" hidden>--
									Month --</option>
								<c:forEach items="${months}" var="month">
									<option value="${month}">${month}</option>
								</c:forEach>
							</select>
						</div>
						<div class="date-col">
							<select name="year" value="" required
								class="form-control datebox blur">
								<option selected="" disabled="disabled" hidden>-- Year
									--</option>
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
									placeholder="Gender" value="3" title="Unknown"> Unknown
							</div>
						</div>
					</div>
					<div class="form-group">
						<div style="margin-top: 2%;">
							<label class="labeltxt">Profile picture (Optional)</label>
						</div>
						<input type="file" class="form-control blur" accept="image/*"
							name="profilepic" onchange="showMyImage(this)">
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
						<label class="required" for="form-phone">Phone number</label> <input
							type="text" name="phone" required placeholder="Phone number..."
							class="form-control blur" id="form-phone" maxlength="12"
							onkeypress="phonenumber(this)">
						<h6 style="color: red; margin-left: 5%;" id="msgPhone"></h6>
					</div>
					<div class="form-group">
						<label class="required" for="form-address">Address</label> <input
							type="text" name="address" required placeholder="Address..."
							class="form-control blur" id="form-address" maxlength="200">
					</div>
					<div class="form-group">
						<div>
							<label class="labeltxt required">Role</label>
						</div>
						<div class="role">
							<select class="form-control blur" required name="userrole">
								<option value="" selected="" disabled="disabled" hidden>--
									Role --</option>
								<option value="1">Staff</option>
								<option value="0">Student</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div>
							<label class="labeltxt required">Departments</label>
						</div>
						<div class="role">
							<select class="form-control blur" required name="deparment">
								<option value="" selected="" disabled="disabled" hidden>--
									Department --</option>
									<c:forEach items="${deparments}" var="deparment">
								<option value="${deparment.id}">${deparment.dept_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label> By clicking <span class="label label-primary"
							style="font-size: 15px; background-color: teal;">Sign up</span>,
							you agree to the <a href="/student/terms">Terms and
								Conditions</a> set out by this site
						</label>
					</div>
					<%-- <h6 style="color: red" id="errors">${errors}</h6> --%>

					<button type="submit" class="btn btn-reg">Sign up</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>