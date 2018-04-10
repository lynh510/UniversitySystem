<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1"
	crossorigin="anonymous">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet prefetch" href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css">
<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>

<title>Department Manager</title>
<style type="text/css">
.container {
	margin-top: 10%;
}

/* Dates */
.agenda .agenda-date { width: 170px; }
.agenda .agenda-date .dayofmonth {
  width: 40px;
  font-size: 36px;
  line-height: 36px;
  float: left;
  text-align: right;
  margin-right: 10px; 
}
.agenda .agenda-date .shortdate {
  font-size: 0.75em; 
}
.navbar-nav.navbar-right:last-child {
		margin-right: 3%;
	}

/* Times */
.agenda .agenda-time { width: 140px; } 
.product-bread-a{
    -webkit-border-radius: 0px !important;
    -moz-border-radius: 0px !important;
    border-radius: 0px !important;
    
}
.product-bread-a a{
    color:#718ac3;
}
table {
	margin-left: 2%;
	width: 95% !important;
	overflow-x: auto;
}
</style>
</head>
<body>
<div id="navbar" class="navbar navbar-default navbar-fixed-top navbar-inverse">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#navbar-ex-collapse">
			<span class="sr-only">Toggle navigation</span> <span
				class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
	</div>
	<div class="collapse navbar-collapse" id="navbar-ex-collapse">
		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" aria-expanded="true"> <img alt=""
					class="img-circle" id="userpicture"
					src="${welcom.person_picture}" width="30" height="30"> <span
					class="hidden-xs"><b id="username"><%-- ${welcom.person_name} --%>QA Coordinator
					</b></span>
			</a>
				<ul class="dropdown-menu">
					<li><a href="/addDepartment.html"><i
							class="fa fa-fw fa-plus"></i> Add Department</a></li>
					<li><a href="/departments.html"><i
							class="fa fa-fw fa-suitcase"></i> Department Manager</a></li>
					<li><a href="#"><i class="fa fa-fw fa-user"></i> Edit
							Profile</a></li>
					<li><a href="#"><i class="fa fa-fw fa-cog"></i> Change
							Password</a></li>
					<li class="divider"></li>
					<li><a href="/student/logout"><i
							class="fa fa-fw fa-power-off"></i> Logout</a></li>
				</ul></li>
		</ul>
	</div>
</div>
<!-- <script type="text/javascript">
	$( "#navbar" ).load( "/navbar.html" );
</script> -->
<div class="container">
	<div class="row">   
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="list-group product-bread-a">
		  <p href="#" class="list-group-item product-bread-a" style="background:#dededd"> Computing 
		  	<button class="btn" style="float: right;"><i class="fa fa-trash-o"></i></button>
		  </p>
		</div> 
		<div style="overflow-x: auto;">
	    <table class="table table-striped">
	        <thead>
	          <tr>
	              <th>Service</th>        
	            </tr>
	          </thead>
	          <tbody>
	           <tr>
	           	<td><strong>Student</strong><br/>Ly Nguyen</td>
	            <td><strong>Title</strong><br/>IT</td>
	            <td><strong>Content</strong><br/>Network slowly</td>
	            <td><strong>Post time</strong><br/>April 9, 2018</td>
	            <td><strong>Close date</strong>
					<div id="datepicker" class="input-group date" data-date-format="dd-mm-yyyy"> 
						<input class="form-control" readonly="" type="text"> 
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div> 
					<script type="text/javascript">
						$(function () {  
						$("#datepicker").datepicker({         
						autoclose: true,         
						todayHighlight: true 
						}).datepicker('update', new Date());
						});
					</script>
				</td>
	            <td><strong>Accept</strong><br/><input type="checkbox" value="accept"/>     Accept</td>
	            <td><strong>Denied</strong><br/><input type="checkbox" value="denied"/>     Denied</td>
	          </tr>       
	          <!--Denbver-->
	        </tbody>
	        
	        <thead>
	          <tr>
	              <th>Attendance</th>        
	            </tr>
	          </thead>
	          <tbody>
	           <tr>
	           	<td><strong>Student</strong><br/>Duc Nguyen</td>
	            <td><strong>Title</strong><br/>Take attendance again</td>
	            <td><strong>Content</strong><br/>Please check for me</td>
	            <td><strong>Post time</strong><br/>April 10, 2018</td>
	            <td><strong>Close date</strong>
					<div id="datepicker" class="input-group date" data-date-format="dd-mm-yyyy"> 
						<input class="form-control" readonly="" type="text"> 
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div> 
					<script type="text/javascript">
						$(function () {  
						$("#datepicker").datepicker({         
						autoclose: true,         
						todayHighlight: true 
						}).datepicker('update', new Date());
						});
					</script>
				</td>
	            <td><strong>Accept</strong><br/><input type="checkbox" value="accept"/>     Accept</td>
	            <td><strong>Denied</strong><br/><input type="checkbox" value="denied"/>     Denied</td>
	          </tr>       
	          <!--Denbver-->
	        </tbody>
	      </table>
	      </div>
     </div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="list-group product-bread-a">
		  <p href="#" class="list-group-item product-bread-a" style="background:#dededd"> Business 
		  	<button class="btn" style="float: right;"><i class="fa fa-trash-o"></i></button>
		  </p>
		</div> 
		<div style="overflow-x: auto;">
	    <table class="table table-striped">
	        <thead>
	          <tr>
	              <th>Course</th>        
	            </tr>
	          </thead>
	          <tbody>
	           <tr>
	           	<td><strong>Student</strong><br/>Nhat Hoang</td>
	            <td><strong>Title</strong><br/>Business Analysis</td>
	            <td><strong>Content</strong><br/>Need documentation</td>
	            <td><strong>Post time</strong><br/>April 10, 2018</td>
	            <td><strong>Close date</strong>
					<div id="datepicker" class="input-group date" data-date-format="dd-mm-yyyy"> 
						<input class="form-control" readonly="" type="text"> 
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div> 
					<script type="text/javascript">
						$(function () {  
						$("#datepicker").datepicker({         
						autoclose: true,         
						todayHighlight: true 
						}).datepicker('update', new Date());
						});
					</script>
				</td>
	            <td><strong>Accept</strong><br/><input type="checkbox" value="accept"/>     Accept</td>
	            <td><strong>Denied</strong><br/><input type="checkbox" value="denied"/>     Denied</td>
	          </tr>       
	          <!--Denbver-->
	        </tbody>
	      </table>
	      </div>
     </div>

    </div>

   </div>
		
      
</body>
</html>