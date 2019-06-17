/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function init() {
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
                var tableENJSON = document.getElementById("reporteTable");
                var maximo = partidosENJSON[0].votos_partido;
                var nombre = partidosENJSON[0].siglas;
                for (var i = 0; i < partidosENJSON.length; i++) {
                    var row1 = document.createElement("tr");
                    var cell1 = document.createElement("td");
                    var cell2 = document.createElement("td");
                    var cell3 = document.createElement("td");
                    cell1.innerHTML = partidosENJSON[i].siglas;
                    cell2.innerHTML = "Votos : " + partidosENJSON[i].votos_partido;
                    cell3.innerHTML = "Porcentaje : " + partidosENJSON[i].votos_partido / 100;
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
                        cell4.innerHTML = cands[i].nombre + " " + cands[i].apellido1 + cands[i].apellido2;
                        cell5.innerHTML = "Votos : " + cands[i].votos_candidato;
                        cell6.innerHTML = "Porcentaje : " + cands[i].votos_candidato / 100;
                        row2.appendChild(cell4);
                        row2.appendChild(cell5);
                        row2.appendChild(cell6);
                        tableENJSON.appendChild(row2);
                    }

                    var row3 = document.createElement("tr");
                    var cell7 = document.createElement("td");
                    var cell8 = document.createElement("td");
                    cell7.innerHTML = maximo;
                    cell8.innerHTML = nombre;
                    row3.appendChild(cell7);
                    row3.appendChild(cell8);
                    tableENJSON.appendChild(row3);
                }

            });
}
