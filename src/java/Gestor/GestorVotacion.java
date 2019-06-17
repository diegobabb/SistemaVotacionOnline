/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor;

import Modelo.Usuario;
import Modelo.Votacion;
import cr.ac.database.managers.DBManager;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.math.*;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GestorVotacion {

    private static final String BASE_DATOS = "BD_VOTACIONES";
    private static final String USUARIO_BD = "root";
    private static final String CLAVE_BD = "root";
    private DBManager db = null;
    private static GestorVotacion instancia = null;
    private static String AGREGAR = "INSERT BD_VOTACIONES.votacion (`fecha_apertura`,`fecha_cierre`,`estado`) VALUES(?,?,1);";
    private static String ABIERTO = "SELECT fecha_apertura, fecha_cierre FROM BD_VOTACIONES.votacion ORDER BY ID DESC LIMIT 1;";
    private static String ULTIMO = "SELECT id,fecha_apertura, fecha_cierre,estado FROM BD_VOTACIONES.votacion ORDER BY ID DESC LIMIT 1;";

    private GestorVotacion()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        db = DBManager.getDBManager(DBManager.DB_MGR.MYSQL_SERVER);
    }

    public static GestorVotacion obtenerInstancia()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorVotacion();
        }
        return instancia;
    }

    public void agregarVotacion() {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(AGREGAR)) {
            Timestamp inicio = new Timestamp(System.currentTimeMillis());
            stm.setTimestamp(1, inicio);
            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede agregarCandidato");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Votacion selectUltimo() {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(ULTIMO)) {

            if (rs.next()) {
                int id = rs.getInt("id");
                Timestamp di = rs.getTimestamp("fecha_apertura");
                Timestamp df = rs.getTimestamp("fecha_cierre");
                int estado = rs.getInt("estado");
                return new Votacion(id, estado, di, df);
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    private final long FORMULA = 3600000;

    public void abrirVotacion(int horas) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(AGREGAR)) {
            Timestamp inicio = new Timestamp(System.currentTimeMillis());
            stm.setTimestamp(1, inicio);
            long p = inicio.getTime();
            Timestamp ultimo = new Timestamp(FORMULA * horas + p);
            stm.setTimestamp(2, ultimo);

            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede agregarCandidato");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean estaAbierto() {
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(ABIERTO)) {

            if (rs.next()) {
                Timestamp inicio = rs.getTimestamp("fecha_apertura");
                Timestamp ultimo = rs.getTimestamp("fecha_cierre");
                return (inicio.before(ahora) && ultimo.after(ahora));
            }
            cnx.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
