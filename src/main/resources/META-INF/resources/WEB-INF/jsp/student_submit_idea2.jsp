<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student - Submit Your Idea</title>
<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	id="bootstrap-css">
<!------ Include the above in your HEAD tag ---------->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1"
	crossorigin="anonymous">
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="/css/jquery-te-1.4.0.css">
<script type="text/javascript"
	src="http://code.jquery.com/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/javascript/jquery-te-1.4.0.min.js"
	charset="utf-8"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Then include bootstrap js -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="/css/stu_submit_idea.css">
<script type="text/javascript" src="/javascript/stu_sub_idea.js"></script>
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
	<div class="screen">
		<!-- HEADER -->
		<div class="navbar navbar-default navbar-fixed-top">
			<header id="header-site">
				<!-- <div class="logo-site">
			<h1><a href="#">Leistung</a></h1>
		</div> -->

				<!--Begin::Mobile View Navigation-->
				<!-- <button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button> -->
				<!--End::Mobile View Navigation-->

				<!--Begin::Header right-->
				<ul class="nav navbar-right pull-right top-nav">
					<li class="dropdown dropdown-notification"><a
						class="dropdown-toggle" href="javascript:;" data-toggle="dropdown"
						data-hover="dropdown" data-close-others="true"
						aria-expanded="true"> <i class="fa fa-bell-o"></i> <span
							class="badge badge-default"> 3 </span>
					</a>
						<ul class="dropdown-menu">
							<li class="external">
								<h3>
									<span class="bold">12 pending</span> notifications
								</h3> <a href="page_user_profile_1.html">view all</a>
							</li>
							<li>
								<ul class="dropdown-menu-list">
									<li><a href="javascript:;"> <span class="time">just
												now</span> <span class="details"> <span
												class="label label-sm label-icon label-success"> <i
													class="fa fa-plus"></i>
											</span> New user registered.
										</span>
									</a></li>
									<li><a href="javascript:;"> <span class="time">3
												mins</span> <span class="details"> <span
												class="label label-sm label-icon label-danger"> <i
													class="fa fa-bolt"></i>
											</span> Server #12 overloaded.
										</span>
									</a></li>
									<li><a href="javascript:;"> <span class="time">10
												mins</span> <span class="details"> <span
												class="label label-sm label-icon label-warning"> <i
													class="fa fa-bell-o"></i>
											</span> Server #2 not responding.
										</span>
									</a></li>
									<li><a href="javascript:;"> <span class="time">14
												hrs</span> <span class="details"> <span
												class="label label-sm label-icon label-info"> <i
													class="fa fa-bullhorn"></i>
											</span> Application error.
										</span>
									</a></li>
								</ul>
							</li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" aria-expanded="true"> <img alt=""
							class="img-circle" src="${welcom.person_picture}" width="30"
							height="30"> <span class="hidden-xs"><b>${welcom.person_name}
							</b></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="/student/activities/${user_id}/1"><i
									class="fa fa-fw fa-history"></i> View Activity Log</a></li>
							<li><a href="/student/submit_idea"><i
									class="fa fa-fw fa-plus"></i> Add idea</a></li>
							<li><a href="/student/edit_account"><i
									class="fa fa-fw fa-user"></i> Edit Profile</a></li>
							<li><a href="#"><i class="fa fa-fw fa-cog"></i> Change
									Password</a></li>
							<li class="divider"></li>
							<li><a href="/student/logout"><i
									class="fa fa-fw fa-power-off"></i> Logout</a></li>
						</ul></li>
				</ul>
				<!--End::Header Right-->


				<!--Begin::Nav-->
				<!-- <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-left">
				<li><a id="home" href="#">Home</a></li>
				<li><a id="about" href="#">About</a></li>
				<li><a id="" href="#">Service</a></li>
				<li><a id="" href="#">Contact</a></li>
			</ul>
		</div> -->
				<!--End::Nav-->

			</header>
		</div>
		<!-- CONTENT -->
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<div class="panel-group" id="accordion">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseOne"><span class="glyphicon glyphicon-file">
									</span>POST NEW IDEA</a>

								</h4>
								<!-- <h2 style="color: red;" id="message"></h2> -->
							</div>
							<div id="collapseOne" class="panel-collapse collapse in">

								<form role="form" id="submit_idea_form" action="/idea/submit"
									method="post" enctype="multipart/form-data">
									<div class="panel-body">
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
													<label for="title" class="required">Title</label> <input
														type="text" class="form-control blur" placeholder="Title"
														name="title" id="title" required onkeyup='check();' />
													<h6 style="color: red; margin-left: 5%;" id="msgCheck"></h6>
												</div>
												<div class="form-group">
													<label for="content" class="required">Content</label>
													<textarea name="content" required="required"
														class="jqte-test">Write something</textarea>
													<script>
														$('.jqte-test').jqte();
													</script>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
													<label for="tags" class="required">Tags</label> <select
														multiple="multiple" class="form-control blur" id="tag"
														name="tag" required>
														<c:forEach items="${tags}" var="tag">
															<option value="${tag.id}">${tag.description}</option>
														</c:forEach>

													</select>
												</div>
											</div>
											<div class="col-md-12">
												<label for="attachment">Attachment</label>
												<div class="input-group image-preview">
													<input type="text"
														class="form-control image-preview-filename blur"
														name="file" disabled="disabled">
													<!-- don't give a name === doesn't send on POST/GET -->
													<span class="input-group-btn"> <!-- image-preview-clear button -->
														<button type="button"
															class="btn btn-default image-preview-clear"
															style="display: none;">
															<span class="glyphicon glyphicon-remove"></span> Clear
														</button> <!-- image-preview-input -->
														<div class="btn btn-default image-preview-input">
															<span class="glyphicon glyphicon-folder-open"></span> <span
																class="image-preview-input-title">Browse</span> <input
																type="file" multiple="multiple"
																accept="image/png, image/jpeg, image/gif, .doc, .pdf, .xls"
																name="input-file-preview" />
															<!-- rename it -->
														</div>
													</span>
												</div>
												<!-- /input-group image-preview [TO HERE]-->
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
													<label for="modes" class="required">Modes</label> <select
														class="form-control blur" name="mode" required>
														<option value="0" style="color: #000;">Anonymous</option>
														<option value="1" style="color: #000;">Published</option>
													</select>
												</div>
												<div class="form-group">
													<div>
														<input id="agree" value="0" type="radio" required><a
															href="/student/terms">Before submitting please
															carefully read these terms and conditions</a>
													</div>
													<button id="submit" type="submit" class="btn">
														<span class="glyphicon glyphicon-floppy-disk"></span>Submit
													</button>
												</div>
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