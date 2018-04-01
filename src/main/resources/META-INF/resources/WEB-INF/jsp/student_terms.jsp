<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!-- Put this in your head tag in your template  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<title>Terms and Conditions for Student</title>
<script type="text/javascript">
	$(function(){
		 
	    $(document).on( 'scroll', function(){
	 
	    	if ($(window).scrollTop() > 100) {
				$('.scroll-top-wrapper').addClass('show');
			} else {
				$('.scroll-top-wrapper').removeClass('show');
			}
		});
	 
		$('.scroll-top-wrapper').on('click', scrollToTop);
	});
	 
	function scrollToTop() {
		verticalOffset = typeof(verticalOffset) != 'undefined' ? verticalOffset : 0;
		element = $('body');
		offset = element.offset();
		offsetTop = offset.top;
		$('html, body').animate({scrollTop: offsetTop}, 500, 'linear');
	}
</script>
<style type="text/css">
	.container {
		width: 80%;
		float: right;
		margin-right: 1%;
	}
	
	.div-title {
		background-color: rgba(128, 128, 128, 0.3);
		height: 200px;
	}
	body {
		font-family: sans-serif;
	}
	.title {
		font-size: 45px;
		color: #666;
		margin-left: 3%;
		margin-top: 0px;
		padding-top: 10%;
	}
	.div-content {
		margin-top: 20%;
	}
	.time {
		font-style: italic;
		float: right;
		margin-right: 2%;
		margin-top: 2%;
	}
	.des {
		display: block;
		clear: both;
		margin-left: 10%;
		margin-right: 10%;
		padding-top: 2%;
		margin-bottom: 5%;
	}
	.terms>p {
		margin-left: 2%;
	}
	li {
		line-height: 1.3em;
	}
	.scroll-top-wrapper {
	    position: fixed;
	    opacity: 0;
	    visibility: hidden;
		overflow: hidden;
		text-align: center;
		z-index: 99999999;
	    background-color: #777777;
		color: #eeeeee;
		width: 50px;
		height: 48px;
		line-height: 48px;
		right: 30px;
		bottom: 30px;
		padding-top: 2px;
		border-top-left-radius: 10px;
		border-top-right-radius: 10px;
		border-bottom-right-radius: 10px;
		border-bottom-left-radius: 10px;
		-webkit-transition: all 0.5s ease-in-out;
		-moz-transition: all 0.5s ease-in-out;
		-ms-transition: all 0.5s ease-in-out;
		-o-transition: all 0.5s ease-in-out;
		transition: all 0.5s ease-in-out;
	}
	.scroll-top-wrapper:hover {
		background-color: #888888;
	}
	.scroll-top-wrapper.show {
	    visibility:visible;
	    cursor:pointer;
		opacity: 1.0;
	}
	.scroll-top-wrapper i.fa {
		line-height: inherit;
	}
	
	.mt-100 {
	    margin-top: 100px; 
	}
	.mb-100 {
	    margin-bottom: 100px;
	}
	
	.icon {
	    width: 32px;
	    height: 32px;
	    text-align: center;
	    padding: 7px 8px;
	    border: 2px solid;
	    border-radius: 50%;
	}
	
	.header {
	    padding-top: 50px;
	    background-color: #eee;
	    overflow: hidden;
	}
	.footer {
	    color: #887;
	    background-color: #eee;
	    padding-top: 30px;
	    padding-bottom: 30px;
	}
	
	.content {
	    position: relative;
	    display: table;
	    width: 100%;
	    min-height: 100vh;
	}
	.pull-middle {
	    display: table-cell;
	    vertical-align: middle;
	}
	
	.btn {
	    padding-left: 25px;
	    padding-right: 25px;
	}
	.btn-circle {
	    border-radius: 20px;
	}
	
	.input-group input {
	    border: 0;
	    box-shadow: none;
	    padding-right: 30px;
	}
	.input-group input:focus,
	.input-group input:active {
	    outline: 0;
	    box-shadow: none;
	}
	.input-group-btn:last-child>.btn {
	    z-index: 2;
	    margin-left: -18px;   
	    border-radius: 20px;
	}
	
	.phone {
	    position: relative;
	    max-width: 263px;
	    margin: 0 auto;
	    padding: 65px 15px 55px;
	    border: 2px solid #ddd;
	    border-radius: 20px;
	    background-color: #222;
	    box-shadow: 20px 20px 40px #887;
	}
</style>
</head>
<body>
<div class="wrapper">
<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navigation">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand " href="#">UniversitySystem.com</a>
        </div>
    
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navigation">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">About</a></li>
                <li><a href="#">Features</a></li>
                <li><button type="button" class="btn btn-success navbar-btn btn-circle"  onclick="window.location.href='/student/login'" on="">Sign in</button></li>
            </ul>
        </div>
      </div>
    </nav>
    </div>
<div class="container">
	<div class="div-title">
		<p class="title">Terms & Conditions</p>
	</div>
	<div class="content">
		<div class="time">Effective as of year 2018</div>
		<div class="des">
			<p style="font-size: 16px; font-weight: bold; color: maroon; font-style: italic; text-align: center;">Please read these terms and conditions carefully before registration account in this site</p>
			<div class="terms" style="margin-top: 3%; text-align: justify;">
				By agreeing to these Terms, you also consent to the following policies applicable to, and accessible on, our websites which are incorporated by reference into these Terms: Privacy Policy, Website Terms of Use, and any other policy as is made available on our Websites from time to time ("Policies").
				<h3>Accessing University System</h3>
				<p>
					<b>Acceptance:</b>
					 By clicking on the "Agree" button when signing up for a Course, you agree to be bound by these "Terms", which include, by reference, the Policies.  
				</p>
				<p>
					<b>Accurate information:</b>
					 All information (including personal information) provided to us on registration, on creating an account, or while accessing University System services and Courses, must be true, accurate and complete. You are also responsible for updating us in the event of a change to your information (for example, your name or address). All personal information will be responsibly processed in accordance with our Privacy Policy
				</p>
				<p>
					<b>License:</b>
					 Subject to these Terms, and to the payment of any applicable fees, we grant you a limited, personal, non-exclusive, non-transferable and revocable license to use our services and products, including all services associated with our Courses, and the Online Campus through which Course content is accessed (this "Online Campus" is our student management system that we use to facilitate constructive interaction between University System, students and university staffs). 
				</p>
				<h3>Student conduct</h3>
				<p>
					<b>General rules:</b>
					 You are required and encouraged to contribute to the enhancement of the experience of all students and to encourage enthusiasm for learning, intellectual honesty, vigour in debate, openness and respect for fellow students. All students are required to adhere to the following core principles or general rules (GRs), and by working together, we can continue to foster a respectful learning environment where we improve lives through better education: 
					 <ul>
					 	<li>GR.1: Students must take responsibility for their own learning, while also interacting constructively with their fellow students, Student Success Team, Head Tutor and Tutors.</li>
					 	<li>GR.2: Students must treat all representatives of University System, university representatives collaborating on a Course, their fellow students, and any other person encountered through University System's services ("University System Stakeholders") with dignity and respect.</li>
					 	<li>GR.3: Students must not abuse or otherwise interfere with any University System Staff in any manner which contributes to disrupting their work, or contribute in an intimidating, hostile or demeaning manner. In particular, Students may not contribute any intimidating or hostile comments in relation to any University System Stakeholder's occupation, opinions, race, gender, beliefs or sexual orientation.</li>
					 	<li>GR.4: Assignments and all other academic submissions for assessment must be a Student's own work (except for assignments that explicitly permit collaboration). Students must not plagiarise or engage in any other form of academic dishonesty.</li>
					 	<li>GR.5: Students may not make, distribute, reproduce, copy, or make use of any material in which copyright resides, without the permission of the author(s) or owner(s).</li>
					 	<li>GR.6: Students must comply with any reasonable instruction of a University System representative.</li>
					 </ul>
				</p>
				<p>
					<b>Student rules on academic conduct in relation to plagiarism:</b>
					<ul>
						<li>Students must refrain from dishonest conduct in completing their assignments and must not copy the work of fellow students or published sources.</li>
						<li>Students may not allow fellow students, friends, family members, or any other individuals to complete their academic work on their behalf.</li>
						<li>Each assignment requires you to accept a plagiarism declaration. This declaration is to be taken seriously as each student is accountable for their own work.</li>
						<li>Instances of plagiarism will be investigated by University System and may involve consultation with the relevant university or college collaborating on your Course.</li>
					</ul>
				</p>
			</div>
		</div>
	</div>
</div>
<div class="scroll-top-wrapper ">
  <span class="scroll-top-inner">
    <i class="fa fa-2x fa-arrow-circle-up"></i>
  </span>
</div>
</body>
</html>