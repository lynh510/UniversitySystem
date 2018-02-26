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
<title>Student Registration</title>
</head>
<body>
	<div class="form-box">
		<div class="form-top">
			<div class="form-top-left">
				<h3>Student Registration Form</h3>
				<p>Fill in the form below to insert student details</p>
			</div>
			<div class="form-top-right">
				<i class="fa fa-pencil"></i>
			</div>
		</div>
		<div class="form-bottom">
			<form role="form" name="registration-form" action="" method="post"
				class="registration-form">
				<div class="form-group">
					<label class="sr-only" for="form-first-name">First name</label> <input
						type="text" name="first-name" required
						placeholder="First name..." class="form-first-name form-control"
						id="form-first-name">
				</div>
				<div class="form-group">
					<label class="sr-only" for="form-last-name">Last name</label> <input
						type="text" name="last-name" required
						placeholder="Last name..." class="form-last-name form-control"
						id="form-last-name">

				</div>
				<div class="form-group">
					<label class="sr-only" for="form-email">Email</label> <input
						type="text" name="email" id="email"
						onkeypress="validateForm()" required placeholder="Email..."
						class="form-email form-control" id="form-email">
					<h6 style="color: red" id="message"></h6>
					<script type="text/javascript">
					    function validateForm() {
					        var x = document.forms["registration-form"]["email"].value;
					        var atpos = x.indexOf("@");
					        var dotpos = x.lastIndexOf(".");
					        if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
					            //alert("Not a valid e-mail address");
					           document.getElementById("message").innerHTML ="Not a valid e-mail address" ;
					           return false;
					        }else{
					        	document.getElementById("message").innerHTML ="" ;
					        }
					        
					    }</script>
				</div>
				<div class="form-group">
					<div>
						<label>Date of birth</label>
					</div>
					<div class="date-col">
						<select class="form-control" required name="day">
							<option value="" selected="" disabled="disabled" hidden>Select a
								day</option>
							<c:forEach items="${days}" var="day">
								<option value="${day}">${day}</option>
							</c:forEach>
						</select>
					</div>
					<div class="date-col">
						<select name="month" required class="form-control">
							<option value="" selected="" disabled="disabled" hidden>Select a
								month</option>
							<c:forEach items="${months}" var="month">
								<option value="${month}">${month}</option>
							</c:forEach>
						</select>
					</div>
					<div class="date-col">
						<select name="year" value="" required class="form-control">
							<option selected="" disabled="disabled" hidden>Select a
								year</option>
							<c:forEach items="${years}" var="year">
								<option value="${year}">${year}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label>Gender</label> <select name="gender" required=""
						class="form-control">
						<option value="" selected="" disabled="disabled" hidden>Select a
							gender</option>
						<option value="1">Male</option>
						<option value="2">Female</option>
						<option value="3">Unknown</option>
					</select>
				</div>
				<div class="form-group">
					<label>Profile picture (Optional)</label> <input type="file"
						class="form-control" accept="image/*" name="profilepic"
						onchange="showMyImage(this)">
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
					<label class="sr-only" for="form-phone">Phone number</label> <input
						type="text" name="phone" required placeholder="Phone number..."
						class="form-control" id="form-phone">
				</div>
				<div class="form-group">
					<label class="sr-only" for="form-address">Address</label> <input
						type="text" name="address" required placeholder="Address..."
						class="form-control" id="form-address">
				</div>
				<div class="form-group">
					<label class="sr-only">About</label>
					<textarea name="about" placeholder="About him/herself..."
						class="form-about-yourself form-control" id="form-about-yourself"
						style="z-index: auto; position: relative; line-height: 30px; font-size: 16px; transition: none; background: transparent !important;"></textarea>
				</div>
				<button type="submit" class="btn" style="width: 100%">Save!</button>
			</form>
		</div>
	</div>
</body>
</html>