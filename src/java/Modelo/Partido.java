/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Partido {

    private ArrayList<Candidato> candidatos;
    private Imagen img;
    private String nombre;
    private String siglas;
    private int id;

    public Partido(int id, String nombre, String siglas) {
        this.id = id;
        this.img = null;
        this.nombre = nombre;
        this.siglas = siglas;
        candidatos = new ArrayList<>();
    }

    public void agregar(Candidato cand) {
        candidatos.add(cand);
    }

    public ArrayList<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(ArrayList<Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    public Imagen getImg() {
        return img;
    }

    public void setImg(Imagen img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

}
