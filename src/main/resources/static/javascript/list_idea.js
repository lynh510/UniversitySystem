$(function() {
	$('.editIdeaInfo').hide();
	$(
			'.panel-google-plus > .panel-footer > .input-placeholder, .panel-google-plus > .panel-google-plus-comment > .panel-google-plus-textarea > button[type="reset"]')
			.on('click', function(event) {
				var $panel = $(this).closest('.panel-google-plus');
				$comment = $panel.find('.panel-google-plus-comment');

				$comment.find('.btn:first-child').addClass('disabled');
				$comment.find('textarea').val('');

				$panel.toggleClass('panel-google-plus-show-comment');

				if ($panel.hasClass('panel-google-plus-show-comment')) {
					$comment.find('textarea').focus();
				}
			});
	$('.panel-google-plus-comment > .panel-google-plus-textarea > textarea')
			.on(
					'keyup',
					function(event) {
						var $comment = $(this).closest(
								'.panel-google-plus-comment');

						$comment.find('button[type="submit"]').addClass(
								'disabled');
						if ($(this).val().length >= 1) {
							$comment.find('button[type="submit"]').removeClass(
									'disabled');
						}
					});
	$('.editIdeaInfo > button[type="reset"]').on('click', function(event) {
		$('.editIdeaInfo').hide();
		$('.ideaInfo').show();
	});
	$('.editIdeaInfo > input, .editIdeaInfo > textarea').on(
			'focus',
			function(event) {
				var $comment = $(this).closest('.editIdeaInfo');

				$comment.find('button[type="submit"]').addClass('disabled');
				if ($(this).val().length >= 1) {
					$comment.find('button[type="submit"]').removeClass(
							'disabled');
				}
			});
});

function editIdea() {
	$('.ideaInfo').hide();
	$('.editIdeaInfo').show();
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
					jQuery('#like_view_' + idea_id).load(
							' #like_view_' + idea_id);
					// jQuery('#like-box' + idea_id).load(' #like-box' +
					// idea_id);
					if (like == 1) {
						if ($('#btnthumbUp' + idea_id).css('background-color') == 'rgb(0, 0, 255)') {
							$('#btnthumbUp' + idea_id).css('background-color',
									'white');
							$('#btnthumbUp' + idea_id + ' span').css('color',
									'blue');
						} else {
							$('#btnthumbUp' + idea_id).css('background-color',
									'blue');
							$('#btnthumbUp' + idea_id + ' span').css('color',
									'white');
							$('#btnthumbDown' + idea_id).css(
									'background-color', 'white');
							$('#btnthumbDown' + idea_id + ' span').css('color',
									'red');
						}
					}
					if (like == 2) {
						if ($('#btnthumbDown' + idea_id)
								.css('background-color') == 'rgb(255, 0, 0)') {
							$('#btnthumbDown' + idea_id).css(
									'background-color', 'white');
							$('#btnthumbDown' + idea_id + ' span').css('color',
									'red');
						} else {
							$('#btnthumbDown' + idea_id).css(
									'background-color', 'red');
							$('#btnthumbDown' + idea_id + ' span').css('color',
									'white');
							$('#btnthumbUp' + idea_id).css('background-color',
									'white');
							$('#btnthumbUp' + idea_id + ' span').css('color',
									'blue');
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
	document.getElementById("idea_id").value = idea_id;
	var commentText = document.getElementById("commentText" + idea_id).value;
	document.getElementById("commentText").value = commentText;
	$
			.ajax({
				type : "Post",
				url : "/comment/submit",
				data : $("#comment_form").serialize(),
				success : function(response) {
					var boxComments = document.getElementById("box_comments"
							+ idea_id);
					var date = new Date();
					boxComments
							.insertAdjacentHTML(
									'beforeend',
									'<div class="comment"><img src="'
											+ response.data.person.person_picture
											+ '" alt="" /> '
											+ '<div class="content">'
											+ '<h3><a href="">'
											+ response.data.person.person_name
											+ '</a><span> <time>'
											+ moment.utc(date).fromNow()
											+ '</time><a href="#"> Like</a></span></h3><p>'
											+ response.data.comment_text
											+ '</p></div></div>');

					$('#commentText' + idea_id).val('');
					$('#comment-count-' + idea_id).load(' #comment-count-' + idea_id);
					// $('#more-comment-box-' + idea_id).load(
					// ' #more-comment-box-' + idea_id);
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
	btnViewComments.setAttribute("disabled","disabled");
	if (noOfComments.value > 0) {
		$
				.ajax({
					type : "get",
					url : "/comment/moreComments/" + idea_id + "/"
							+ noOfComments.value,
					data : null,
					success : function(response) {
						boxComments.innerHTML = "";
						showComments(response.data, idea_id);
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
function showComments(data, idea_id) {
	var boxComments = document.getElementById("box_comments" + idea_id);
	var list = "";
	for (i = 0; i < data.length; i++) {
		var date = new Date(data[i].comment_time);
		list += '<div class="comment"><img src="'
				+ data[i].person.person_picture + '" alt="" /> '
				+ '<div class="content">' + '<h3><a href="">'
				+ data[i].person.person_name + '</a><span> <time>'
				+ moment(date).fromNow()
				+ '</time><a href="#"> Like</a></span></h3><p>'
				+ data[i].comment_text + '</p></div></div>';
	}
	boxComments.insertAdjacentHTML('afterbegin', list);

}
function onLoadMoreComments(idea_id) {
	var noOfComments = document.getElementById("noOfComments" + idea_id);
	var moreComments = document.getElementById("moreComments" + idea_id);
	alert(idea_id);
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
