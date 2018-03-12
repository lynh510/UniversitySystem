<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of idea</title>
<link rel="stylesheet" href="/css/list_idea.css">
<script type="text/javascript" src="/javascript/list_idea.js"></script>
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="tags" class="com.system.models.IdeaTagManagement"
	scope="page" />
</head>
<body>
	<div class="container">
		<c:forEach items="${ideas}" var="idea">
			<div class="row">
				<%-- <c:forEach items="${ideas}" var="idea"> --%>
				<div class="[ col-xs-12 col-sm-offset-1 col-sm-10 col-md-10 ]">
					<div class="[ panel panel-default ] panel-google-plus">
						<!-- <div class="dropdown">
		                    <span class="dropdown-toggle" type="button" data-toggle="dropdown">
		                        <span class="[ glyphicon glyphicon-chevron-down ]"></span>
		                    </span>
		                    <ul class="dropdown-menu" role="menu">
		                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
		                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
		                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
		                        <li role="presentation" class="divider"></li>
		                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
		                    </ul>
		                </div> -->
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
							<p>
								<b>${idea.title}</b>
							</p>
							<p>${idea.content}</p>
						</div>
						<div class="panel-footer">
							<button type="button" class="[ btn btn-default ]">
								<span class="[ glyphicon glyphicon-thumbs-up ]"
									style="color: blue;"></span>
							</button>
							<button type="button" class="[ btn btn-default ]">
								<span class="[ glyphicon glyphicon-thumbs-down ]"
									style="color: red;"></span>
							</button>
							<button type="button" class="[ btn btn-default ]">
								<span class="[ glyphicon glyphicon-share-alt ]"></span>
							</button>
							<textarea class="input-placeholder">Add a comment...</textarea>
						</div>
						<div class="panel-google-plus-comment">
							<img class="img-circle"
								src="https://lh3.googleusercontent.com/uFp_tsTJboUY7kue5XAsGA=s46"
								alt="User Image" />
							<div class="panel-google-plus-textarea">
								<textarea rows="4"></textarea>
								<button type="submit" class="[ btn btn-success disabled ]">Post
									comment</button>
								<button type="reset" class="[ btn btn-default ]">Cancel</button>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				<%-- </c:forEach> --%>
			</div>
		</c:forEach>
	</div>


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
</body>
</html>