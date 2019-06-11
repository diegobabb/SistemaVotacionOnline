/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Blob;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Candidato extends Usuario {

    public Candidato(int id, String cedula, String nombre,
            String apellido1, String apellido2, String clave) {

        super(id, cedula, nombre, apellido1, apellido2, clave, 0);

    }

}
