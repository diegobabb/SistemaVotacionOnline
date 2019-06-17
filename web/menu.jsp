<%--
    Document   : menu
    Created on : 31/05/2019, 04:01:24 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="CSS/menu.css" rel="stylesheet" type="text/css"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Principal</title>
    </head>
    <body>
        <nav class="menu">
            <ol>
                <li class="menu-item"><a href="#0">INFORMACION</a></li>
                <li class="menu-item">
                    <a href="#0">USUARIOS</a>
                    <ol class="sub-menu">
                        <li class="menu-item"><a href="usuarios_page.jsp">AGREGAR</a></li>
                        <li class="menu-item"><a href="usuarios_modificar.jsp">MODIFICAR</a></li>
                    </ol>
                </li>
                <li class="menu-item">
                    <a href="#0">CANDIDATOS</a>
                    <ol class="sub-menu">

                        <li class="menu-item"><a href="candidatos_registro.jsp">AGREGAR</a></li>
                        <li class="menu-item"><a href="candidatos_modificar.jsp">MODIFICAR</a></li>

                    </ol>
                </li>
                <li class="menu-item">
                    <a href="#0">PARTIDOS</a>
                    <ol class="sub-menu">
                        <li class="menu-item"><a href="partidos_registro.jsp">AGREGAR</a></li>
                        <li class="menu-item"><a href="partidos_modificar.jsp">MOFIFICAR</a></li>

                    </ol>
                </li>

                <li class="menu-item"><a href="reporte_page.jsp">REPORTES</a></li>
                <li class="menu-item"><a href="admin_page.jsp">CONFIGURACION</a></li>

                <li class="menu-item">  <a href="../Votacion/LogOut">CERRAR</a></li>
            </ol>
        </nav>

    </body>
</html>
