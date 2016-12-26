<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Add University</title>

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
                                    <h4>Add University</h4>
                                </div>
                                <form action="University?action=INS" method="POST"
                                      class="form-horizontal">
                                    <input type="hidden" name="accion" value="INS" />
                                    <input type="hidden" name="latlng" id="latlng" value="${consultancy.latlng}" />


                                    <div class="form-group form-group-sm">
                                        <label for="name" class="control-label col-md-4"> University Name</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="name"
                                                   value="${university.name}" placeholder=" Name" required=""
                                                   maxlength="30" />
                                        </div>
                                    </div>

                                    <div class="form-group form-group-sm">
                                        <label for="country" class="control-label col-md-4">Country</label>
                                        <div class="col-md-8">
                                            <select class="form-control" name="country"
                                                    value="${university.countryId}">

                                                <c:forEach items="${countries}" var="country">
                                                    <option value="${country.countryId}">${country.name}</option>
                                                </c:forEach>

                                            </select>

                                        </div>
                                    </div>

                                    <div class="form-group form-group-sm">
                                        <label for="address" class="control-label col-md-4">Address
                                        </label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="address"
                                                   value="${university.address}" placeholder="Address" required=""
                                                   />
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm">
                                        <label for="website" class="control-label col-md-4">Website
                                        </label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="website"
                                                   value="${university.website}" placeholder="Website" required=""
                                                   />
                                        </div>
                                    </div>





                                    <div class="form-group form-group-sm">
                                        <label for="contact" class="control-label col-md-4">Contact
                                            No</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="contact"
                                                   value="${university.contact}" placeholder="Contact No"
                                                   maxlength="30" />
                                        </div>
                                    </div>

                                    <div class="form-group form-group-sm">
                                        <label for="description" class="control-label col-md-4">Description
                                        </label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="description"
                                                   value="${university.description}" placeholder="Description"
                                                   required="" maxlength="150" />
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
                    <div>${mensaje}</div>

                </div>
            </div>
        </div>

        <script src="../js/jquery.js" type="text/javascript"></script>
        <script src="../js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../js/contactos.js" type="text/javascript"></script>

        <script type="text/javascript">
            function loadMap() {
                var latlng = new google.maps.LatLng(28.3949, 84.1240);
                var myOptions = {
                    zoom: 12,
                    center: latlng,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("map_container"), myOptions);
                             if (navigator.geolocation) {
     navigator.geolocation.getCurrentPosition(function (position) {
         initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
         map.setCenter(initialLocation);
     });
 }
           
           
            google.maps.event.addListener(map, 'click', function(event) {
   placeMarker(event.latLng);
      var latlng = marker.getPosition();

                    document.getElementById("latlng").value = latlng;

});
  var marker;
function placeMarker(location) {
  if (marker == null) {
    marker = new google.maps.Marker({
          position: location,
          map: map
      });
  } else {
    marker.setPosition(location);
  }
}
               
                 


                
            }


        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCrkP5YNRjfFG_HFf02wbvoxuFZ_4E0GxE&callback=initMap">
        </script>

    </body>
</html>