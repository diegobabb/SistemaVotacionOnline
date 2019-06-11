<%--
    Document   : index
    Created on : 30/05/2019, 10:38:36 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Iniciar Sesión</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link href="CSS/style.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Questrial&display=swap" rel="stylesheet">

    </head>
    <body>

        <div class="centrar">
            <form action="ServletLogin" method="POST">
                <table>
                    <tr>
                    <tr>
                        <td>Usuario</td>
                    </tr>
                    <td>
                        <input name="user"type="text" autocomplete="off"> </td>
                    </tr>
                    <tr>
                    <tr>
                        <td>Contraseña</td>
                    </tr>
                    <td>
                        <input name="password"type="password" autocomplete="off"> </td>
                    </tr>
                    <tr>

                        <td>
                            <%
                                String error = (String) request.getAttribute("error");
                                if (error != null) {%>
                            <%=error%>
                            <%}%>
                        </td>
                    </tr>
                    <tr>

                        <td>

                            <input class="btn" type="submit" value="Ingresar"/>


                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
