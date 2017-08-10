<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

	String message = (String) request.getAttribute("message");

%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Activation</title>
	</head>
	<body>
	
		<p><%= message %>! You'll be redirected in 5 seconds!</p>
	
	</body>
	
	<script type="text/javascript">

		window.setTimeout(redirect, 5000);

		function redirect(){
			window.location.href = "./";	
		}		

	</script>

		
	
</html>