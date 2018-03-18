$(document).on('click', '#close-preview', function() {
	$('.image-preview').popover('hide');
	// Hover befor close the preview
	$('.image-preview').hover(function() {
		$('.image-preview').popover('show');
	}, function() {
		$('.image-preview').popover('hide');
	});
});
$(document).ready(function() {
	$("#submit_idea_form").submit(function(e) {
		e.preventDefault();
		var message = document.getElementById("message");
		$.ajax({
			type : "Post",
			url : "/idea/submit",
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			data : new FormData($("#submit_idea_form")[0]),
			success : function(response) {
				message.style.color = 'green';
				message.innerHTML = 'Your idea is successfully posted';
			},
			error : function(xhr, response, error) {
				var err = JSON.parse(xhr.responseText);
				message.style.color = 'red';
				message.innerHTML = err.message;
			}
		});
		return false;
	});
});

$(function() {
	// Create the close button
	var closebtn = $('<button/>', {
		type : "button",
		text : 'x',
		id : 'close-preview',
		style : 'font-size: initial;',
	});
	closebtn.attr("class", "close pull-right");
	// Clear event
	$('.image-preview-clear').click(function() {
		$('.image-preview').attr("data-content", "").popover('hide');
		$('.image-preview-filename').val("");
		$('.image-preview-clear').hide();
		$('.image-preview-input input:file').val("");
		$(".image-preview-input-title").text("Browse");
	});
	// Create the preview image
	$(".image-preview-input input:file").change(
			function() {
				var img = $('<img/>', {
					id : 'dynamic',
					width : 250,
					height : 200
				});
				var file = this.files[0];
				var reader = new FileReader();
				// Set preview image into the popover data-content
				reader.onload = function(e) {
					$(".image-preview-input-title").text("Change");
					$(".image-preview-clear").show();
					$(".image-preview-filename").val(file.name);
					img.attr('src', e.target.result);
					$(".image-preview").attr("data-content",
							$(img)[0].outerHTML).popover("show");
				}
				reader.readAsDataURL(file);
			});
});

var check = function() {
	var title = document.getElementById('title');
	var splChars = "*|,\":<>[]{}`\';()@&$#%";
	for (var i = 0; i < title.value.length; i++) {
		if (splChars.indexOf(title.value.charAt(i)) != -1) {
			document.getElementById('msgCheck').innerHTML = "The title mustn't have special character!";
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