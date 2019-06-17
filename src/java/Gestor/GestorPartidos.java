/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor;

import Modelo.Candidato;
import Modelo.Partido;
import Modelo.Usuario;
import Utilities.IOUtilities;
import cr.ac.database.managers.DBManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GestorPartidos {

    private static final String BASE_DATOS = "BD_VOTACIONES";
    private static final String USUARIO_BD = "root";
    private static final String CLAVE_BD = "root";
    private DBManager db = null;
    private static GestorPartidos instancia = null;

    private static final String INSERT_PARTIDO
            = "INSERT INTO `BD_VOTACIONES`.`partido`(`nombre`, `siglas`,`bandera_img`)"
            + " VALUES(?,?,?);";
    private static final String SELECT_PARTIDO
            = "SELECT nombre,siglas FROM `BD_VOTACIONES`.`partido`WHERE id = ?;";
    private static final String GET_IMAGE
            = "SELECT  bandera_img FROM `BD_VOTACIONES`.`partido` WHERE id =?; ";
    private static final String LISTAR_PARTIDO
            = "SELECT  id,nombre,siglas FROM `BD_VOTACIONES`.`partido`;";
    private static final String GET_ID
            = " SELECT id"
            + " FROM `BD_VOTACIONES`.`partido`"
            + " WHERE id =? ;";

    private GestorPartidos()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        db = DBManager.getDBManager(DBManager.DB_MGR.MYSQL_SERVER);
    }

    public static GestorPartidos obtenerInstancia()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorPartidos();
        }
        return instancia;

    }

    public void agregarPartido(String nom, String sig, InputStream in) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(INSERT_PARTIDO)) {
            stm.setString(1, nom);
            stm.setString(2, sig);
            stm.setBlob(3, in);

            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede agregar partido");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Partido getPartido(int id) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SELECT_PARTIDO)) {
            stm.clearParameters();
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                Partido p;
                if (rs.next()) {
                    String nom = rs.getString(1);
                    String sig = rs.getString(2);
                    p = new Partido(id, nom, sig);
                    cnx.close();
                    return p;
                }
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getUsuario" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getUsuario" + e.getMessage());
        }
        return null;
    }

    public void readBlob(int id) {

        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement pstmt = cnx.prepareStatement(GET_IMAGE)) {
            // set parameter;
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {

                // write binary stream into file
                String filename = "C:\\Users\\Nao Rojas\\Desktop\\UNA\\Progra 4\\Practicas\\Votacion\\" + "Partido_foto" + id;
                File file = new File(filename);
                FileOutputStream output = new FileOutputStream(file);

                System.out.println("Writing to file " + file.getAbsolutePath());
                while (rs.next()) {
                    InputStream input = rs.getBinaryStream("foto_img");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<Partido> listarPartidos() throws SQLException {

        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(LISTAR_PARTIDO)) {
            ArrayList<Partido> partidos = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nombre");
                String sig = rs.getString("siglas");
                Partido p = new Partido(id, nom, sig);
                partidos.add(p);
            }
            return partidos;
        } catch (Exception e) {
            System.out.println("Excepcion listarProvincias " + e.getMessage());
        }
        return null;
    }

    public int getId(int i) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(GET_ID)) {
            stm.clearParameters();
            stm.setInt(1, i);
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    int id = rs.getInt(1);

                    return id;
                }
                cnx.close();
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getId" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getId" + e.getMessage());
        }
        return -1;
    }

    public byte[] getImg(Partido c) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(GET_IMAGE)) {
            stm.setInt(1, c.getId());
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Blob blob = rs.getBlob("bandera_img");
                    byte byteArray[] = blob.getBytes(1, (int) blob.length());
                    return byteArray;
                }
                cnx.close();
            } catch (Exception e) {
                System.out.println("Excepcion getImg" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion getImg" + e.getMessage());
        }
        return null;
    }

}
