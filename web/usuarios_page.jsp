<%--
    Document   : usuarios_registro
    Created on : 29/05/2019, 06:47:03 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="Modelo.Votacion"%>
<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
<link href="https://fonts.googleapis.com/css?family=Questrial&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
<script src="JS/script.js" type="text/javascript"></script>
<html>
    <form action="ServletUsuarios" method="POST">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Usuario</title>

        </head>
        <%
            Usuario user = (Usuario) request.getSession(false).getAttribute("usuario");
            Votacion v = (Votacion) request.getSession(false).getAttribute("votacion");
            if (user == null) {
                response.sendRedirect("index.jsp");
            }

        %>
        <body>
            <%@include file="/menu.jsp" %>
            <div class="first">
                Información de Usuario
            </div>
            <div class="second">
                <div class="centro">

                    <table class="dataTable">
                        <tr>
                            <td>
                                Cedula
                            </td>
                        <tr>
                            <td>
                                <input type="text"  name="cedula" class="form-control" >
                            </td>
                        </tr>


                        <tr>
                            <td>
                                Nombre
                            </td>
                        </tr>
                        </tr>
                        <td>
                            <input type="text"  name="nombre" class="form-control" >
                            </input>
                        </td>

                        </tr>
                        </tr>
                        <td>
                            Apellido 1
                        </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="ap1" class="form-control">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Apellido 2

                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text"  name="ap2" class="form-control" >
                            </td>
                        </tr>
                        <tfooter>
                            <tr>
                                <td>
                                    <%                                        String error = (String) request.getAttribute("error");

                                        if (error != null && error == "No") {%>
                                    <div class="error" >
                                        No se puede agregar Usuario
                                    </div>
                                    <%} else if (error != null && error == "Si") {%>
                                    <div class="success" >
                                        Usuario Agregado
                                    </div>
                                    <%}%>
                                </td>
                            </tr>
                        </tfooter>

                    </table>

                </div>
            </div>
        </body>
        <footer>
            <button type="submit" class ="btn btn-primary">Siguiente</button>
        </footer>
    </form>
</html>
