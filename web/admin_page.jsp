
<%@page import="Modelo.Votacion"%>
<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="JS/script.js" type="text/javascript"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="/menu.jsp" %>
        <div class="first">
            Configuracion
        </div>
        <div class="second">
            <div class="centro">
                <form action="AbrirVotacion" method="GET">
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
                                Seleccione las horas
                            </td>
                            <td>
                                <input class="form-control" type="number" name="horas" id="example-number-input">
                            </td>
                            <td>
                                <button type="submit"id="votacion" class ="btn btn-primary">Abrir</button>
                            </td>
                        </tr>
                        <tr>
                            <td style="column-span: 2">
                                <%            String error = (String) request.getAttribute("error");

                                    if (error != null && error == "Si") {%>
                                <div class="error" >
                                    Ya hay una votacion abierta. Intentar más tarde
                                </div>
                                <%} else if (error != null && error == "No") {%>
                                <div class="success" >
                                    Se ha abierto la votación
                                </div>
                                <%}%>

                            </td>
                        </tr>
                        <% if (v != null) {%>
                        <tr>
                            <td>
                                Hora de Inicio
                            </td>
                            <td>
                                <input name="horaIncio"type="text" disabled="true"placeholder=<%=v.getHoraInicio()%>>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Hora Final
                            </td>
                            <td>
                                <input name="horaIncio"type="text" disabled="true" placeholder=<%=v.getHoraFinal()%>>
                            </td>
                        </tr>
                        <% }%>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
