$(function () {
	$('.editIdeaInfo').hide();
   $('.panel-google-plus > .panel-footer > .input-placeholder, .panel-google-plus > .panel-google-plus-comment > .panel-google-plus-textarea > button[type="reset"]').on('click', function(event) {
        var $panel = $(this).closest('.panel-google-plus');
            $comment = $panel.find('.panel-google-plus-comment');
            
        $comment.find('.btn:first-child').addClass('disabled');
        $comment.find('textarea').val('');
        
        $panel.toggleClass('panel-google-plus-show-comment');
        
        if ($panel.hasClass('panel-google-plus-show-comment')) {
            $comment.find('textarea').focus();
        }
   });
   $('.panel-google-plus-comment > .panel-google-plus-textarea > textarea').on('keyup', function(event) {
        var $comment = $(this).closest('.panel-google-plus-comment');
        
        $comment.find('button[type="submit"]').addClass('disabled');
        if ($(this).val().length >= 1) {
            $comment.find('button[type="submit"]').removeClass('disabled');
        }
   });
   $('.editIdeaInfo > button[type="reset"]').on('click', function(event) {
	   $('.editIdeaInfo').hide();
	   $('.ideaInfo').show();
   });
   $('.editIdeaInfo > input, .editIdeaInfo > textarea').on('focus', function(event) {
       var $comment = $(this).closest('.editIdeaInfo');
       
       $comment.find('button[type="submit"]').addClass('disabled');
       if ($(this).val().length >= 1) {
           $comment.find('button[type="submit"]').removeClass('disabled');
       }
  });
});

function editIdea () {
    $('.ideaInfo').hide();
    $('.editIdeaInfo').show();
}

function onThumbUp(idea_id,like) {
	document.getElementById("like" + idea_id).value = like;
	document.getElementById("idea_id" + idea_id).value = idea_id;
	$.ajax({
		type : "Post",
		url : "/like/like",
		data : $("#like_form" + idea_id).serialize(),
		success : function(response) {
			jQuery('#like_view_'+idea_id).load(' #like_view_'+idea_id);
			if(like == 1) {
				$('#btnthumbUp' + idea_id).css('background-color','blue');
				$('#btnthumbUp' + idea_id + ' span').css('color','white');
				$('#btnthumbDown' + idea_id).css('background-color','white');
			}
			if(like == 2) {
				$('#btnthumbDown' + idea_id).css('background-color','red');
				$('#btnthumbDown' + idea_id + ' span').css('color','white');
				$('#btnthumbUp' + idea_id).css('background-color','white');
			}
		},
		error: function(xhr,response,error){	
			var err = JSON.parse(xhr.responseText);
			alert(err.message);
		}
	});
}