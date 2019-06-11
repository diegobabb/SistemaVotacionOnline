/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Gestor.GestorCandidatos;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Votacion {

    private int tipo; //1-Ni abierta ni cerrada
    //2-Abierta
    //3-Cerrada

    private Hora horaInicio;
    private Hora horaFinal;
    private int horas;
    private static Votacion instancia = null;

    public Votacion() {
        horaFinal = new Hora(0, 0, 0);
        horaInicio = new Hora(0, 0, 0);
        tipo = 1;

    }

    public static Votacion obtenerInstancia()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        if (instancia == null) {
            instancia = new Votacion();
        }
        return instancia;
    }

    public void abrirVotacion(Hora h, int hour) {

        if (tipo == 1) {
            horas = hour;
            setTipo(2);
            setHoraInicio(h);
            horaFinal = new Hora(horaInicio.getHora() + horas, horaInicio.getMinuto(), horaInicio.getSegundo());

        }

    }

    public boolean estaAbierta(Hora h) {
        System.out.printf("Hora Actual %s \n", h);
        System.out.printf("Hora de Inicio %s y Hora Final %s %n", horaInicio, horaFinal);
        if (tipo != 1) {
            if (horaInicio.getHora() <= h.getHora() && h.getHora() < horaFinal.getHora()) {
                return true;
            } else if (0 <= h.getMinuto() && h.getMinuto() <= horaFinal.getMinuto()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public int getTipo() {
        return tipo;
    }

    public int getHoras() {
        return horas;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setHoraInicio(Hora horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Hora getHoraInicio() {
        return horaInicio;
    }

    public Hora getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Hora horaFinal) {
        this.horaFinal = horaFinal;
    }

//    public static void main(String[] args) {
//        Votacion v = new Votacion();
//        v.abrirVotacion(new Hora(Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND), 2);
//        if (v.estaAbierta(new Hora(Calendar.HOUR_OF_DAY + 2, Calendar.MINUTE + 50, Calendar.SECOND))) {
//            System.out.print("SI ESTA ABIERTA \n");
//        } else {
//            System.out.print("NO ESTA ABIERTA \n");
//        }
//
//    }
}
