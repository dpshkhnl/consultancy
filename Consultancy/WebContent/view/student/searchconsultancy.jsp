<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Consultancy Management</title>

        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="datatable/jquery-ui.css" rel="stylesheet" type="text/css" />
        <link href="datatable/dataTables.jqueryui.min.css" rel="stylesheet"
              type="text/css" />
        <link href="css/estilos.css" rel="stylesheet" type="text/css" />

    </head>
    <body>

        <div id="m_main">

            <div id="m_body">
                <br /> <br />
                <jsp:include page="/menu.jsp" />
                <div id="container" style="margin: auto; width: 80%">

                    <h3 class="titulo">Search Consultancy</h3>

                    <form action="Student?action=SEARCH" method="POST"
                          style="color: white; padding-left: 100px" class="form-inline">
                        <div class="form-group">
                            <label>Country</label> <select class="form-control" id="country" onchange="changeFunc();"
                                                           style="width: 180px" name="country">
                                <option value="0" >Select</option>
                                <c:forEach items="${countries}" var="country">
                                    <option value="${country.countryId}">${country.name}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="form-group">
                            <label for="pwd">University:</label> <select class="form-control" id="university"
                                                                         style="width: 180px" name="university">
                                <option value="0">Select</option>
                                <c:forEach items="${universities}" var="university">
                                    <option value="${university.uniId}">${university.name}</option>
                                </c:forEach>

                            </select>
                        </div>

                        <button type="submit" class="btn btn-default">Search</button>
                    </form>
                    <div id="demo_jui">
                        <table id="example" class="display table-responsive" width="100%"
                               cellspacing="0">
                            <thead>
                                <tr>
                                    <th style="display: none;"><u>id</u></th>
                                    <th style="text-align: center"><u>Consultancy Name</u></th>
                                    <th style="text-align: center"><u>Address</u></th>
                                    <th style="text-align: center"><u>Email</u></th>
                                    <th style="text-align: center"><u>Phone</u></th>
                                    <th style="text-align: center"><u>Website</u></th>
                                    <th style="text-align: center"><u>Contact Person</u></th>
                                    <th></th>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="n" items="${lista}">
                                    <tr>
                                        <td style="display: none;">${n.id}</td>
                                        <td style="text-align: center">${n.name}</td>
                                        <td style="text-align: center">${n.address}</td>

                                        <td style="text-align: center">${n.email}</td>
                                        <td style="text-align: center">${n.phone}</td>
                                        <td style="text-align: center">${n.website}</td>
                                        <td style="text-align: center">${n.contactPerson}</td>
                                        <td style="text-align: center"><a class="btn btn-warning" title="Add to List"
                                                                          href="Consultancy?action=FAV&id=${n.id}" role="button"> <span class="glyphicon glyphicon-plus"></span></a></td>

                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <br />
                <%-- para mensajes  --%>
                <div>${message}</div>

            </div>
            <!--/div-->

        </div>

        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <script src="datatable/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="datatable/dataTables.jqueryui.min.js"
        type="text/javascript"></script>

        <!--script src="../../datatableBstp/js/dataTables.bootstrap4.min.js" type="text/javascript"></script-->

        <script type="text/javascript">
                                $(document)
                                        .ready(
                                                function () {
                                                    $('#example')
                                                            .dataTable(
                                                                    {
                                                                        "lengthMenu": [7, 10, 25, 50,
                                                                            75, 100],
                                                                        "language": {
                                                                            "lengthMenu": 'Select <select>'
                                                                                    + '<option value="7">7</option>'
                                                                                    + '<option value="10">10</option>'
                                                                                    + '<option value="25">25</option>'
                                                                                    + '<option value="50">50</option>'
                                                                                    + '<option value="75">75</option>'
                                                                                    + '<option value="100">100</option>'
                                                                                    + '</select>Consultancy List',
                                                                            "search": "Search:",
                                                                            "paginate": {
                                                                                "previous": "previous",
                                                                                "next": "next"
                                                                            }
                                                                        }
                                                                    });
                                                });
        </script>

        <script type="text/javascript">
       
            function changeFunc() {


                var selectBox = document.getElementById("country");
                var selectedValue = selectBox.options[selectBox.selectedIndex].value;
               
$.ajax({
                   type: "POST",
                        url: "Student",
                        data: {country: selectedValue},
                        success: function (data) {
                            
                            $("#university").html(data);
                            }
                        
                });

               
            }
            

        </script>

    </body>
</html>
