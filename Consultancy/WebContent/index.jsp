<%-- <% response.sendRedirect("User?action=QRY"); %> --%>
<%-- <% response.sendRedirect("Country?action=QRY"); %> --%>
<%-- <% response.sendRedirect("University?action=QRY"); %> --%>
<% response.sendRedirect("Login?action=WELCOME"); %>
<%-- <% response.sendRedirect("Consultancy?action=QRY"); %> --%>


<%-- <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
<head>
<meta charset="UTF-8">
<title>Login</title>

<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	type="text/css" />
<link href="icons/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<br />
	<br />
	<br />
	<div id="m_main">

		<div id="m_body">
			<div class="container">

				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="page-header"
								style="text-align: center; margin-top: 5px">
								<h3>Login</h3>
							</div>
							<form action="Login?action=LOGIN" method="POST"
								class="form-horizontal">
								<input type="hidden" name="accion" value="INS" />


								<div class="form-group form-group-sm">
									<label for="username" class="control-label col-md-4">User
										Name</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="userName"
											value="${user.userName}" placeholder="User Name" required=""
											maxlength="9" />
									</div>
								</div>
								<div class="form-group form-group-sm">
									<label for="password" class="control-label col-md-4">Password
									</label>
									<div class="col-md-8">
										<input type="password" class="form-control" name="password"
											value="${user.password}" placeholder="User Name" required=""
											maxlength="9" />
									</div>
								</div>


								<div class="form-group form-group-sm">
									<label for="role" class="control-label col-md-4">Role</label>
									<div class="col-md-8">
										<select class="form-control" name="role" value="${user.role}">
											<option value="Admin">Admin</option>
											<option value="Consultancy">Consultancy</option>
											<option value="Student">Student</option>
										</select> 
									</div>
								</div>
								


								<hr />
								<div class="form-group">
									<div class="col-md-4 col-sm-offset-2">
										<button type="submit" class="btn btn-info">Login</button>
									</div>
									
								</div>

							</form>
						</div>
					</div>
				</div>
				<br />
				para mensajes 
				<div>${message}</div>

			</div>
		</div>
	</div>

	<script src="../js/jquery.js" type="text/javascript"></script>
	<script src="../js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../js/contactos.js" type="text/javascript"></script>

</body>
</html> --%>