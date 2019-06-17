/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor;

import Modelo.Usuario;
import cr.ac.database.managers.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GestorUsuarios {

    private static final String BASE_DATOS = "BD_VOTACIONES";
    private static final String USUARIO_BD = "root";
    private static final String CLAVE_BD = "root";
    private DBManager db = null;
    private static GestorUsuarios instancia = null;

    private static final String INSERT_USUARIO
            = "INSERT INTO `BD_VOTACIONES`.`usuario` (`cedula`, `apellido1`, `apellido2`, `nombre`,  `clave`)"
            + "VALUES(?, ?, ?, ?, ?);";
    private static final String SELECT_USUARIO
            = "SELECT id, nombre, apellido1, apellido2 FROM `BD_VOTACIONES`.`usuario` WHERE cedula=? AND clave=? ;";
    private static final String SELECT_CLAVE
            = "SELECT cedula, clave FROM `BD_VOTACIONES`.`usuario`WHERE cedula = ?;";
    private static final String SET_CLAVE
            = "UPDATE `BD_VOTACIONES`.`usuario` SET `clave`= ? WHERE cedula = ?;";
    private static final String GET_ADMIN
            = "SELECT admin FROM `BD_VOTACIONES`.`usuario` where cedula = ?;";
    private static final String VOTAR
            = "UPDATE `BD_VOTACIONES`.`usuario` SET `voto`=1 WHERE `id` = ?;";

    private static final String YA_VOTO
            = "SELECT voto FROM `BD_VOTACIONES`.`usuario` where cedula = ?;";

    private static final String NUM_VOTANTES
            = " select count(*) from `BD_VOTACIONES`.`usuario`;";
    private static final String NUM_ABS
            = " select count(*) from `BD_VOTACIONES`.`usuario`;";
    private static final String UPDATE_USUARIO
            = "update usuario set nombre = ?, apellido1 = ?, apellido2 = ? where cedula = ?;";
    private GestorUsuarios()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        db = DBManager.getDBManager(DBManager.DB_MGR.MYSQL_SERVER);
    }

    public static GestorUsuarios obtenerInstancia()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorUsuarios();
        }
        return instancia;
    }

    public void updateUsuario(String nombre, String apellido1, String apellido2, String cedula){
          try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(UPDATE_USUARIO)) {
            stm.setString(1, nombre);
            stm.setString(2, apellido1);
            stm.setString(3, apellido2);
            stm.setString(4, cedula);
            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede actualizar usuario");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void agregarUsuario(String ced, String ap1, String ap2, String nombre) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(INSERT_USUARIO)) {
            stm.setString(1, ced);
            stm.setString(2, ap1);
            stm.setString(3, ap2);
            stm.setString(4, nombre);
            stm.setString(5, ced);

            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede agregar usuario");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean samePass(String ced) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SELECT_CLAVE)) {
            stm.setString(1, ced);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String c = rs.getString(1);
                String n = rs.getString(2);
                cnx.close();
                return (c == n);

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public boolean findUser(String ced) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SELECT_CLAVE)) {
            stm.setString(1, ced);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String c = rs.getString(1);
                cnx.close();
                return (true);

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public void setPass(Usuario u, String clave) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SET_CLAVE)) {
            stm.clearParameters();
            stm.setString(1, clave);
            stm.setString(2, u.getCedula());

            if (stm.executeUpdate() != 1) {
                throw new SQLException(String.format(
                        "No se puede Cambiar clave: '%s'", clave));
            }
        }
    }

    public Usuario getUsuario(String ced, String pas) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SELECT_USUARIO)) {
            stm.clearParameters();
            stm.setString(1, ced);
            stm.setString(2, pas);
            try (ResultSet rs = stm.executeQuery()) {
                Usuario u;
                if (rs.next()) {
                    int i = rs.getInt(1);
                    String nombre = rs.getString(2);
                    String ap1 = rs.getString(3);
                    String ap2 = rs.getString(4);

                    u = new Usuario(i, ced, nombre, ap1, ap2, pas, 0);
                    cnx.close();
                    return u;
                }
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getUsuario" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getUsuario" + e.getMessage());
        }
        return null;
    }

    public int getAdmin(String ced) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(GET_ADMIN)) {
            stm.clearParameters();
            stm.setString(1, ced);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int i = rs.getInt(1);
                    cnx.close();
                    return i;
                }
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getAdmin" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getAdmin" + e.getMessage());
        }
        return -1;
    }

    public boolean yaVoto(Usuario user) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(YA_VOTO)) {
            stm.setString(1, user.getCedula());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                int i = rs.getInt(1);

                cnx.close();
                return (i == 1);

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public void votar(Usuario user) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(VOTAR)) {
            stm.setInt(1, user.getId());

            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede votar");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public int cantidadVotantes() {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(NUM_VOTANTES)) {
            if (rs.next()) {

                int i = rs.getInt(1);

                cnx.close();
                return i;

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    public int abstencionismo() {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(NUM_ABS)) {
            if (rs.next()) {

                int i = rs.getInt(1);

                cnx.close();
                return i;

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

}
