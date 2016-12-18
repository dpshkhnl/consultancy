<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Insert Consultancy</title>

        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        
        <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet"
              type="text/css" />
        <link href="icons/estilos.css" rel="stylesheet" type="text/css" />
        <link href="css/estilos.css" rel="stylesheet" type="text/css" />
    </head>
    <body onload="loadMap()">
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
                                    <h3>Insert Consultancy</h3>
                                </div>
                                <form action="Consultancy?action=INS" method="POST"
                                      class="form-horizontal">
                                    <input type="hidden" name="accion" value="INS" />
                                    <input type="hidden" name="latlng" id="latlng" value="${consultancy.latlng}" />
                                    



                                    <div class="form-group form-group-sm">
                                        <label for="name" class="control-label col-md-4">Full
                                            Name</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="name"
                                                   value="${consultancy.name}" placeholder="Name" required=""
                                                    />
                                        </div>
                                    </div>

                                    <div class="form-group form-group-sm">
                                        <label for="address" class="control-label col-md-4">Address
                                        </label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="address"
                                                   value="${consultancy.address}" placeholder="Address" required=""
                                                   />
                                        </div>
                                    </div>
                                                 
                                    <div class="form-group form-group-sm">
                                        <label for="website" class="control-label col-md-4">Website
                                        </label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="website"
                                                   value="${consultancy.website}" placeholder="Website" required=""
                                                    />
                                        </div>
                                    </div>


                                    <%-- <div class="form-group form-group-sm">
                                            <label for="role" class="control-label col-md-4">Role</label>
                                            <div class="col-md-8">
                                                    <select class="form-control" name="role" value="${consultancy.role}">
                                                            <option value="Admin">Admin</option>
                                                            <option value="Consultancy">Consultancy</option>
                                                            <option value="Consultancy">Consultancy</option>
                                                    </select> 
                                            </div>
                                    </div> --%>

                                    <div class="form-group form-group-sm">
                                        <label for="email" class="control-label col-md-4">Email</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="email"
                                                   value="${consultancy.email}" placeholder="Email" maxlength="30" />
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm">
                                        <label for="contact" class="control-label col-md-4">Contact
                                            No</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="contact"
                                                   value="${consultancy.phone}" placeholder="Contact No"
                                                   maxlength="30" />
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm">
                                        <label for="contactPerson" class="control-label col-md-4">Contact
                                            Person</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="contactPerson"
                                                   value="${consultancy.contactPerson}" placeholder="Contact Person"
                                                  />
                                        </div>
                                    </div>
<div id ="map_container" style="width:100%;height:200px;">
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

 <script type="text/javascript">
            function loadMap() {
                var latlng = new google.maps.LatLng(27.7172, 85.3240);
                var myOptions = {
                    zoom: 8,
                    center: latlng,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("map_container"), myOptions);

               
                google.maps.event.addListener(map, 'click', function (event) {
                    
                    var marker = new google.maps.Marker({
                        position: event.latLng,
                        map: map
                    });
                    var latlng = marker.getPosition();

                    document.getElementById("latlng").value = latlng;


                });

                
            }


        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCrkP5YNRjfFG_HFf02wbvoxuFZ_4E0GxE&callback=initMap">
        </script>
    </body>
</html>