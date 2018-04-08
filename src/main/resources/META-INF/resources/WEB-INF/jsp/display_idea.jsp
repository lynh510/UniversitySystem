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
<script type="text/javascript" src="/javascript/list_idea.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="likes" class="com.system.models.LikeManagement"
	scope="page" />
<jsp:useBean id="comments" class="com.system.models.CommentManagement"
	scope="page" />
<jsp:useBean id="tags" class="com.system.models.IdeaTagManagement"
	scope="page" />
<title>Activity Log</title>
</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
		<div class="container">
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
							class="hidden-xs"><b id="username">${welcom.person_name}
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
	</div>
	<form action="/comment/submit" id="comment_form" method="post">
		<input type="hidden" name="idea_id" id="idea_id" value=""> <input
			type="hidden" name="text" id="commentText" value="">
	</form>
	<div class="section container">
		<c:forEach items="${ideas}" var="idea">
			<div class="row">
				<div class="[ col-xs-12 col-sm-offset-1 col-sm-10 col-md-10 ]">
					<div class="[ panel panel-default ] panel-google-plus">
						<div class="dropdown">
							<span class="dropdown-toggle" type="button"
								data-toggle="dropdown"> <span
								class="[ glyphicon glyphicon-chevron-down ]"></span>
							</span>
							<ul class="dropdown-menu" role="menu">
								<li role="presentation"><a role="menuitem" tabindex="-1"
									class="editIdea" onclick="return editIdea();">Edit</a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="/student/delete/${welcom.id}/${idea.id}">Delete</a></li>
								<!-- <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
		                        <li role="presentation" class="divider"></li>
		                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li> -->
							</ul>
						</div>
						<div class="panel-google-plus-tags">
							<ul>
								<c:forEach items="${tags.get_idea_tags(idea.id)}" var="t">
									<li>${t.tag.description}</li>
								</c:forEach>
							</ul>
						</div>
						<div class="panel-heading">
							<img class="[ img-circle pull-left ]"
								src="${idea.person.person_picture}" alt="Mouse0270" />
							<h3>${idea.person.person_name }</h3>
							<h5>
								<span>Shared publicly</span> - <span>${idea.post_date }</span>
							</h5>
						</div>
						<div class="panel-body">
							<div class="ideaInfo">
								<p>
									<b>${idea.title}</b>
								</p>
								<p>${idea.content}</p>
							</div>
							<form id="edit_idea_form" method="post" action="/student/edit">
								<div class="editIdeaInfo">
									<input type="hidden" name="idea_id" value="${idea.id}" /> <input
										type="hidden" name="person_id" value="${welcom.id}" /> <input
										class="form-control" name="title" required
										value="${idea.title}" />
									<textarea name="content" rows="4" required>${idea.content}</textarea>
									<button type="submit" class="[ btn btn-success disabled ]">Edit
										post</button>
									<button type="reset" class="[ btn btn-default ]">Cancel</button>
								</div>
							</form>
							<div class="clearfix"></div>
							<div class="box-likes">
								<div class="row" id="like_view_${idea.id}">
									<span><a href="#">${likes.count_like(1,idea.id)}</a></span> <span>Like
										this</span> <span><a href="#">${likes.count_like(2,idea.id)}</a></span>
									<span>Dislike this</span>
								</div>
								<div id="comment-count-${idea.id}" class="row">
									<span>${comments.countCommentsPerIdea(idea.id) }
										comment(s)</span>
								</div>
							</div>
						</div>

						<div class="panel-footer">
							<form id="like_form${idea.id}" method="post" action="/like/like">
								<input type="hidden" id="idea_id${idea.id}" name="idea" value="">
								<input type="hidden" name="like" id="like${idea.id}" value="">
							</form>
							<div id="like-box${idea.id}">
							<c:set var="checklike" value="${likes.check_like(idea.id)}"
								scope="page" />
								<c:choose>
									<c:when test="${checklike  == 1}">
										<button type="button" class="[ btn btn-default ]"
											id="btnthumbUp${idea.id}" style="background: blue;"
											onclick="onThumbUp(${idea.id},1)">
											<span class="[ glyphicon glyphicon-thumbs-up ]"
												style="color: white;"></span>
										</button>
										<button type="button" class="[ btn btn-default ]"
											id="btnthumbDown${idea.id}" onclick="onThumbUp(${idea.id},2)">
											<span class="[ glyphicon glyphicon-thumbs-down ]"
												style="color: red;"></span>
										</button>
									</c:when>
									<c:when test="${checklike == 2}">
										<button type="button" class="[ btn btn-default ]"
											id="btnthumbUp${idea.id}" onclick="onThumbUp(${idea.id},1)">
											<span class="[ glyphicon glyphicon-thumbs-up ]"
												style="color: blue;"></span>
										</button>
										<button type="button" class="[ btn btn-default ]"
											id="btnthumbDown${idea.id}" style="background: red;"
											onclick="onThumbUp(${idea.id},2)">
											<span class="[ glyphicon glyphicon-thumbs-down ]"
												style="color: white;"></span>
										</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="[ btn btn-default ]"
											id="btnthumbUp${idea.id}" onclick="onThumbUp(${idea.id},1)">
											<span class="[ glyphicon glyphicon-thumbs-up ]"
												style="color: blue;"></span>
										</button>
										<button type="button" class="[ btn btn-default ]"
											id="btnthumbDown${idea.id}" onclick="onThumbUp(${idea.id},2)">
											<span class="[ glyphicon glyphicon-thumbs-down ]"
												style="color: red;"></span>
										</button>
									</c:otherwise>
								</c:choose>
							</div>
							<div id="more-comment-box-${idea.id}" class="box-click">
								<button id="btnViewComments${idea.id}" onclick="onViewComments(${idea.id})">Comments</button>
								<input type="hidden" id="noOfComments${idea.id}"
									value="${comments.noOfComments(idea.id)}"> <span>
									<a id="moreComments${idea.id}" style="display: none"
									onclick="onLoadMoreComments(${idea.id})">View more comment(s)</a>
								</span>
							</div>
							<div id="box_comments${idea.id }" class="box-comments"></div>
							<div class="input-placeholder">Add a comment...</div>
						</div>
						<div class="panel-google-plus-comment">
							<img class="img-circle" src="${welcom.person_picture}"
								width="50" height="50" alt="User Image" />
							<div class="panel-google-plus-textarea">
								<textarea id="commentText${idea.id}" rows="4"></textarea>
								<button type="submit" onclick="onComment(${idea.id})"
									class="[ btn btn-success disabled ]">Post comment</button>
								<button type="reset" class="[ btn btn-default ]">Cancel</button>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<div style="width: 70%; margin: auto; text-align: center;">
		<%--For displaying Previous link except for the 1st page --%>
		<c:if test="${currentPage != 1}">
			<td><a href="${currentPage - 1}">Previous</a></td>
		</c:if>

		<%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
		<table border="1" cellpadding="5" cellspacing="5">
			<tr>
				<c:forEach begin="1" end="${noOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<td>${i}</td>
						</c:when>
						<c:otherwise>
							<td><a href="${i}">${i}</a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</table>

		<%--For displaying Next link --%>
		<c:if test="${currentPage lt noOfPages}">
			<td><a href="${currentPage + 1}">Next</a></td>
		</c:if>
	</div>

</body>
</html>