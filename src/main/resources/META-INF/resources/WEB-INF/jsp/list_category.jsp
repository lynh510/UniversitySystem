<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/list_idea.css">

<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="https://momentjs.com/downloads/moment.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<title>List Category</title>
<style type="text/css">
	.container {
		margin-top: 5%;
		margin-left: 20%;
	}
	.single {
		padding: 30px 15px;
		margin-top: 40px;
		background: #fcfcfc;
		border: 1px solid #f0f0f0; 
	}
	.single h3.side-title {
		margin: 0;
		margin-bottom: 10px;
		padding: 0;
		font-size: 20px;
		color: #333;
		text-transform: uppercase; 
	}
	.single h3.side-title:after {
		content: '';
		width: 100%;
		height: 1px;
		background: #ff173c;
		display: block;
		margin-top: 6px; 
	}
	.single ul {
		margin-bottom: 0; 
	}
	.single li {
		color: #666;
		font-size: 14px;
		text-transform: uppercase;
		border-bottom: 1px solid #f0f0f0;
		line-height: 40px;
		display: block;
		text-decoration: none; 
	}
	.single li a:hover {
		color: #ff173c; 
	}
	.single li a {
		float: right; 
	}
	.single li:last-child a {
		border-bottom: 0; 
	}
	.count {
		background-color: silver;
		border-radius: 20px;
		padding: 1%;
		margin-left: 5%;
	}
	.navbar-nav.navbar-right:last-child {
		margin-right: 3%;
	}
</style>
</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
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
						class="hidden-xs"><b id="username">Username
						</b></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/student/activities/${welcom.id}/1"><i
								class="fa fa-fw fa-history"></i> View Activity Log</a></li>
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
	<div class="container">
		<div class="row">
			<div class="col-sm-10">
				<!-- Category -->
				<div class="single category">
					<h3 class="side-title">Category</h3>
					<ul class="list-unstyled">
						<li>Business <span class="count"> 13 </span><a href="">Delete</a></li>
						<li>Technology <span class="count">13</span><a href="">Delete</a></li>
						<li>Web <span class="count">13</span><a href="">Delete</a></li>
						<li>Ecommerce <span class="count">13</span><a href="">Delete</a></li>
						<li>Wordpress <span class="count">13</span><a href="">Delete</a></li>
						<li>Android <span class="count">13</span><a href="">Delete</a></li>
						<li>IOS <span class="count">13</span><a href="">Delete</a></li>
						<li>Windows <span class="count">13</span><a href="">Delete</a></li>
					</ul>
			   </div>
			</div> 
		</div>
	</div>
</body>
</html>