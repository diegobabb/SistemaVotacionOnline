<%--
    Document   : usuarios_modificar
    Created on : 06/06/2019, 05:29:58 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Votacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
        <script src="JS/script.js" type="text/javascript"></script>
        <title>Usuarios</title>
    </head>
    <body>
        <%@include file="/menu.jsp" %>
        <div class="first">
            Modificar Usuario
        </div>
        <div class="second">
            <div class="centro">
                <form action="ModificarUsuario" method="GET">
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
                                Cedula
                            </td>
                        <tr>
                            <td>
                                <input type="text"  name="cedula" class="form-control" >
                            </td>
                            <td>
                                <button onclick="buscarUsuario()" type="button" class ="btn btn-primary">Buscar</button>
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
