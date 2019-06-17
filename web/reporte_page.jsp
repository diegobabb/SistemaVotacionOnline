<%--
    Document   : registro_page
    Created on : 17/06/2019, 12:08:36 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="JS/reporte_script.js" type="text/javascript"></script>
        <link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <%@include file="/menu.jsp" %>
    <body onload="init()">
        <div class="first">
            Reporte
        </div>

        <div class="second">
            <div class="centro">
                <table id="reportePrincipal">
                    <caption> Datos Principales</caption>
                </table>
                <table id="reporteTable">
                    <caption> Partidos y Candidatos</caption>
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            Votos
                        </td>
                        <td>
                            Porcentaje
                        </td>

                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>
