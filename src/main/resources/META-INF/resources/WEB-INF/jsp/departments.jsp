<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet prefetch" href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css">
<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>

<title>Department Manager</title>
<style type="text/css">
.container {
	margin-top: 5%;
}

/* Dates */
.agenda .agenda-date { width: 170px; }
.agenda .agenda-date .dayofmonth {
  width: 40px;
  font-size: 36px;
  line-height: 36px;
  float: left;
  text-align: right;
  margin-right: 10px; 
}
.agenda .agenda-date .shortdate {
  font-size: 0.75em; 
}
.navbar-nav.navbar-right:last-child {
		margin-right: 3%;
	}

/* Times */
.agenda .agenda-time { width: 140px; } 
.product-bread-a{
    -webkit-border-radius: 0px !important;
    -moz-border-radius: 0px !important;
    border-radius: 0px !important;
    
}
.product-bread-a a{
    color:#718ac3;
}
table {
	margin-left: 2%;
	width: 95% !important;
}
</style>
</head>
<body>
<div id="navbar" class="navbar navbar-default navbar-fixed-top navbar-inverse">
</div>
<script type="text/javascript">
	$( "#navbar" ).load( "/navbar.html" );
</script>
<div class="container">
	<div class="row">   
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="list-group product-bread-a">
		  <a href="#" class="list-group-item product-bread-a" style="background:#dededd"><i class="fa fa-chevron-left"></i><i class="fa fa-chevron-left"></i>  Semua Kategori</a>
		</div> 
	    <table class="table table-striped">
	        <thead>
	          <tr>
	              <th>2015 IRS Forum</th>        
	            </tr>
	          </thead>
	          <tbody>
	           <tr>
	           	<td><strong>Username</strong></td>
	            <td><strong>Washington DC</strong><br/>National Harbor, MD 20745</td>
	            <td><strong>Post time</strong><br/>July 7 - 9, 2015</td>
	            <td><strong>Close date</strong>
					<div id="datepicker" class="input-group date" data-date-format="dd-mm-yyyy"> 
						<input class="form-control" readonly="" type="text"> 
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div> 
					<script type="text/javascript">
						$(function () {  
						$("#datepicker").datepicker({         
						autoclose: true,         
						todayHighlight: true 
						}).datepicker('update', new Date());
						});
					</script>
				</td>
	            <td><strong>Accept</strong><br/><input type="checkbox" value="accept"/>     Accept</td>
	            <td><strong>Denied</strong><br/><input type="checkbox" value="denied"/>     Denied</td>
	          </tr>       
	          <!--Denbver-->
	        </tbody>
	      </table>
     </div>

    </div>

   </div>
		
      
</body>
</html>