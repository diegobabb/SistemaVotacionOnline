/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Gestor.GestorCandidatos;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Votacion {

    private int id;
    private int tipo; //1-Ni abierta ni cerrada
    //2-Abierta
    //3-Cerrada

    private Timestamp horaInicio;
    private Timestamp horaFinal;

    public Votacion(int id, int tipo, Timestamp horaInicio, Timestamp horaFinal) {
        this.id = id;
        this.tipo = tipo;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getHoraInicio() {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(horaInicio);
        return timeStamp;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {

        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(horaFinal);
        return timeStamp;
    }

    public void setHoraFinal(Timestamp horaFinal) {
        this.horaFinal = horaFinal;
    }

    public boolean estaAbierto() {
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        boolean y = horaInicio.before(ahora);
        boolean x = horaFinal.after(ahora);
        return (x && y);
    }

}
