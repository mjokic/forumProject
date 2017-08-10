<%@page import="obicne.Mail"%>
<%@page import="java.util.ArrayList"%>
<%@page import="obicne.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	User user = (User) session.getAttribute("user");
	Mail mail = (Mail) request.getAttribute("mail");
	String username = null;

	int uID = 0;
	String logged_out = "";
	String logged_in = "hidden";
	ArrayList<User> users = null;
	if(user != null){
		uID = user.getId();
		logged_out = "hidden";
		logged_in = "";
		username = user.getUsername();
		users = (ArrayList<User>) request.getAttribute("users");
		
	}
	
	if(users == null){
		response.sendRedirect("./Panel");
		return;
	}
	

%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="assets/css/bootstrap.css">
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Panel</title>
	</head>
	<body>
		
		<!-- MAIN CONTAINER -->
		<div class="container" style="margin-top: 10px">
			<!-- HEADER -->
			<div class="page-header page-heading">
				<ol class="breadcrumb pull-left where-am-i">
					<li><a href="./Index">Main</a></li>
					<li class="active"><a href="#">User Panel</a></li>
				</ol>
				<div class="pull-right" id="logged_out" <%= logged_out %>>
					
					<form method="post" action="#">
						<input class="input-sm" type="text" name="username" placeholder="username" id="username">
						<input class="input-sm" type="password" name="password" placeholder="password" id="password">
						<input class="btn-default btn-sm btn-block" type="submit" value="Log In" id="btn_login">
					</form>
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


			

			<div class="panel-group" id="accordion">

				<!-- USER INFO PANEL -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapse1">User Info</a>
						</h4>
					</div>
					
					<div id="collapse1" class="panel-collapse collapse">
						<div class="panel-body">
						
							<!-- CHANGE DETAILS -->
							<div class="user_details well well-sm">

								<fieldset>
									<legend>Change Details</legend>
										<form>
											<div class="form-group form-group-sm">
											    <label for="email">Email address:</label>
											    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
											    <input type="email" class="form-control" id="email" value="${ user.email }">
											</div>
					   						<div class="form-group form-group-sm">
											    <label for="fname">First Name:</label>
											    <input type="text" class="form-control" id="fname" value="${ user.name }">
											</div>
											  
					   						<div class="form-group form-group-sm">
											    <label for="lname">Last Name:</label>
											    <input type="text" class="form-control" id="lname" value="${ user.surname }">
											</div>


											<div class="form-group form-group-sm selectContainer">
												<label>Posts Per Page:</label>
									            <select class="form-control" id="numberPerPage">
									                <option value="5">5</option>
									                <option value="10">10</option>
									                <option value="20">20</option>
									            </select>
									        </div>

										   
											<button type="submit" class="btn btn-default" id="change_details">Save</button>


											
											 
										</form>

								</fieldset>

							</div>


							<!-- CHANGE PASSWORD -->
							<div class="user_password well well-sm">

								<fieldset>
									<legend>Change Password</legend>
										<form>
											<div class="form-group form-group-sm">
											    <label for="pwd">Old Password:</label>
												<span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
											    <input type="password" class="form-control" id="oldPassword">
											 </div>

											<div class="form-group form-group-sm">
											    <label for="pwd">Password:</label>
												<span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
											    <input type="password" class="form-control" id="password1">
											</div>

					  						<div class="form-group form-group-sm">
											    <label for="pwd">Confirm Password:</label>
											    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
											    <input type="password" class="form-control" id="password2">
											</div>
											  
											<button type="submit" class="btn btn-default" id="change_pass">Save</button>
											 
										</form>

								</fieldset>

							</div>


							<!-- CHANGE AVATAR -->
							<div class="user_avat well well-sm">

								<fieldset>
									<legend>Change Avatar</legend>
										<form id="avatar_upload_form">
											<input type="file" class="btn btn-default" id="change_avatar" style="float: left;"/>	  
											<button type="submit" class="btn btn-default" id="save_avatar" style="float: right;">Save</button>
											 
										</form>
										
								</fieldset>

							</div>

						</div>
					</div>
				</div>
				 
				 <%
				 	if(user.getRole().getId() == 1){
				 %>
				 
				<!-- MEMBERS PANEL -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Members</a>
						</h4>
					</div>

					<div id="collapse2" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="user_infos well well-sm">
								


								<table class="table forum">

									<thead>
										<tr>

											<th class="cell-stat text-center hidden-xs hidden-sm black">Avatar</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">Email</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">Username</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">First Name</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">Last Name</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">Register</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">Banned</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">Active</th>
											<th class="cell-stat text-center hidden-xs hidden-sm black">Actions</th>


										</tr>
									</thead>


									<tbody>
										<!-- List all members here -->
										<%
											for(User mem : users){
										%>
										
										<tr>
											
											<td class="text-center"><img src="./assets/images/avatars/<%= mem.getAvatarName() %>" width=30 height=30></td>
											<td class="text-center"><%= mem.getEmail() %></td>
											<td class="text-center"><a style="color:inherit;" href="./Members?id=<%= mem.getId() %>" target="_blank"><%= mem.getUsername() %></a></td>
											<td class="text-center"><%= mem.getName() %></td>
											<td class="text-center"><%= mem.getSurname() %></td>
											<td class="text-center"><%= mem.getDate() %></td>
											<td class="text-center cursor-pointer banBtn" memberId="<%= mem.getId() %>" bannedStatus="<%= mem.isBanned() %>">
												<%= mem.isBanned() %>
											</td>
											<td class="text-center cursor-pointer actBtn" memberId="<%= mem.getId() %>" activeStatus="<%= mem.isActive() %>">
												<%= mem.isActive() %>
											</td>
											<td class="text-center">
												
												<span class="glyphicon glyphicon-pencil cursor-pointer memberEditBtn" id="<%= mem.getId() %>"></span>
												<span class="glyphicon glyphicon-remove cursor-pointer memberDelBtn" id="<%= mem.getId() %>"></span>
												<span class="cursor-pointer memberResetPassBtn" id="<%= mem.getId() %>"><b>&#128273;</b></span>

											</td>
										</tr>
										
										<%
											}
										
										%>
									</tbody>

								</table>



								


							</div>
						</div>
					</div>
				</div>


				<!-- EMAIL OPTIONS -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Mail Options</a>
						</h4>
					</div>

					<div id="collapse3" class="panel-collapse collapse">
						<div class="panel-body">
							<!-- MAIL SETTINGS -->
							<div class="mail_settings well well-sm">
								<fieldset>
									<legend>Change Settings</legend>
										<form>
											<div class="form-group form-group-sm">
											    <label for="host">Host:</label>
											    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:8px;"></span>
											    <input type="text" class="form-control" id="host" value="${ mail.host }">
											</div>
					   						<div class="form-group form-group-sm">
											    <label for="port">Port:</label>
											    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:8px;"></span>
											    <input type="text" class="form-control" id="port" value="${ mail.port }">
											</div>
											  
					   						<div class="form-group form-group-sm">
											    <label for="username">Username:</label>
											    <input type="text" class="form-control" id="mailUsername" value="${ mail.mailUsername }">
											</div>


											<div class="form-group form-group-sm">
											    <label for="password">Password:</label>
											    <input type="password" class="form-control" id="mailPassword" value="${ mail.mailPassword }">
											</div>

										   
											<button type="submit" class="btn btn-default" id="change_settings">Save</button>

											<button type="submit" class="btn btn-default" id="test_settings" style="float:right;">test</button>
											 
										</form>

								</fieldset>
							</div>



							<!-- MAIL CONFIRM MESSAGE SETTINGS -->
							<div class="mail_conf_settings well well-sm">
								<fieldset>
									<legend>Confirmation Message</legend>
										<form>

											<div class="form-group form-group-sm">
											    <label for="subject">Subject:</label>
											    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:8px;"></span>
											    <input type="text" class="form-control" id="subject" value="${ mail.subject }">
											</div>

											<div class="form-group form-group-sm">
											    <label for="message">Message:</label>
											    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:8px;"></span>

											    <textarea class="form-control mailMessage" name="message" id="message">${ mail.content }</textarea>

											</div>
					   						
										   
											<button type="submit" class="btn btn-default" id="change_conf_settings">Save</button>
											
											 
										</form>

								</fieldset>
							</div>



						</div>
					</div>
				</div>



				<%
				 	}
				%>

			</div> 

			


		</div>


		<div class="black_window_container">
		
			<!-- EDIT MEMBER WINDOW -->
			<div class="editMember_container">
				<div style="width: 80%; margin: auto; padding:30px">
						<p>Change data bellow to edit selected member!</p>
						 <form>
							  <div class="form-group form-group-sm">
							    <label for="name">Email:</label>
							    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
							    <input type="text" class="form-control" id="editEmail">
							  </div>

  							  <div class="form-group form-group-sm">
							    <label for="name">Username:</label>
							    <span class="glyphicon glyphicon-asterisk" style="float: right;font-size:10px;"></span>
							    <input type="text" class="form-control" id="editUsername">
							  </div>

							  <div class="form-group form-group-sm">
							    <label style="display: block;">Name:</label>
							    <input id="editfName" style="width:100%;">
							  </div>

							  <div class="form-group form-group-sm">
							    <label style="display: block;">Surname:</label>
							    <input id="editlName" style="width:100%;">
							  </div>


							<div class="form-group form-group-sm selectContainer">
								<label>Role:</label>
					            <select class="form-control" id="memberRole">
					                <option value="1">Administrator</option>
					                <option value="2">Moderator</option>
					                <option value="3">Regular Member</option>
					            </select>
					        </div>

							<input type="hidden" id="memberId" value="">
							  							 
							<button type="submit" class="btn btn-default" id="editMember_btn_edit">Edit</button>
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
		<script src="http://cdn.tinymce.com/4/tinymce.min.js"></script>
		<script>tinymce.init({ selector: ".mailMessage" });</script>
		<script>
			var numPerPage = ${ user.numberPerPage };
			setUpNumPerPage(numPerPage);
		</script>

	</body>
</html>