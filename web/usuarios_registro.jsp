<%--
    Document   : usuarios_registro
    Created on : 29/05/2019, 06:47:03 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
<link href="https://fonts.googleapis.com/css?family=Questrial&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">

<html>
    <form action="ServletPassword" method="GET">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Usuario</title>
        </head>
        <%
            Usuario user = (Usuario) request.getSession(false).getAttribute("usuario");
            if (user == null) {
                response.sendRedirect("index.jsp");
            }
        %>
        <body>

            <div class="first">
                Información de Usuario
            </div>
            <div class="second">
                <div class="centrar">

                    <table class="dataTable">
                        <tr>
                            <td>
                                Cedula
                            </td>
                            <td>
                                Nombre
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text"  disabled="true"id="cedula" class="form-control" placeholder=<%=user.getCedula()%>>

                            </td>
                            <td>
                                <input type="text"  disabled="true"id="nombre" class="form-control" placeholder=  <%=user.getNombre()%>>
                                </input>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                Apellido 1
                            </td>
                            <td>
                                Apellido 2
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text"  disabled="true"id="ap1" class="form-control" placeholder= <%=user.getApellido1()%>>

                            </td>
                            <td>
                                <input type="text" disabled="true" id="ap2" class="form-control" placeholder= <%=user.getApellido2()%>>


                            </td>

                        </tr>
                        <tr>
                            <td>
                                Nueva Contraseña
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="password"name="pass" class="form-control"  placeholder="Nueva Contraseña">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Confirmar Contraseña
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="password" name="pass2"class="form-control"  placeholder="Confirmar Contraseña">
                            </td>
                        </tr>

                    </table>

                </div>
            </div>
            <div class="second">
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {%>
                <div class="error">
                    <%=error%>
                </div>
                <%}%>
            </div>
        </body>
        <footer>
            <button type="submit" class="btn btn-primary">Siguiente</button>
        </footer>
    </form>
</html>
