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
                <br/>
                <br/>
                <jsp:include page="/menu.jsp" />
                <div id="container" style="margin: auto; width: 80%">


                    <div id="myModal" class="modal fade" role="dialog">
                        <div class="modal-dialog">

                            <!-- Modal content-->
                            <div class="modal-content" >
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Location</h4>
                                </div>
                                <div id ="map_container" style="width:100%;height:200px;">
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </div>
                            </div>

                        </div>
                    </div>

                    <h3 class="titulo">Consultancy</h3>
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

                                    <th style="text-align: center"><u><a
                                                class="btn btn-warning" href="Consultancy?action=INS"
                                                role="button"> Add</a></u></th>
                                    <th></th>
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
                                        <td style="text-align: center"><a class="btn btn-warning"
                                                                          href="Consultancy?action=DEL&id=${n.id}" role="button"> <span
                                                    class="glyphicon glyphicon-trash"></span></a></td>
                                        <td style="text-align: center"><a class="btn btn-warning"
                                                                          href="Consultancy?action=UPD&id=${n.id}" role="button"><span class="glyphicon glyphicon-edit"></span></a></td>
                                        <td style="text-align: center"><a title="View Map" class="btn btn-warning" data-toggle="modal" data-target="#myModal"
                                                                          role="button" onclick="loadMap('${n.latlng}', '${n.name}')">
                                                <span class="glyphicon glyphicon-map-marker"></span>
                                            </a></td>
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
                                                                                                                               $(document).ready(function () {
                                                                                                                                   $('#example').dataTable({
                                                                                                                                       "lengthMenu": [7, 10, 25, 50, 75, 100],
                                                                                                                                       "language": {
                                                                                                                                           "lengthMenu": 'Select <select>' +
                                                                                                                                                   '<option value="7">7</option>' +
                                                                                                                                                   '<option value="10">10</option>' +
                                                                                                                                                   '<option value="25">25</option>' +
                                                                                                                                                   '<option value="50">50</option>' +
                                                                                                                                                   '<option value="75">75</option>' +
                                                                                                                                                   '<option value="100">100</option>' +
                                                                                                                                                   '</select>Consultancy List',
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
            function loadMap(latln, name) {


                //var latlng = new google.maps.LatLng(28.3949, 84.1240);
                var latlng = getLatLngFromString(latln);
                var myOptions = {
                    zoom: 12,
                    center: latlng,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("map_container"), myOptions);
//                         if (navigator.geolocation) {
//     navigator.geolocation.getCurrentPosition(function (position) {
//         initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
//         map.setCenter(initialLocation);
//     });
 //}
           

                var marker = new google.maps.Marker({
                    position: latlng,
                    map: map,
                    title: name
                });
                google.maps.event.addListenerOnce(map, 'idle', function () {

                    google.maps.event.trigger(map, 'resize');

                });


                function getLatLngFromString(ll) {
                    var latlng = ll.split(',');

                    return new google.maps.LatLng(parseFloat(latlng[0]), parseFloat(latlng[1]));
                }


            }



        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCrkP5YNRjfFG_HFf02wbvoxuFZ_4E0GxE&">
        </script>

    </body>
</html>
