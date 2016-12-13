<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<div id="container" style="margin: auto; width: 80%">

		<% if(request.getSession().getAttribute("role")!=null)
 			{ 
					
   				String p = (String)request.getSession().getAttribute("role");
   				if (p.equals("Admin")){	
   				%>


		<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header"></div>
			<ul class="nav navbar-nav">
				<li ><a href="Login?action=WELCOME">Home</a></li>
				<li ><a href="User?action=QRY">User</a></li>
				<li  ><a href="Student?action=QRY">Student</a></li>
				<li ><a href="Consultancy?action=QRY">Consultancy</a></li>
				<li ><a href="Country?action=QRY">Country</a></li>
				<li ><a href="University?action=QRY">University</a></li>
				<li style="float: right;"><a href="Login?action=LOGOUT">Logout</a></li>
			</ul>
		</div>
		</nav>

		<%}else if (p.equals("Consultancy")){	
				%>

		<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header"></div>
			<ul class="nav navbar-nav">
				<li  class="active"><a href="Login?action=WELCOME">Home</a></li>
				<li  class><a href="Student?action=QRY">Student</a></li>
				<li ><a href="University?action=QRY">University</a></li>
				<li ><a href="Consultancy?action=QRY">Profile</a></li>
				<li style="float: right;"><a href="Login?action=LOGOUT">Logout</a></li>
			</ul>
		</div>
		</nav>


		<%}
		else if (p.equals("Student")){	
   	   				%>

		<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header"></div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="Login?action=WELCOME">Home</a></li>
				<li  class><a href="Student?action=SEARCH">Search Consultancy</a></li>
				<li  class><a href="University?action=QRY">Universities</a></li>
				<li ><a href="Student?action=QRY">Profile</a></li>
				<li  style="float: right;"><a href="Login?action=LOGOUT">Logout</a></li>
			</ul>
		</div>
		</nav>


		<%}}%>}%>}%>


	</div>

</body>
</html>