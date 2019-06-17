<%--
    Document   : partidos_registro
    Created on : 03/06/2019, 01:19:10 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="Modelo.Votacion"%>
<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="/menu.jsp" %>
        <form action="ServletPartidos" method="POST" enctype="multipart/form-data">
            <div class="first">
                Registro de Partidos
            </div>

            <div class="second">
                <div class="centro">
                    <form action="ServletPartidos" method="POST"enctype="multipart/form-data" >
                        <table class="dataTable">

                            <%
                                Usuario user = (Usuario) request.getSession(false).getAttribute("usuario");
                                Votacion v = (Votacion) request.getSession(false).getAttribute("votacion");
                                if (user == null) {
                                    response.sendRedirect("index.jsp");
                                }

                            %>

                            <tr>
                                <td>
                                    Nombre
                                </td>
                            <tr>
                                <td>
                                    <input type="text" autocomplete="off" name="nombre" id="nombre" class="form-control" >
                                </td>

                            </tr>


                            <tr>
                                <td>
                                    Siglas
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text"   autocomplete="off"  id="siglas" name="siglas"   class="form-control" >
                                    </input>
                                </td>

                            </tr>

                            <tr>

                                <td>
                                    <label for="exampleFormControlFile1">Subir Foto</label>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="file" name="imagen"class="form-control-file" id="exampleFormControlFile1">
                                </td>

                            </tr>

                            <tfooter>
                                <tr>
                                    <td>
                                        <%            String error = (String) request.getAttribute("error");

                                            if (error != null && error == "No") {%>
                                        <div class="error" >
                                            No se puede agregar Partido
                                        </div>
                                        <%} else if (error != null && error == "Si") {%>
                                        <div class="success" >
                                            Partido Agregado
                                        </div>
                                        <%}%>

                                    </td>
                                </tr>
                            </tfooter>
                        </table>
                </div>
            </div>
        </div>

        <footer>
            <button type="submit" class ="btn btn-primary">Registrar</button>
        </footer>
    </form>
</body>
</html>
