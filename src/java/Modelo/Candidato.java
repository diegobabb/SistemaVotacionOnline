/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Blob;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Candidato extends Usuario {

    public Candidato(int id, String cedula, String nombre,
            String apellido1, String apellido2, String clave) {

        super(id, cedula, nombre, apellido1, apellido2, clave, 0);

    }

}
