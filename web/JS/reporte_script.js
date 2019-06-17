/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var total_votos = 0;
function init() {
    cargarReporte2();
    cargarReporte1();
}


function cargarReporte1() {
    fetch("RegistroServlet")
            .then((response) => {
                return response.text();
                // hay que esperar hasta que el servidor devuelva el response
            })
            .then((resultadosEnTexto) => {
                var partidosENJSON = JSON.parse(resultadosEnTexto);
                console.log(partidosENJSON);
                var tableENJSON = document.getElementById("reporteTable");
                var maximo = partidosENJSON[0].votos_partido;
                var nombre = partidosENJSON[0].siglas;

                for (var i = 0; i < partidosENJSON.length; i++) {
                    var row1 = document.createElement("tr");
                    var cell1 = document.createElement("td");
                    var cell2 = document.createElement("td");
                    var cell3 = document.createElement("td");
                    cell1.innerHTML = partidosENJSON[i].siglas;
                    cell2.innerHTML = partidosENJSON[i].votos_partido;
                    cell3.innerHTML = partidosENJSON[i].votos_partido / total_votos * 100 + "%";
                    row1.appendChild(cell1);
                    row1.appendChild(cell2);
                    row1.appendChild(cell3);
                    tableENJSON.appendChild(row1);
                    if (maximo <= partidosENJSON[i].votos_partido) {
                        maximo = partidosENJSON[i].votos_partido;
                        nombre = partidosENJSON[i].siglas;
                    }
                    var cands = partidosENJSON[i].candidatos;
                    for (var j = 0; j < cands.length; j++) {
                        var row2 = document.createElement("tr");
                        var cell4 = document.createElement("td");
                        var cell5 = document.createElement("td");
                        var cell6 = document.createElement("td");
                        cell4.innerHTML = cands[j].nombre + " " + cands[j].apellido1 + cands[j].apellido2;
                        cell5.innerHTML = cands[j].votos_candidato;
                        cell6.innerHTML = +cands[j].votos_candidato / total_votos * 100 + "%";
                        row2.appendChild(cell4);
                        row2.appendChild(cell5);
                        row2.appendChild(cell6);
                        tableENJSON.appendChild(row2);
                        console.log("Candidatos: " + cands[j]);
                    }


                }

                var row3 = document.createElement("tr");
                var cell8 = document.createElement("td");
                var cell9 = document.createElement("td");
                cell9.innerHTML = "Ganador";
                cell8.innerHTML = nombre;

                row3.appendChild(cell9);
                row3.appendChild(cell8);

                tableENJSON.appendChild(row3);

            });
}

function cargarReporte2() {

    fetch("RegistroPrincipal")
            .then((response) => {
                return response.text();
                // hay que esperar hasta que el servidor devuelva el response
            })
            .then((resultadosEnTexto) => {
                var reporte = JSON.parse(resultadosEnTexto);
                var tabla = document.getElementById("reportePrincipal");

                var row1 = document.createElement("tr");
                var row2 = document.createElement("tr");
                var row3 = document.createElement("tr");

                var cell1 = document.createElement("td");
                var cell2 = document.createElement("td");
                var cell3 = document.createElement("td");
                cell1.innerHTML = "Numero de Votantes: " + reporte.votantes;
                cell2.innerHTML = "Abstecionismo: " + reporte.abstecionismo;
                cell3.innerHTML = "Votos efectivos: " + reporte.votos_efectivos;
                total_votos = reporte.votos_efectivos;
                row1.appendChild(cell1);
                row2.appendChild(cell2);
                row3.appendChild(cell3);
                tabla.appendChild(row1);
                tabla.appendChild(row2);

                tabla.appendChild(row3);

            });

}