
<link href="CSS/principal_style.css" rel="stylesheet" type="text/css"/>
<link href="https://fonts.googleapis.com/css?family=Questrial&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Alegreya+SC:900&display=swap" rel="stylesheet">
<script src="JS/script.js" type="text/javascript"></script>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body onload="cargarDatos()">
        <%@include file="/menu_usuario.jsp" %>
        <div class="first">
            Sistema de Votación
        </div>
        <div class="second">
            <table id="candidatosTable">

            </table>
        </div>

    </body>
</html>