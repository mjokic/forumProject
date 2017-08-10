<%@page import="obicne.Reply"%>
<%@page import="obicne.Topic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="obicne.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 

	if(request.getAttribute("member") == null){
		response.sendRedirect("./Members");
		return;
	}
	

	User user = (User) session.getAttribute("user");
	String username = null;
	ArrayList<Topic> topics = (ArrayList<Topic>) request.getAttribute("topics");
	ArrayList<Reply> replies = (ArrayList<Reply>) request.getAttribute("replies");

	int uID = 0;
	String logged_out = "";
	String logged_in = "hidden";
	if(user != null){
		uID = user.getId();
		logged_out = "hidden";
		logged_in = "";
		username = user.getUsername();
		
	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<link rel="stylesheet" href="assets/css/bootstrap.css">
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>Members</title>
	</head>
	<body>
		

		<div class="container" style="margin-top: 10px">
			<!-- HEADER -->
			<div class="page-header page-heading">
				<ol class="breadcrumb pull-left where-am-i">
					<li><a href="./Index">Main</a></li>
					<li class="active"><a href="#">Members</a></li>
				</ol>
				<div class="pull-right" id="logged_out" <%= logged_out %> style="width:100px;">

					<button class="btn-default btn-sm btn-block" id="btn_login">Log In</button>
					<button class="btn-default btn-sm btn-block" id="btn_register">Register</button>
					
				</div>
				<div class="pull-right" id="logged_in" <%= logged_in %>>
					<!-- Ako je logovan prikazi ovo... -->
					
					<h1 id="LoggedInUsername"><a style="color:#fff;" href="./Members?id=<%= uID %>"><%= username %></a></h1>
					
					<button class="btn-default btn-sm" id="btn_panel">Panel</button>
					<button class="btn-default btn-sm" id="btn_logout">Logout</button>
				</div>
				<div class="clearfix"></div>
			</div>



			<div class="users">
	
				<div class="memberPrikaz">

					<img src="assets/images/avatars/${ requestScope.member.avatarName }" height="150" width="150" style="float: left;">
					

						<div class="membersInfo_username">
							<center><b>${ requestScope.member.username }</b></center>
							<center><i style="font-size:20px;">(${ requestScope.member.role.name })</i></center>

						</div>

						</br>

						<div style="float: left;">
							<p class="membersInfo_other" style="padding-top: 30px;">email: ${ requestScope.member.email }</p>
							<p class="membersInfo_other">register date: ${ requestScope.member.date }</p>
							<p class="membersInfo_other">banned: ${ requestScope.member.banned }</p>
						</div>

						<div class="memberTopicsRepliesNum">

							<form action="Search" method="post">
								<center>
									<input type="hidden" name="keyword" value="${ requestScope.member.username }">
									<input type="hidden" name="user" value="on">
									<!-- <input type="hidden" name="title" value="on"> -->


								Topics: <input class="fake_link" type="submit" style="padding-bottom: 20px;" value="<%= topics.size() %>">
								</center>
							</form>

							<form action="Search" method="post">
								<center>
									<input type="hidden" name="keyword" value="${ requestScope.member.username }">
									<input type="hidden" name="user" value="on">
									<!-- <input type="hidden" name="content" value="on"> -->


								Replies: <input class="fake_link" type="submit" style="padding-bottom: 20px;" value="<%= replies.size() %>">
								</center>
							</form>


						</div>
	
					</div>
					

				</div>



			</div>

	
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


		<script src="assets/js/jquery-2.1.0.min.js" type="text/javascript"></script>
		<script src="assets/js/bootstrap.js" type="text/javascript"></script>
		<script src="assets/js/moj.js" type="text/javascript"></script>


	</body>
</html>