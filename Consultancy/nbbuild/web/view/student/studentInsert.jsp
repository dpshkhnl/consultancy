<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
<head>
<meta charset="UTF-8">
<title>Insert Student</title>

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
		<br/>
		<br/>
		<jsp:include page="/menu.jsp" />
			<div class="container">

				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="page-header"
								style="text-align: center; margin-top: 5px">
								<h3>Insert Student</h3>
							</div>
							<form action="Student?action=INS" method="POST"
								class="form-horizontal">
								<input type="hidden" name="accion" value="INS" />



								<div class="form-group form-group-sm">
									<label for="name" class="control-label col-md-4">Full
										Name</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="name"
											value="${student.name}" placeholder="Full Name" required=""
											maxlength="30" />
									</div>
								</div>

								<div class="form-group form-group-sm">
									<label for="address" class="control-label col-md-4">Address
										</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="address"
											value="${student.address}" placeholder="Address" required=""
											/>
									</div>
								</div>
								<div class="form-group form-group-sm">
									<label for="pName" class="control-label col-md-4">Parent Name
									</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="pName"
											value="${student.parentName}" placeholder="Parent's Name" required=""
											/>
									</div>
								</div>


								<%-- <div class="form-group form-group-sm">
									<label for="role" class="control-label col-md-4">Role</label>
									<div class="col-md-8">
										<select class="form-control" name="role" value="${student.role}">
											<option value="Admin">Admin</option>
											<option value="Consultancy">Consultancy</option>
											<option value="Student">Student</option>
										</select> 
									</div>
								</div> --%>
								
								<div class="form-group form-group-sm">
									<label for="email" class="control-label col-md-4">Email</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="email"
											value="${student.email}" placeholder="Email" maxlength="30" />
									</div>
								</div>
								<div class="form-group form-group-sm">
									<label for="contact" class="control-label col-md-4">Contact
										No</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="contact"
											value="${student.phone}" placeholder="Contact No"
											maxlength="30" />
									</div>
								</div>


								<hr />
								<div class="form-group">
									<div class="col-md-4 col-sm-offset-2">
										<button type="submit" class="btn btn-info">Save</button>
									</div>
									
								</div>

							</form>
						</div>
					</div>
				</div>
				<br />
				<%-- para mensajes  --%>
			<%-- 	<div>${message}</div> --%>

			</div>
		</div>
	</div>

	<script src="../js/jquery.js" type="text/javascript"></script>
	<script src="../js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../js/contactos.js" type="text/javascript"></script>

</body>
</html>