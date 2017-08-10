<%@page import="obicne.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%

	User user = (User) request.getAttribute("user");
	String username = null;
	
	
	String logged_out = "";
	String logged_in = "hidden";
	String admin_display = "none;";
	String display = "none";

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="assets/css/bootstrap.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Password Reset - ${ user.username }</title>
	</head>
	<body>
		
		<!-- MAIN CONTAINER -->
		<div class="container" style="margin-top: 10px">


			<!-- HEADER -->
			<div class="page-header page-heading">
				<ol class="breadcrumb pull-left where-am-i">
					<li class="active"><a href="#">Main</a></li>
				</ol>
				

				<div class="pull-right" id="logged_out" <%= logged_out %> style="width:100px;">

					<button class="btn-default btn-sm btn-block" id="btn_login">Log In</button>
					<button class="btn-default btn-sm btn-block" id="btn_register">Register</button>
					
				</div>



				<!-- Search box -->
				<div style="width: 200px;">
					<form action="./Search" method="post">
			            <input style="float:right; width:79%;" type="text" class="form-control" placeholder="Search" name="keyword" id="srch-term">

			            <input type="hidden" value="on" name="forum">
			            <input type="hidden" value="on" name="title">
			            <input type="hidden" value="on" name="content">


						<button style="float:left; height:34px" type="submit" class="btn btn-default btn-md" id="srch"><span class="glyphicon glyphicon-search"></span></button>
					</form>

		        </div>



				<div class="clearfix"></div>
			</div>
			

			<!-- ovde main - iznad je sve header -->
			<div class="pass-reset_window">
				<div style="width: 80%; margin: auto; padding:30px">
				
					<p>Please enter new password bellow!</p>
					 <form method="post" action="PasswordReset">
						  
						  <div class="form-group form-group-sm">
						    <label for="pwd">Password:</label>
							<span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
						    <input type="password" class="form-control" id="pwd">
						  </div>

						  <div class="form-group form-group-sm">
						    <label for="pwd2">Confirm Password:</label>
						    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
						    <input type="password" class="form-control" id="pwd2">
						  </div>
						 
						 <input type="hidden" name="userId" id="userId" value="${ user.id }"> 
						 
						 <button type="submit" class="btn btn-default" id="reset_pass">Reset</button>

						 </div>
					</form>
				</div>

				
			</div>


		</div>



		<script src="assets/js/jquery-2.1.0.min.js" type="text/javascript"></script>
		<script src="assets/js/bootstrap-datepicker.js" type="text/javascript"></script>



		
	</body>



	<div class="black_window_container">
		
		<!-- Login WINDOW -->
		<div class="login_window">
			<div style="width: 80%; margin: auto; padding:30px">
			
				<p>Please fill info under to login!</p>
				 <form>
					  <div class="form-group form-group-sm">
					    <label for="email">Username:</label>
					    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
					    <input type="text" class="form-control" id="usernameLog">
					  </div>
					  
					  <div class="form-group form-group-sm">
					    <label for="pwd">Password:</label>
						<span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
					    <input type="password" class="form-control" id="passwordLog">
					  </div>
					  
					 
					  <button type="submit" class="btn btn-default" id="login_btn">Login</button>
					  <button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>

					  <div class="pass-reset">
					  	<button class="fake_link" id="pass-reset-btn" style="color:#337ab7;">Forgot password?</button>
					  </div>
				</form>
			</div>
		</div>


		<!-- REGISTER WINDOW -->
		<div class="register_window">
			<div style="width: 80%; margin: auto; padding:30px">
			
				<p>Please fill info under to register!</p>
				 <form>
					  <div class="form-group form-group-sm">
					    <label for="email">Username:</label>
					    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
					    <input type="text" class="form-control" id="usernameReg">
					  </div>
					  <div class="form-group form-group-sm">
					    <label for="email">Email address:</label>
					    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
					    <input type="email" class="form-control" id="email">
					  </div>
					  <div class="form-group form-group-sm">
					    <label for="pwd">Password:</label>
						<span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
					    <input type="password" class="form-control" id="pwd">
					  </div>
					  
						  <div class="form-group form-group-sm">
					    <label for="pwd">Confirm Password:</label>
					    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
					    <input type="password" class="form-control" id="pwd2">
					  </div>
					  
						  <div class="form-group form-group-sm">
					    <label for="fname">First Name:</label>
					    <input type="text" class="form-control" id="fname">
					  </div>
					  
						  <div class="form-group form-group-sm">
					    <label for="lname">Last Name:</label>
					    <input type="text" class="form-control" id="lname">
					  </div>
					 
					  <button type="submit" class="btn btn-default" id="register_btn">Register</button>
					  <button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>
				</form>
			</div>
		</div>

	
		<!-- ADVANCED SEARCH WINDOW -->
		<div class="advancedSearch_container">
			<div style="width: 80%; margin: auto; padding:30px">
					<p>Advanced Search</p>
					 <form method="post" action="Search">
						<div class="form-group form-group-sm">
							<label for="keyword">Keyword:</label>
							<input type="text" class="form-control" name="keyword" id="keyword">
						</div>


						<div class="checkbox">
							<script type="text/javascript">
								function dateClicked(){

									$(".checkbox .input-group.date.d1").datepicker({
									});
									

									$(".checkbox .input-group.date.d2").datepicker({
									});

								}

								function getDateValues(){
									var dt1 = $(".checkbox .input-group.date.d1").datepicker("getDate");
									var dt2 = $(".checkbox .input-group.date.d2").datepicker("getDate");

									var dt1_year = dt1.getFullYear();
									var dt1_month = dt1.getMonth() + 1;
									var dt1_day = dt1.getDate();

									var dt2_year = dt2.getFullYear();
									var dt2_month = dt2.getMonth() + 1;
									var dt2_day = dt2.getDate();

									var final1 = dt1_year + "-" + dt1_month + "-" + dt1_day;
									var final2 = dt2_year + "-" + dt2_month + "-" + dt2_day;

									$("#date1").val(final1);
									$("#date2").val(final2);
								}
							</script>

							<label><input type="checkbox" id="forumCB" name="forum">Forum</label> &nbsp;
							<label><input type="checkbox" id="titleCB" name="title">Topic</label> &nbsp;
							<label><input type="checkbox" id="contentCB" name="content">Replies</label> &nbsp;
							<label><input type="checkbox" id="ownerCB" name="user">User</label> &nbsp;
							<label><input type="checkbox" id="dateCB" name="date">Date</label> </br></br>

							<div style="float: left;">
								<div class="input-group date d1" style="width: 150px;">
							      <input type="text" class="form-control">
							      	<span class="input-group-addon">
							      		<i class="glyphicon glyphicon-th"></i>
							      	</span>
							    </div>

							</div>


							<div style="float: left; padding-left: 10px;">

							    <div class="input-group date d2" style="width: 150px;">
							      <input type="text" class="form-control">
							      	<span class="input-group-addon">
							      		<i class="glyphicon glyphicon-th"></i>
							      	</span>
							    </div>

							</div>

							<input type="hidden" name="date1" id="date1">
							<input type="hidden" name="date2" id="date2">

						    <script type="text/javascript">
						    	dateClicked();
						    </script>

						</div>


						</br>
						</br>

						<button type="submit" class="btn btn-default" id="searchBtn" onclick="getDateValues()">Search</button>
						<button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>
					</form>
				</div>
		</div>


	</div>


	<!-- NOTIFICATION WINDOW -->
	<div class="notification">

		<!-- error msg -->
		<div class="messageErr">
			
			<span class="glyphicon glyphicon-exclamation-sign mErr" style="font-size: 20px; float:right;"></span>

			<p class="message"></p>

		</div>

		<!-- ok msg -->
		<div class="messageOk" style="display:none;">

			<span class="glyphicon glyphicon-ok-sign mOk" style="font-size: 20px; float:right;"></span>

			<p class="message"></p>


		</div>

	</div>



	<script src="assets/js/bootstrap.js" type="text/javascript"></script>
	<script src="assets/js/moj.js" type="text/javascript"></script>


</html>