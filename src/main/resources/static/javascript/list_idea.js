function editIdea(id) {
	$('.ideaInfo' + id).hide();
	$('.editIdeaInfo' + id).show();
	$('#edit_idea_form' + id).css({
		'display' : 'block'
	});
}

function onThumbUp(idea_id, like) {
	document.getElementById("like" + idea_id).value = like;
	document.getElementById("idea_id" + idea_id).value = idea_id;
	$
			.ajax({
				type : "Post",
				url : "/like/like",
				data : $("#like_form" + idea_id).serialize(),
				success : function(response) {
					// jQuery('#like_view_' + idea_id).load(
					// ' #like_view_' + idea_id);
					// jQuery('#like-box' + idea_id).load(' #like-box' +
					// idea_id);
					var like_count = $('#like_count_' + idea_id).text();
					var dislike_count = $('#dislike_count_' + idea_id).text();
					if (like == 1) {
						if ($('#btnthumbUp' + idea_id).css('background-color') == 'rgb(0, 0, 255)') {
							$('#btnthumbUp' + idea_id).css('background-color',
									'white');
							$('#btnthumbUp' + idea_id + ' span').css('color',
									'blue');
							$('#like_count_' + idea_id).text(
									parseInt(like_count) - 1);
						} else {
							if ($('#btnthumbDown' + idea_id).css(
									'background-color') == 'rgb(255, 0, 0)') {
								$('#dislike_count_' + idea_id).text(
										parseInt(like_count) - 1);
							}
							$('#btnthumbUp' + idea_id).css('background-color',
									'blue');
							$('#btnthumbUp' + idea_id + ' span').css('color',
									'white');
							$('#btnthumbDown' + idea_id).css(
									'background-color', 'white');
							$('#btnthumbDown' + idea_id + ' span').css('color',
									'red');
							$('#like_count_' + idea_id).text(
									parseInt(like_count) + 1);
						}
					}
					if (like == 2) {
						if ($('#btnthumbDown' + idea_id)
								.css('background-color') == 'rgb(255, 0, 0)') {
							$('#btnthumbDown' + idea_id).css(
									'background-color', 'white');
							$('#btnthumbDown' + idea_id + ' span').css('color',
									'red');
							$('#dislike_count_' + idea_id).text(
									parseInt(dislike_count) - 1);
						} else {
							if ($('#btnthumbUp' + idea_id).css(
									'background-color') == 'rgb(0, 0, 255)') {
								$('#like_count_' + idea_id).text(
										parseInt(like_count) - 1);
							}
							$('#btnthumbDown' + idea_id).css(
									'background-color', 'red');
							$('#btnthumbDown' + idea_id + ' span').css('color',
									'white');
							$('#btnthumbUp' + idea_id).css('background-color',
									'white');
							$('#btnthumbUp' + idea_id + ' span').css('color',
									'blue');
							$('#dislike_count_' + idea_id).text(
									parseInt(dislike_count) + 1);
						}
					}
				},
				error : function(xhr, response, error) {
					var err = JSON.parse(xhr.responseText);
					alert(err.message);
					if (err.message == "You have to login first") {
						window.location = "/student/login.html";
					}
				}
			});
}
function onComment(idea_id) {
	$
			.ajax({
				type : "Post",
				url : "/comment/submit",
				data : $("#comment_form_" + idea_id).serialize(),
				success : function(response) {
					var boxComments = document.getElementById("box_comments"
							+ idea_id);
					var date = new Date();
					var data = response.data;
					boxComments
							.insertAdjacentHTML(
									'beforeend',
									'<div class="comment" id="comment_row_'
											+ data.comment_id
											+ '"><img src="'
											+ data.person.person_picture
											+ '" alt="" /> '
											+ '<div class="content">'
											+ '<h3><a href="#">'
											+ data.person.person_name
											+ '</a><span> <time>'
											+ moment(date).fromNow()
											+ '</time><a href="#"> Like</a></span></h3><p id = "comment_text_'
											+ data.comment_id
											+ '">'
											+ data.comment_text
											+ '</p><span style="float: right"><a onclick="edit('
											+ idea_id
											+ ','
											+ data.comment_id
											+ ')">Edit</a>'
											+ '| <a onclick="onDelete('
											+ data.comment_id
											+ ','
											+ idea_id
											+ ')">Delete</a></span></div></div>');

					$('#commentText' + idea_id).val('');
					$('#comment-count-' + idea_id).load(
							' #comment-count-' + idea_id);

				},
				error : function(xhr, response, error) {
					var err = JSON.parse(xhr.responseText);
					alert(err.message);
				}
			})
}
function onViewComments(idea_id) {
	var noOfComments = document.getElementById("noOfComments" + idea_id);
	var moreComments = document.getElementById("moreComments" + idea_id);
	var btnViewComments = document.getElementById("btnViewComments" + idea_id);
	var boxComments = document.getElementById("box_comments" + idea_id);
	btnViewComments.setAttribute("disabled", "disabled");
	if (noOfComments.value > 0) {
		$
				.ajax({
					type : "get",
					url : "/comment/moreComments/" + idea_id + "/"
							+ noOfComments.value,
					data : null,
					success : function(response) {
						boxComments.innerHTML = "";
						showComments(response, idea_id);
						if (noOfComments.value > 0) {
							noOfComments.value = noOfComments.value - 1;
							moreComments.style = "";
						}
						if (noOfComments.value == 0) {
							moreComments.style = "display:none";
						}
					},
					error : function(xhr, response, error) {
						var err = JSON.parse(xhr.responseText);
						alert(err.message);
					}
				});

	}
}
function showComments(response, idea_id) {
	var boxComments = document.getElementById("box_comments" + idea_id);
	var list = "";
	var data = response.data;
	for (i = 0; i < data.length; i++) {
		var date = new Date(data[i].comment_time);
		if (data[i].mode == 0) {
			if (response.message == data[i].person.id) {
				list += '<div class="comment" id="comment_row_'
						+ data[i].comment_id
						+ '"><img src="'
						+ data[i].person.person_picture
						+ '" alt="" /> '
						+ '<div class="content">'
						+ '<h3><a href="#">'
						+ data[i].person.person_name
						+ '</a><span> <time>'
						+ moment(date).fromNow()
						+ '</time><a href="#"> Like</a></span></h3><p id = "comment_text_'
						+ data[i].comment_id + '">' + data[i].comment_text
						+ '</p><span style="float: right"><a onclick="edit('
						+ idea_id + ',' + data[i].comment_id + ')">Edit</a>'
						+ '| <a onclick="onDelete(' + data[i].comment_id + ','
						+ idea_id + ')">Delete</a></span></div></div>';
			} else {
				list += '<div class="comment"><img src="'
						+ data[i].person.person_picture + '" alt="" /> '
						+ '<div class="content">' + '<h3><a href="">'
						+ data[i].person.person_name + '</a><span> <time>'
						+ moment(date).fromNow()
						+ '</time><a href="#"> Like</a></span></h3><p>'
						+ data[i].comment_text + '</p></div></div>';
			}
		} else {
			if (response.message == data[i].person.id) {
				list += '<div class="comment" id="comment_row_'
						+ data[i].comment_id
						+ '"><img src="'
						+ data[i].person.person_picture
						+ '" alt="" /> '
						+ '<div class="content">'
						+ '<h3>'
						+ data[i].person.person_name
						+ '<span> <time>'
						+ moment(date).fromNow()
						+ '</time><a href="#"> Like</a></span></h3><p id = "comment_text_'
						+ data[i].comment_id + '">' + data[i].comment_text
						+ '</p><span style="float: right"><a onclick="edit('
						+ idea_id + ',' + data[i].comment_id + ')">Edit</a>'
						+ '| <a onclick="onDelete(' + data[i].comment_id + ','
						+ idea_id + ')">Delete</a></span></div></div>';
			} else {
				list += '<div class="comment"><img src="'
						+ data[i].person.person_picture + '" alt="" /> '
						+ '<div class="content">' + '<h3>'
						+ data[i].person.person_name + '<span> <time>'
						+ moment(date).fromNow()
						+ '</time><a href="#"> Like</a></span></h3><p>'
						+ data[i].comment_text + '</p></div></div>';
			}
		}

	}
	boxComments.insertAdjacentHTML('afterbegin', list);

}
function onLoadMoreComments(idea_id) {
	var noOfComments = document.getElementById("noOfComments" + idea_id);
	var moreComments = document.getElementById("moreComments" + idea_id);
	if (noOfComments.value > 0) {
		$
				.ajax({
					type : "get",
					url : "/comment/moreComments/" + idea_id + "/"
							+ noOfComments.value,
					data : null,
					success : function(response) {
						showComments(response.data, idea_id);
						if (noOfComments.value > 0) {
							noOfComments.value = noOfComments.value - 1;
						}
						if (noOfComments.value == 0) {
							moreComments.style = "display:none";
						}
					},
					error : function(xhr, response, error) {
						var err = JSON.parse(xhr.responseText);
						alert(err.message);
					}
				});
		moreComments.style = "";
	}
}

function onView(idea_id) {
	$.ajax({
		type : "get",
		url : "/idea/onview/" + idea_id,
		data : null,
		success : function(response) {

		},
		error : function(xhr, response, error) {

		}
	});
}

function onEdit(idea_id, comment_id) {
	$.ajax({
		type : "post",
		url : "/comment/edit/",
		data : $("#comment_form_" + idea_id).serialize(),
		success : function(response) {
			$('#comment_text_' + comment_id).text(
					$("#comment_form_" + idea_id + " textarea[name=text]")
							.val());
		},
		error : function(xhr, response, error) {

		}
	});
}

function onDelete(comment_id, idea_id) {
	$.ajax({
		type : "get",
		url : "/comment/delete/" + comment_id,
		data : null,
		success : function(response) {
			$("#comment_row_" + comment_id).remove();
			$('#comment-count-' + idea_id).load(' #comment-count-' + idea_id);
		},
		error : function(xhr, response, error) {

		}
	});
}

function edit(idea_id, comment_id) {
	$("#comment_form_" + idea_id + " textarea[name=text]").val(
			$('#comment_text_' + comment_id).text());
	$('a#post_comment_btn_' + idea_id).text('Edit comment');
	$('a#post_comment_btn_' + idea_id).attr("onclick",
			'onEdit(' + idea_id + ',' + comment_id + ')');
	$('#hidden_comment_' + idea_id).val(comment_id);
}

function cancel(idea_id) {
	$('a#post_comment_btn_' + idea_id).text('Post comment');
	$('a#post_comment_btn_' + idea_id).attr("onclick",
			'onComment(' + idea_id + ')');
	$('#hidden_comment_' + idea_id).val(0);
}
