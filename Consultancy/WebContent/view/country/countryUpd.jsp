<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
<head>
<meta charset="UTF-8">
<title>Update Country</title>

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
								<h4>Update Country</h4>
							</div>
							<form action="Country?action=UPD" method="POST"
								class="form-horizontal">
								<input type="hidden" name="id" value="${country.countryId}" />
								

								<div class="form-group form-group-sm">
									<label for="name" class="control-label col-md-4">
										Name</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="name"
											value="${country.name}" placeholder=" Name" required=""
											maxlength="30" />
									</div>
								</div>

								<div class="form-group form-group-sm">
									<label for="description" class="control-label col-md-4">Description
										</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="description"
											value="${country.description}" placeholder="User Name" required=""
											maxlength="150" />
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
				<div>${mensaje}</div>

			</div>
		</div>
	</div>

	<script src="../js/jquery.js" type="text/javascript"></script>
	<script src="../js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../js/contactos.js" type="text/javascript"></script>

</body>
</html>