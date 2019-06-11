<%--
    Document   : candidatos_modificar
    Created on : 06/06/2019, 05:30:26 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
        <script src="JS/script.js" type="text/javascript"></script>
        <title>Candidatos</title>
    </head>
    <body onload="cargarPartidos()">
        <form action="ModificarCandidato" method="POST" enctype="multipart/form-data">
            <div class="first">
                Registro de Candidatos
            </div>

            <div class="second">
                <div class="centro">

                    <table class="dataTable">
                        <%
                            Usuario user = (Usuario) request.getSession(false).getAttribute("usuario");
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
                                <input type="text" name="cedula" id="cedula" class="form-control" >
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
                            <input type="text"   disabled="true"   id="nombre" name="nombre"   class="form-control" >
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
                                <input type="text"  disabled="true"  id="ap1" name="ap1"   class="form-control">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Apellido 2

                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text"   disabled="true" id="ap2" name="ap2" class="form-control" >
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Partido
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <select name="partido" class="form-control" id="partido" >

                                    <option value="0">Seleccione un Partido...</option>
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
                                        No se puede agregar Candidato
                                    </div>
                                    <%} else if (error != null && error == "Si") {%>
                                    <div class="success" >
                                        Candidato Agregado
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
