<%@page import="obicne.Reply"%>
<%@page import="obicne.Forum"%>
<%@page import="obicne.Topic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="obicne.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	User user = (User) session.getAttribute("user");
	int userId = 0;
	String username = null;
	
	Forum forum = (Forum) request.getAttribute("forum");
	
	if(forum == null){
		response.sendRedirect("./Index");
		return;
	}
	
	
	Topic topic = (Topic) request.getAttribute("topic");
	ArrayList<Reply> replies = (ArrayList<Reply>) request.getAttribute("replies");
	User owner = topic.getOwner();
	int topicId = topic.getId();
	
	
	if(forum.getType().getId() != 1 && (user == null || user.isBanned())){
		request.setAttribute("message", "You're not authorized to see this thread");
		request.getRequestDispatcher("error.jsp").forward(request, response);
		return;
	}
	int uID = 0;
	
	String logged_out = "";
	String logged_in = "hidden";
	String display = "none";
	int postsPerPage = 5;
	if(user != null){
		uID = user.getId();
		logged_out = "hidden";
		logged_in = "";
		username = user.getUsername();
		userId = user.getId();
		display = "block";
		postsPerPage = user.getNumberPerPage();
		
	}

	if(topic.isLocked()) display = "none";
%>


<!DOCTYPE html>
<html class="full" lang="en">
	<head>
		<link rel="stylesheet" href="assets/css/bootstrap.css">
		<link rel="stylesheet" href="assets/css/simplePagination.css">

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Topic - <%= topic.getName() %></title>
	</head>
	<body>
	
		<!-- MAIN CONTAINER -->
		<div class="container" style="margin-top: 10px">
		
			<!-- HEADER -->
			<div class="page-header page-heading">
				<ol class="breadcrumb pull-left where-am-i">
					<li><a href="./Index">Main</a></li>
					<% if(forum.getParentForumId() != 0){ %>
					<li><a href="./Forum?id=<%= forum.getParentForumId() %>">..</a></li>
					<%} %>
					<li class="active"><a href="./Forum?id=<%= forum.getId()%>"><%= forum.getName() %></a></li>
					<li class="active"><a href="#"><%= topic.getName() %></a></li>
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
			
			
			
			<!-- PAGINATION -->
			<div class="page-nav" style="margin-bottom:10px; width:90%; display:inline-block;"></div>
			<!-- posts per page -->
			<input type="hidden" id="postsPerPage" value="<%= postsPerPage %>">

			
			<% if(user != null && !topic.isLocked()){ %>
			<button class="btn-default btn-sm" style="float: right;" id="add_reply_normal">
				New Reply
			</button>
			<% } %>

			<!-- TOPIC -->
			<!-- Topic Header -->
			<div class="topic">
				<div class="topic_head">
					
					<div class="user_info">

						<div class="user_avatar">
							<img src="assets/images/avatars/<%= owner.getAvatarName() %>" alt="defult-avatar" height="100" width="100">
						</div>

						<div class="about_user">
							<h3 style="display: inline-block">
							<a id="topic_username" href="./Members?id=<%= owner.getId() %>"><%= owner.getUsername() %></a>
							</h3>
							<h5 id="topic_role">( <%= owner.getRole().getName() %> )</h5>
					
						</div>
						
					</div>
					
					<div class="topic_info">
					
						<%= topic.getDate() %>
					
					</div>

					</br>

					<% if(user != null && !topic.isLocked() &&
						(user.getRole().getId() == 1 || user.getRole().getId() == 2 || 
						(topic.getOwner().getId() == user.getId()))){ 
					%>
					<div class="topic_actions">
						<span class="glyphicon glyphicon-edit cursor-pointer topicEditBtn"></span> &nbsp;
						<span class="glyphicon glyphicon-lock cursor-pointer topicLockBtn" topicId="<%= topic.getId() %>" locked="<%= topic.isLocked() %>"></span>
					</div>
					<% }  %>
				
				</div>

				<!-- Topic Body -->
				<div class="topic_body">

					<p style="padding-left: 10px; padding-right: 10px;"><%= topic.getContent() %></p>
					
				</div>
				
			</div>
				
			<!-- Posts on this topic -->
			<%
				for(Reply reply : replies){
			%>
			
			<p>
				<a name="<%= reply.getId() %>">
			</p>
			<div class="reply">
				<div class="reply_head">
					<div class="user_info">
						<div class="user_avatar">
							<img src="assets/images/avatars/<%= reply.getOwner().getAvatarName() %>" alt="defult-avatar" height=60 width="60">
						</div>

						<div class="about_user">
							<h3 style="display: inline-block">
							<a id="topic_username" href="./Members?id=<%= reply.getOwner().getId() %>"><%= reply.getOwner().getUsername() %></a>
							</h3>
							<h5 id="topic_role">( <%= reply.getOwner().getRole().getName() %> )</h5>
						</div>
						
					</div>
					
					<div class="topic_info">
						<%= reply.getDate() %>
					</div>

				</div>
				
				<div class="reply_body">
				
					<div style="padding-left: 10px; padding-right: 10px;">
						<%= reply.getContent() %>

					</div>

					<% 
						if(user != null && !topic.isLocked() && ((user.getRole().getId() == 1 || user.getRole().getId() == 2) || user.getId() == reply.getOwner().getId())){
					%>
					<div class="reply_actions">
						<div style="float:right;">
							<span class="glyphicon glyphicon-edit cursor-pointer replyEditBtn" replyId="<%= reply.getId() %>"></span> &nbsp;
							<span class="glyphicon glyphicon-remove cursor-pointer replyRemoveBtn" replyId="<%= reply.getId() %>"></span>
						</div>
					</div>
					<% } %>

				</div>

			</div>
			</a>

			<%
				}
			%>
			
			
			<button class="btn btn-default btn-sm colButton" data-toggle="collapse" data-target="#fast_reply" style="display: <%= display %>">
			<span class="glyphicon glyphicon-chevron-down"></span>
			</button>

			<!-- Fast Reply -->
			<div id="fast_reply" class="fast_reply_box collapse">
			
				<textarea id="text_box" rows="4" cols="80" tabindex="1"
				placeholder="Type your reply here..."></textarea>
				
				<input type="hidden" id="userId" value="<%= userId %>">
				<input type="hidden" id="topicId" value="<%= topicId %>">
				
				<div class="manage_fast_reply">
					<button class="btn-sm btn-block" id="btn_post" style="height:100%">Post</button>
				</div>
			
			</div>




			<!-- PAGINATION -->
			<div class="page-nav" style="margin-top:10px;"></div>


		<div class="black_window_container">
			

			<!-- EDIT TOPIC WINDOW -->
			<div class="addTopic_container">
				<div style="width: 80%; margin: auto; padding:30px">
				
					 <form>
						<div class="form-group form-group-sm">
							<label>Title:</label>
						    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
						    <input type="text" class="form-control" id="topicTitle">
						</div>

						<div class="form-group form-group-sm">
							<label>Description:</label>
						    <input type="text" class="form-control" id="topicDesc">
						</div>

						<div class="form-group form-group-sm">
							<label>Text:</label>
						    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
							<textarea class="topicContent" style="width: 100%;" id="topicText"></textarea>
						</div>

						<button type="submit" class="btn btn-default" id="editTopic_btn_edit">Edit</button>
						<button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>
					</form>
				</div>
			</div>


			<!-- ADD REPLY WINDOW -->
			<div class="reply_window">
				<div style="margin: auto; padding:30px">
				
					 <form>
						<div class="form-group form-group-sm">
							<label>Text:</label>
						    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
							<textarea class="topicContent" id="topicCont" style="width: 100%; height:200px"></textarea>
						</div>

						 
						<button type="submit" class="btn btn-default" id="addReply_btn">Create</button>
						<button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>
					</form>
				</div>
			</div>


			<!-- EDIT REPLY WINDOW -->
			<div class="reply_windowEdit">
				<div style="margin: auto; padding:30px">
				
					 <form>
						<div class="form-group form-group-sm">
							<label>Text:</label>
						    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
							<textarea class="topicContent" id="editTopicContent" style="width: 100%; height:200px"></textarea>
						</div>

						<input type="hidden" id="replyId" value="">
						 
						<button type="submit" class="btn btn-default" id="editReply_btn">Edit</button>
						<button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>
					</form>
				</div>
			</div>


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
		<script src="assets/js/jquery.simplePagination.js" type="text/javascript"></script>
		<script src="http://cdn.tinymce.com/4/tinymce.min.js"></script>
		<script>tinymce.init({ selector: ".topicContent" });</script>

	</body>

</html>