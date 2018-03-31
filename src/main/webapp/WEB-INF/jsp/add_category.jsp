<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<title>Add new category</title>
<style type="text/css">
	.container {
		margin-top: 5%;
	}
	.form-group {
		margin-top: 3%;
	}
</style>
<script type="text/javascript">
var check = function() {
	var title = document.getElementById('title');
	var splChars = "*|,\":<>[]{}`\';()@&$#%";
	for (var i = 0; i < title.value.length; i++) {
		if (splChars.indexOf(title.value.charAt(i)) != -1) {
			document.getElementById('msgCheck').innerHTML = "The category name mustn't have special character!";
			title.focus();
			return false;
		}
		if (splChars.indexOf(title.value.charAt(i)) == -1) {
			document.getElementById('msgCheck').innerHTML = "";
			title.focus();
			return true;
		}
	}
}
</script>
</head>
<body>
	<div class="container">
    	<div class="row">
			<form class="form-horizontal">
			        <!-- Form Name -->
			        <h3>Add new category</h3>
					<hr/>
			        <!-- Text input-->
			        <div class="form-group">
			            <label class="col-md-4 control-label" for="title">Category Name</label>
			
			            <div class="col-md-5">
			                <input id="title" name="title" type="text" placeholder="Category name" class="form-control input-md" required onkeyup='check();' >
			                <h6 style="color: red; margin-left: 5%;" id="msgCheck"></h6>
			            </div>
			        </div>
			
			        <!-- Button (Double) -->
			        <div class="form-group">
			            <label class="col-md-4 control-label" for="saveBtn"></label>
			
			            <div class="col-md-8">
			                <button id="saveBtn" name="saveBtn" class="btn btn-primary">Save</button>
			                <button id="cancelBtn" name="cancelBtn" class="btn btn-inverse">Cancel</button>
			            </div>
			        </div>
			</form>
		</div>
	</div>
</body>
</html>