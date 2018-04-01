//$(document).ready(function() {
//	 $("#login-form").submit(function(e){
//		 e.preventDefault();
//			$.ajax({
//				type : "post",
//				url : "/qa/authorization",
//				data : $("#login_form").serialize(),
//				success : function(response) {
//						
//				},
//				error : function(response) {
////					var err = JSON.parse(xhr.responseText);
////					alert(err);
//					//document.getElementById("errors").innerHTML = err.message;
//				}
//			});
//			return false;
//	 });
//});

$(function() {

    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});

});

function validateForm() {
    var x = document.forms["registration-form"]["email"].value;
    var atpos = x.indexOf("@");
    var dotpos = x.lastIndexOf(".");
    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 1 >= x.length) {
       document.getElementById("message").innerHTML ="Not a valid e-mail address" ;
       return false;
    }else{
    	document.getElementById("message").innerHTML ="" ;
    }
    
}
//CONFIRM PASSWORD
var check = function() {
	if (document.getElementById('password').value ==
		document.getElementById('password_confirmation').value) {
		document.getElementById('msgConfirm').innerHTML = "";
		return true;
	} 
	if (document.getElementById('password').value == "") {
		document.getElementById('msgConfirm').innerHTML = "";
		return false;
	}
	else {
		document.getElementById('msgConfirm').innerHTML = "Password doesn't match!";
		return false;
	}
}