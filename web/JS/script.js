function buscarUsuario() {
    var ced_input = document.getElementById("cedula");
    var ced = ced_input.value;
    fetch("ServletBusqueda?cedula=" + ced)
            .then((response) => {
                return response.text();
                // hay que esperar hasta que el servidor devuelva el response
            })
            .then((resultadosEnTexto) => {
                console.log("Resultados Usuario: " + resultadosEnTexto);
                var userEnJSON = JSON.parse(resultadosEnTexto);
                var nom_input = document.getElementById("nombre");
                var ap1_input = document.getElementById("ap1");
                var ap2_input = document.getElementById("ap2");
                nom_input.value = userEnJSON.nombre;
                ap1_input.value = userEnJSON.apellido1;
                ap2_input.value = userEnJSON.apellido2;
            });
}

function cargarPartidos() {

    fetch("ServletCargarPartidos")
            .then((response) => {
                return response.text();
                // hay que esperar hasta que el servidor devuelva el response
            })
            .then((resultadosEnTexto) => {
                console.log("Resultados Partidos: " + resultadosEnTexto);
                var partidosENJSON = JSON.parse(resultadosEnTexto);
                var dropdownPartido = document.getElementById("partido");
                for (var i = 0; i < partidosENJSON.length; i++) {
                    console.log(partidosENJSON[i]);
                    var option = document.createElement("option");
                    option.text = partidosENJSON[i].nombre;
                    option.value = partidosENJSON[i].id;
                    dropdownPartido.add(option);
                }
            });
}

function cargarDatos() {

    fetch("ServletVotos")
            .then((response) => {
                return response.text();
                // hay que esperar hasta que el servidor devuelva el response
            })
            .then((resultadosEnTexto) => {

                console.log("Resultados Partidos: " + resultadosEnTexto);
                var cant = 0;
                var table = document.getElementById("candidatosTable");
                var tblBody = document.createElement("tbody");

                var partidosENJSON = JSON.parse(resultadosEnTexto);

                for (var i = 0; i < partidosENJSON.length; i++) {
                    if (cant % 5 === 0) {
                        var row = document.createElement("tr");
                    }
                    var cands = partidosENJSON[i].candidatos;
                    cant++;
                    for (var j = 0; j < cands.length; j++) {

                        var cell1 = document.createElement("td");

                        var contenedor = document.createElement("div");
                        var flipper = document.createElement("div");
                        var front = document.createElement("div");
                        var back = document.createElement("div");
                        var div1 = document.createElement("div");
                        var div2 = document.createElement("div");
                        contenedor.setAttribute("class", "flip-container");
                        flipper.setAttribute("class", "flipper");
                        front.setAttribute("class", "front");
                        back.setAttribute("class", "back");
                        div1.setAttribute("class", "div1");
                        div2.setAttribute("class", "div2");
                        contenedor.setAttribute("ontouchstart", "this.classList.toggle('hover');");
                        var form1 = document.createElement("form");

                        form1.setAttribute("action", "SevlrtCargarImagen");
                        form1.setAttribute("method", "GET");
                        var img = document.createElement("IMG");
                        img.setAttribute("src", "SevlrtCargarImagen?id=" + cands[j].id);
                        var t = document.createTextNode(cands[j].nombre + " " + cands[j].apellido1 + " " + cands[j].apellido2);
                        form1.appendChild(img);
                        div1.appendChild(form1);
                        div2.appendChild(t);
                        front.appendChild(div1);
                        front.appendChild(div2);

                        var form2 = document.createElement("form");
                        form2.setAttribute("action", "CargarImgPartido");
                        form2.setAttribute("method", "GET");

                        var img1 = document.createElement("IMG");
                        img1.setAttribute("src", "CargarImgPartido?id=" + partidosENJSON[i].id);
                        var div3 = document.createElement("div");
                        var div4 = document.createElement("div");
                        div3.setAttribute("class", "div1");
                        div4.setAttribute("class", "div2");
                        var t1 = document.createTextNode(partidosENJSON[i].nombre);
                        var btn = document.createElement("button");
                        btn.setAttribute("id", "btn");
                        btn.innerHTML = "Votar";

                        form2.appendChild(img1);
                        div3.appendChild(form2);
                        div4.appendChild(t1);
                        back.appendChild(div3);
                        back.appendChild(div4);
                        back.appendChild(btn);




                        flipper.appendChild(front);
                        flipper.appendChild(back);
                        contenedor.appendChild(flipper);
                        cell1.appendChild(contenedor);
                        row.appendChild(cell1);


                    }
                }
                tblBody.appendChild(row);
                table.appendChild(tblBody);
            });
}




//function agregarCandidato() {
//    var ced_input = document.getElementById("cedula");
//    var ced = ced_input.value;
//
//    fetch("ServletCandidatos?cedula=" + ced)
//            .then((response) => {
//                return response.text();
//                // hay que esperar hasta que el servidor devuelva el response
//            })
//            .then((resultadosEnTexto) => {
//                console.log("Resultados Usuario: " + resultadosEnTexto);
//                var errorEnJson = JSON.parse(resultadosEnTexto);
//                var error_input = document.getElementById("error");
//                var success_input = document.getElementById("success");
//                if (errorEnJson.tipo === 1) {
//                    error_input.style.display = 'block';
//                    error_input.innerHTML = "No se puede agregar Candidato";
//                } else if (errorEnJson.tipo === 2) {
//                    success_input.style.display = 'block';
//                    success_input.innerHTML = "Candidato agreado";
//                }
//
//            });
//}
