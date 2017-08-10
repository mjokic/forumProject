<%@page import="dao.ReplyDAO"%>
<%@page import="obicne.Reply"%>
<%@page import="dao.TopicDAO"%>
<%@page import="obicne.Forum"%>
<%@page import="obicne.User"%>
<%@page import="obicne.Topic"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
	User user = (User) session.getAttribute("user");
	String username = null;

	String term = (String) request.getAttribute("term");
	ArrayList<Forum> forums = (ArrayList<Forum>) request.getAttribute("forums");
	ArrayList<Topic> topics = (ArrayList<Topic>) request.getAttribute("topics");
	ArrayList<Reply> replies = (ArrayList<Reply>) request.getAttribute("replies");

	ServletContext context = getServletContext();
	ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
	
	if(term == null){
		response.sendRedirect("error.jsp");
		return;
	}


	String logged_out = "";
	String logged_in = "hidden";

	int postsPerPage = 5;
	int uID = 0;
	
	if(user != null){
		uID = user.getId();
		logged_out = "hidden";
		logged_in = "";
		username = user.getUsername();
		postsPerPage = user.getNumberPerPage();

	}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="assets/css/bootstrap.css">
		<!-- <link rel="stylesheet" href="assets/css/simplePagination.css"> -->

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Search - <%= term %></title>

	</head>
	<body>
		
		<!-- MAIN CONTAINER -->
		<div class="container" style="margin-top: 10px">


			<!-- HEADER -->
			<div class="page-header page-heading">
				<ol class="breadcrumb pull-left where-am-i">
					<li class="active"><a href="./Index">Main</a></li>
					<li class="active"><a href="#">Search</a></li>

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


						<button style="float:left; height:34px" type="submit" class="btn btn-default btn-md" id="srch"><span class="glyphicon glyphicon-search"></span></button>
					</form>

		        </div>



				<div class="clearfix"></div>
			</div>
			

			<!-- FORUMS -->
			<% if(forums != null && !forums.isEmpty()){ %>
			<table class="table forum table-hover">

				<thead>
					<tr>
						<th class="cell-stat"></th>
						<th>
							<h3 style="display: inline;">Forums</h3> &nbsp;
						</th>
						<th class="cell-stat text-center hidden-xs hidden-sm white">Topics</th>
						<th class="cell-stat text-center hidden-xs hidden-sm white">Created</th>
						<th class="cell-stat-2x hidden-xs hidden-sm white" >Owner</th>

					</tr>
				</thead>

				<tbody>

					<% 
					for(Forum f : forums){ 
					%>
					<tr class="searchForums">
						<td style="width:1%">
						<% if(f.isLocked()){ %>
							<span class="glyphicon glyphicon-lock" style="color: #337ab7; padding-top:100%;"></span>
						<% } %>
						</td>

						<td>
							<h4><a href="./Forum?id=<%=f.getId() %>"><%= f.getName() %></a><br><small><%= f.getDescription() %></small></h4>
						</td>

						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= TopicDAO.getTopicNumber(f.getId()) %></p></td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= f.getCreationDate() %></p></td>
						<td class="hidden-xs hidden-sm"><a href="Members?id=<%= f.getOwner().getId() %>"><%= f.getOwner().getUsername() %></a></td>
					</tr>
					<%
						}
					%>

				</tbody>


			</table>
			<% } %>
			

			<!-- TOPICS -->
			<% if(topics != null && !topics.isEmpty()){ %>
			<table class="table forum table-hover">

				<thead>
					<tr>
						<th class="cell-stat"></th>
						<th>
							<h3 style="display: inline;">Topics</h3> &nbsp;
						</th>
						<th class="cell-stat text-center hidden-xs hidden-sm white">Replies</th>
						<th class="cell-stat text-center hidden-xs hidden-sm white">Created</th>
						<th class="cell-stat-2x hidden-xs hidden-sm white" >Owner</th>

					</tr>
				</thead>

				<tbody>

					<% 
					for(Topic t : topics){ 
					%>
					<tr class="topicc">
						<td style="width:1%">
						<% if(t.isLocked()){ %>
							<span class="glyphicon glyphicon-lock" style="color: #337ab7; padding-top:100%;"></span>
						<% }
						if(t.isImportant()) {%>
							<span class="glyphicon glyphicon-pushpin" style="color: #337ab7;"></span>
						<% } %>
						</td>
						
						<td>
						<!-- <h1><%= t.getId() %></h1> -->
							<h4><a href="./Topic?id=<%= t.getId() %>&highlight=<%= term %>"><%= t.getName() %></a><br><small><%= t.getDescription() %></small></h4>
						</td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= ReplyDAO.getReplyNumber(t.getId()) %></p></td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= t.getDate() %></p></td>
						<td class="hidden-xs hidden-sm"><a href="Members?id=<%= t.getOwner().getId() %>"><%= t.getOwner().getUsername() %></a><br></td>

						<%
							}
						%>

					</tr>

				</tbody>


			</table>
			<% } %>
			


			<!-- POSTS -->
			<% if(replies != null && !replies.isEmpty()){ %>
			<table class="table forum table-hover">

				<thead>
					<tr>
						<th class="cell-stat"></th>
						<th>
							<h3 style="display: inline;">Replies</h3> &nbsp;
						</th>
						<th class="cell-stat text-center hidden-xs hidden-sm white">Topic</th>
						<th class="cell-stat text-center hidden-xs hidden-sm white">Created</th>
						<th class="cell-stat-2x hidden-xs hidden-sm white" >Owner</th>

					</tr>
				</thead>

				<tbody>

					<% 
					for(Reply r : replies){ 
					%>
					<tr class="topicc">
						<td style="width:1%"></td>
						<td>
							<h4><a href="./Topic?id=<%= r.getTopicId()%>&highlight=<%= term %>#<%= r.getId() %>"><%= r.getContent().substring(0, 10) + "..." %></a></h4>
						</td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= TopicDAO.getTopic(r.getTopicId(), users).getName() %></p></td>
						<td class="text-center hidden-xs hidden-sm"><p style="color: #4a79bf;"><%= r.getDate() %></p></td>
						<td class="hidden-xs hidden-sm"><a href="Members?id=<%= r.getOwner().getId() %>"><%= r.getOwner().getUsername() %></a><br></td>

						<%
							}
						%>

					</tr>

				</tbody>


			</table>
			<% } %>
			

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
		<!-- <script src="assets/js/jquery.simplePagination.js" type="text/javascript"></script> -->
	</body>
</html>