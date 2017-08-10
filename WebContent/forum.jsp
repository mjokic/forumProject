<%@page import="javax.swing.event.ListSelectionEvent"%>
<%@page import="java.util.List"%>
<%@page import="dao.ReplyDAO"%>
<%@page import="dao.TopicDAO"%>
<%@page import="obicne.Topic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="obicne.Forum"%>
<%@page import="obicne.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<%
	User user = (User) session.getAttribute("user");
	String username = null;
	
	Forum forum = (Forum) request.getAttribute("forum");
	ArrayList<Forum> subForums = (ArrayList<Forum>) request.getAttribute("subForums");
	ArrayList<Topic> topics = (ArrayList<Topic>) request.getAttribute("topics");
	
	if(forum == null){
		response.sendRedirect("./Index");
		return;
	}
	
	
	if(forum.getType().getId() != 1 && user == null){
		request.setAttribute("message", "You're not authorized to see this thread");
		request.getRequestDispatcher("error.jsp").forward(request, response);
		return;
	}
	
	String logged_out = "";
	String logged_in = "hidden";
	String admin_display = "none;";
	String display = "none";

	int postsPerPage = 5;
	int uID = 0;

	if(user != null){
		if(user.getRole().getId() == 1){
			admin_display = "inline;";
		}
		uID = user.getId();
		logged_out = "hidden";
		logged_in = "";
		username = user.getUsername();
		postsPerPage = user.getNumberPerPage();
		display = "inline;";

	}

%>

<!DOCTYPE html>
<html class="full" lang="en">
	<head>
		<link rel="stylesheet" href="assets/css/bootstrap.css">
		<link rel="stylesheet" href="assets/css/simplePagination.css">


		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Forum - <%= forum.getName() %></title>
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
					<li class="active"><a href="#"><%= forum.getName() %></a></li>
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

				<!-- Search box -->
				<div style="width: 200px;">
					<form action="./Search" method="post">
			            <input style="float:right; width:79%;" type="text" class="form-control" placeholder="Search" name="keyword" id="srch-term">

			            <input type="hidden" value="on" name="forum">
			            <input type="hidden" value="on" name="title">
			            <input type="hidden" value="on" name="content">


						<button style="float:left;" type="submit" class="btn btn-default btn-md" id="srch"><span class="glyphicon glyphicon-search"></span></button>
					</form>

		        </div>


				<div class="clearfix"></div>
			</div>
			
			<!-- posts per page -->
			<input type="hidden" id="postsPerPage" value="<%= postsPerPage %>">

			
			<!-- FORUMS -->
			<table class="table forum table-hover">
				
				<thead>
					<tr>
						<th class="cell-stat"></th>
						<th>
							<!-- <h3><%= forum.getName() %></h3> -->
							<%
								if (subForums.isEmpty()){
									
							%>
								<h3 style="display: inline;">No SubForums Here</h3> &nbsp;
								<% if(!forum.isLocked()){ %>
									<% if(user != null && user.getRole().getId() == 1){ %>
										<span class="glyphicon glyphicon-plus white add_forum" style="cursor: pointer; display: <%= display %>;"><span>
									<% } %>	
								<% } %>								
							<%
								}else{
							%>
								<h3 style="display: inline;">SubForums</h3> &nbsp;
								<% if(!forum.isLocked()){ %>
									<% if(user != null && user.getRole().getId() == 1){ %>
										<span class="glyphicon glyphicon-plus white add_forum" style="cursor: pointer; display: <%= display %>;"><span>
									<% } %>
								<% } %>								
								</th>
								<th class="cell-stat text-center hidden-xs hidden-sm white">Topics</th>
								<th class="cell-stat text-center hidden-xs hidden-sm white">Created</th>
								<th class="cell-stat-2x hidden-xs hidden-sm white">Owner</th>
								<% if(user != null && user.getRole().getId() == 1){ %>
									<th class="cell-stat-2x white" >Actions</th>
								<% } %>

							<%
								}
							%>
							
					</tr>
				</thead>

				<tbody>
					
				</tbody>
				

				<!-- SUBFORUMS -->
				<tbody>
					
					<%
						for(Forum subForum : subForums){
					%>
					
					<tr class="subforums">
						<td style="width:1%">
						<% if(subForum.isLocked()){ %>
							<span class="glyphicon glyphicon-lock" style="color: #337ab7; padding-top:100%;"></span>
						<% } %>
						</td>
						<td class="canClick cursor-pointer" ajdi="<%=subForum.getId() %>">
							<h4><a href="./Forum?id=<%= subForum.getId() %>"><%= subForum.getName() %></a><br><small><%= subForum.getDescription() %></small></h4>
						</td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= TopicDAO.getTopicNumber(subForum.getId()) %></p></td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= subForum.getCreationDate() %></p></td>
						<td class="hidden-xs hidden-sm"><a href="Members?id=<%= subForum.getOwner().getId() %>"><%= subForum.getOwner().getUsername() %></a><br></td>

						<% if(user != null && user.getRole().getId() == 1){ %>
						
						<td style="width:10%">
							<span class="glyphicon glyphicon-pencil cursor-pointer forumEditBtn" style="color: #337ab7;" forumId="<%= subForum.getId() %>"></span>
							&nbsp;
							<span class="glyphicon glyphicon-lock cursor-pointer forumLockBtn" style="color: #337ab7;" forumId="<%= subForum.getId() %>" locked="<%= subForum.isLocked() %>"></span>
							&nbsp;
							<span class="glyphicon glyphicon-remove cursor-pointer forumRemoveBtn" style="color: #337ab7;" forumId="<%= subForum.getId() %>"></span>
							&nbsp;

						</td>
						
						<%} %>

					</tr>
					<%
						}
					%>
					
				</tbody>

				<thead>
					<tr>
						
					<th style="border: none"></th>
					<th style="border: none">
						
						<!-- PAGINATION -->
						<div class="page-nav nav-subForum" style="margin-bottom:10px; display: <%= display %>"></div>

					</th>

					</tr>
				</thead>



				<!-- THREADS -->
				<thead>
					<tr>
						<th class="cell-stat"></th>
						<th>
							<%
								if (topics.isEmpty()){
							%>
							<h3 style="display: inline;">No Topics Here</h3> &nbsp;
								<% if(!forum.isLocked() && user != null && !user.isBanned()){ %>
									<span class="glyphicon glyphicon-plus white add_topic" style="cursor: pointer; display: <%= display %>;"><span>
								<% } %>
							<%
								}else{
							%>
							<h3 style="display: inline;">Topics</h3> &nbsp;
								<% if(!forum.isLocked() && user != null && !user.isBanned()){ %>
									<span class="glyphicon glyphicon-plus white add_topic" style="cursor: pointer; display: <%= display %>;"><span>
								<% } %>
							</th>

							
							</th>
							<th class="cell-stat text-center hidden-xs hidden-sm white">Replies</th>
							<th class="cell-stat text-center hidden-xs hidden-sm white">Created</th>
							<th class="cell-stat-2x hidden-xs hidden-sm white">Owner</th>
							<% if(user != null && user.getRole().getId() != 3){ %>
								<th class="cell-stat-2x white" >Actions</th>
							<% } %>
							<%
								}
							%>
					</tr>
				</thead>

				<tbody>
					
					<!-- Important Topics -->
					<%
						for(int j = topics.size() -1; j >= 0; j--){
							Topic iTopic = topics.get(j);
							if(iTopic.isImportant() == true){
					%>
					
					<p>
						<a name="<%= iTopic.getId() %>">
					</p>
					<tr class="topicc">
						<td style="width:1%">
						<% if(iTopic.isLocked()){ %>
							<span class="glyphicon glyphicon-lock" style="color: #337ab7; padding-top:100%;"></span>
						<% }
						if(iTopic.isImportant()) {%>
							<span class="glyphicon glyphicon-pushpin" style="color: #337ab7;"></span>
						<% } %>
						</td>
						<td class="canClick2 cursor-pointer" ajdi="<%=iTopic.getId() %>">
							<h4><a href="./Topic?id=<%= iTopic.getId() %>"><%= iTopic.getName() %></a><br><small><%= iTopic.getDescription() %></small></h4>
						</td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= ReplyDAO.getReplyNumber(iTopic.getId()) %></p></td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= iTopic.getDate() %></p></td>
						<td class="hidden-xs hidden-sm"><a href="Members?id=<%= iTopic.getOwner().getId() %>"><%= iTopic.getOwner().getUsername() %></a><br></td>

						<% if(user != null && user.getRole().getId() != 3){ %>
						<td style="width:10%">

							<span class="glyphicon glyphicon-lock cursor-pointer topicLockBtn" style="color: #337ab7;" topicId="<%= iTopic.getId() %>" locked="<%= iTopic.isLocked() %>"></span>
							&nbsp;
							<span class="glyphicon glyphicon-remove cursor-pointer topicRemoveBtn" style="color: #337ab7;" topicId="<%= iTopic.getId() %>"></span>
							&nbsp;
							<span class="glyphicon glyphicon-bookmark cursor-pointer topicBookmarkBtn" style="color: #337ab7;" topicId="<%= iTopic.getId() %>" bookmarked="<%= iTopic.isImportant() %>"></span>
							&nbsp;

						</td>
						<% 
							}
						}
						%>

					</tr>
						</a>

					<%
						}
					%>


					<!-- Non-Important Topics -->
					<%
						for(int i = topics.size() -1; i >= 0; i--){
							Topic topic = topics.get(i);
							if(topic.isImportant() == false){
					%>
					
					<p>
						<a name="<%= topic.getId() %>">
					</p>
					<tr class="topicc">
						<td style="width:1%">
						<% if(topic.isLocked()){ %>
							<span class="glyphicon glyphicon-lock" style="color: #337ab7; padding-top:100%;"></span>
						<% } %>
						</td>
						<td class="canClick2 cursor-pointer" ajdi="<%=topic.getId() %>">
							<h4><a href="./Topic?id=<%= topic.getId() %>"><%= topic.getName() %></a><br><small><%= topic.getDescription() %></small></h4>
						</td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= ReplyDAO.getReplyNumber(topic.getId()) %></p></td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= topic.getDate() %></p></td>
						<td class="hidden-xs hidden-sm"><a href="Members?id=<%= topic.getOwner().getId() %>"><%= topic.getOwner().getUsername() %></a><br></td>

						<% if(user != null && user.getRole().getId() != 3){ %>
						<td style="width:10%">
							<!-- <span class="glyphicon glyphicon-pencil cursor-pointer topicEditBtn" style="color: #337ab7;" forumId="<%= topic.getId() %>"></span> -->
							<!-- &nbsp; -->
							<span class="glyphicon glyphicon-lock cursor-pointer topicLockBtn" style="color: #337ab7;" topicId="<%= topic.getId() %>" locked="<%= topic.isLocked() %>"></span>
							&nbsp;
							<span class="glyphicon glyphicon-remove cursor-pointer topicRemoveBtn" style="color: #337ab7;" topicId="<%= topic.getId() %>"></span>
							&nbsp;
							<span class="glyphicon glyphicon-bookmark cursor-pointer topicBookmarkBtn" style="color: #337ab7;" topicId="<%= topic.getId() %>" bookmarked="<%= topic.isImportant() %>"></span>
							&nbsp;

						</td>
						<% 
							}
						}
						%>

					</tr>

						</a>

					<%
						}
					%>
					
				</tbody>
				
				<thead>
					<tr>
						
					<th style="border: none"></th>
					<th style="border: none">
						
						<!-- PAGINATION -->
						<div class="page-nav nav-topic" style="margin-bottom:10px; display: <%= display %>"></div>

					</th>

					</tr>
				</thead>


			</table>				
				
		</div>


		<div class="black_window_container">
			
			<!-- ADD TOPIC WINDOW -->
			<div class="addTopic_container">
				<div style="width: 80%; margin: auto; padding:10px">
				
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
							<textarea class="thisOne" style="width: 100%;" id="topicText" name="topicText"></textarea>
						</div>

						<input type="hidden" id="userId" value="${user.id}">
						<input type="hidden" id="forumId" value="${forum.id}">
						 
						<button type="submit" class="btn btn-default" id="addTopic_btn_add">Create</button>
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


			<!-- ADD FORUM WINDOW -->
			<div class="addForum_container">
				<div style="width: 80%; margin: auto; padding:30px">
						<p>Fill data bellow to add new forum!</p>
						 <form>
							  <div class="form-group form-group-sm">
							    <label for="name">Name:</label>
							    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
							    <input type="text" class="form-control" id="name">
							  </div>

							  <div class="form-group form-group-sm">
							    <label style="display: block;">Description:</label>
							    <textarea id="description" style="width:100%;"></textarea>
							  </div>
							  

							  <input type="hidden" id="userId" value="${user.id}">
							  <input type="hidden" id="typeId" value="">
							  <input type="hidden" id="parentId" value="${forum.id}">
							 
							  <button type="submit" class="btn btn-default" id="addForum_btn_add">Add</button>
							  <button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>
						</form>
					</div>
			</div>


			<!-- EDIT FORUM WINDOW -->
			<div class="editForum_container">
				<div style="width: 80%; margin: auto; padding:30px">
						<p>Change data bellow to edit selected forum!</p>
						 <form>
							  <div class="form-group form-group-sm">
							    <label for="name">Name:</label>
							    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
							    <input type="text" class="form-control" id="editName">
							  </div>

							  <div class="form-group form-group-sm">
							    <label style="display: block;">Description:</label>
							    <textarea id="editDescription" style="width:100%;"></textarea>
							  </div>

							  <input type="hidden" id="forumId" value="">
							  							 
							  <button type="submit" class="btn btn-default" id="editForum_btn_edit">Edit</button>
							  <button type="submit" class="btn btn-default btn_black_win_container_close">Close</button>
						</form>
					</div>
			</div>


			<script src="assets/js/jquery-2.1.0.min.js" type="text/javascript"></script>
			<script src="assets/js/bootstrap-datepicker.js" type="text/javascript"></script>

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
		<script src="assets/js/jquery.simplePagination.js" type="text/javascript"></script>
		<script src="http://cdn.tinymce.com/4/tinymce.min.js"></script>
		<script>tinymce.init({ selector:'textarea.thisOne' });</script>

	</body>
</html>