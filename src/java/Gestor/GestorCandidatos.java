/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor;

import Modelo.Candidato;
import Modelo.Partido;
import Modelo.Usuario;
import cr.ac.database.managers.DBManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GestorCandidatos {

    private static final String BASE_DATOS = "BD_VOTACIONES";
    private static final String USUARIO_BD = "root";
    private static final String CLAVE_BD = "root";
    private DBManager db = null;
    private static GestorCandidatos instancia = null;
    private static final String INCREMENTAR_VOTOS = "UPDATE `BD_VOTACIONES`.`candidato` SET num_votos = num_votos + 1 WHERE id = ?;";
    private static final String INSERT_CANDIDATO
            = "INSERT INTO `BD_VOTACIONES`.`candidato` (`id_usuario`,`id_partido`,`foto_img`)"
            + "VALUES(?,?,?);";

    private static final String SELECT_USUARIO
            = " SELECT cedula,nombre,apellido1,apellido2,clave"
            + " FROM `BD_VOTACIONES`.`usuario`"
            + " WHERE id = ?;";

    private static final String SELECT_CANDIDATO
            = " SELECT cedula,nombre,apellido1,apellido2,clave"
            + " FROM `BD_VOTACIONES`.`usuario`"
            + " INNER JOIN `BD_VOTACIONES`.`candidato`"
            + " ON `BD_VOTACIONES`.`usuario`.id = ?;";

    private static final String GET_IMAGEN
            = " SELECT DISTINCT foto_img"
            + " FROM `BD_VOTACIONES`.`candidato`"
            + " WHERE id = ?;";

    private static final String SAVE_IMAGEN
            = " SELECT DISTINCT foto_img"
            + " FROM `BD_VOTACIONES`.`candidato`"
            + " WHERE id = ?;";

    private static final String GET_ID
            = " SELECT id"
            + " FROM `BD_VOTACIONES`.`usuario`"
            + " WHERE cedula =? ;";

    private static final String LISTAR_CANDIDATOS = "SELECT candidato.id,usuario.cedula,usuario.nombre, usuario.apellido1,usuario.apellido2,usuario.clave,partido.nombre"
            + " FROM ((`BD_VOTACIONES`.`candidato`"
            + " INNER JOIN `BD_VOTACIONES`.`usuario` ON `BD_VOTACIONES`.`candidato`.id_usuario = `BD_VOTACIONES`.`usuario`.id)"
            + " INNER JOIN `BD_VOTACIONES`.`partido`ON `BD_VOTACIONES`.`candidato`.id_partido= ? AND `BD_VOTACIONES`.`partido`.id= ?);";

    private static final String VOTOS_EFECTIVOS = "select sum(num_votos) from `BD_VOTACIONES`.`candidato`;";
    private static final String VOTOS_PARTIDO = "  select sum(num_votos)   from `BD_VOTACIONES`.`candidato` inner join `BD_VOTACIONES`.`partido` on"
            + "  `candidato`.`id_partido`=? AND `partido`.`id`=?;";
    private static final String VOTOS_CANDIDATO = " select sum(num_votos)   from `BD_VOTACIONES`.`candidato` where id=?;";

    private static final String UPDATE_CANDIDATO
            = "update candidato set id_partido = ? where id_usuario = ?;";

    private GestorCandidatos()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        db = DBManager.getDBManager(DBManager.DB_MGR.MYSQL_SERVER);
    }

    public static GestorCandidatos obtenerInstancia()
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorCandidatos();
        }
        return instancia;
    }

    public void updateCadidato(int partido, int usuario) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(UPDATE_CANDIDATO)) {
            stm.setInt(1, partido);
            stm.setInt(2, usuario);
            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede actualizar usuario");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void readBlob(int candidateId) {

        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement pstmt = cnx.prepareStatement(SAVE_IMAGEN)) {
            // set parameter;
            pstmt.setInt(1, candidateId);
            try (ResultSet rs = pstmt.executeQuery()) {

                // write binary stream into file
                String filename = "C:\\Users\\Nao Rojas\\Desktop\\UNA\\Progra 4\\Practicas\\Votacion\\" + "Candidato_foto" + candidateId;
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

    public ArrayList<Candidato> listarCandidatos(int id_partido) throws SQLException {

        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(LISTAR_CANDIDATOS)) {
            stm.clearParameters();
            stm.setInt(1, id_partido);
            stm.setInt(2, id_partido);
            ArrayList<Candidato> cands = new ArrayList<>();
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("candidato.id");
                    String ced = rs.getString("usuario.cedula");
                    String nom = rs.getString("usuario.nombre");
                    String ap1 = rs.getString("usuario.apellido1");
                    String ap2 = rs.getString("usuario.apellido2");
                    String clave = rs.getString("usuario.clave");

                    Candidato c = new Candidato(id, ced, nom, ap1, ap2, clave);
                    cands.add(c);
                }
                return cands;
            } catch (Exception e) {
                System.out.println("Excepcion listarCandidatos " + e.getMessage());
            }
            return null;
        }
    }

    public void agregarCandidato(Usuario user, int p, InputStream in) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(INSERT_CANDIDATO)) {
            stm.setInt(1, user.getId());
            stm.setInt(2, p);
            stm.setBlob(3, in);
            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede agregarCandidato");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Candidato getCandidato(int id_u) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SELECT_CANDIDATO)) {
            stm.clearParameters();
            stm.setInt(1, id_u);

            try (ResultSet rs = stm.executeQuery()) {
                Candidato u;
                if (rs.next()) {

                    String ced = rs.getString(1);
                    String nom = rs.getString(2);
                    String ap1 = rs.getString(3);
                    String ap2 = rs.getString(4);
                    String clave = rs.getString(5);
                    u = new Candidato(id_u, ced, nom, ap1, ap2, clave);
                    return u;
                }
                cnx.close();
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getCedula" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getCedula" + e.getMessage());
        }
        return null;
    }

    public byte[] getImg(Candidato c) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(GET_IMAGEN)) {

            stm.setInt(1, c.getId());

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    Blob blob = rs.getBlob("foto_img");
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

    public int getId(String ced) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(GET_ID)) {
            stm.clearParameters();
            stm.setString(1, ced);
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

    public Usuario getUsuario(int id) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SELECT_USUARIO)) {
            stm.clearParameters();
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                Usuario u;
                if (rs.next()) {
                    String ced = rs.getString(1);
                    String nombre = rs.getString(2);
                    String ap1 = rs.getString(3);
                    String ap2 = rs.getString(4);

                    String pass = rs.getString(5);
                    u = new Usuario(id, ced, nombre, ap1, ap2, pass, 0);

                    return u;
                }
                cnx.close();
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getUsuario" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getUsuario" + e.getMessage());
        }
        return null;
    }

    public Partido getPartido(int id) throws SQLException {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(SELECT_USUARIO)) {
            stm.clearParameters();
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                Partido p;
                if (rs.next()) {
                    String nom = rs.getString(1);
                    String sig = rs.getString(2);
                    p = new Partido(id, nom, sig);
                    return p;
                }
                cnx.close();
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getUsuario" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getUsuario" + e.getMessage());
        }
        return null;
    }

    public void incrementarVoto(int id) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(INCREMENTAR_VOTOS)) {
            stm.setInt(1, id);
            if (stm.executeUpdate() != 1) {
                throw new SQLException("No se puede agregarCandidato");
            }
            cnx.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public int votosEfectivos() {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(VOTOS_EFECTIVOS)) {
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

    public int votoPorPartido(int id) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(VOTOS_PARTIDO)) {
            stm.clearParameters();
            stm.setInt(1, id);
            stm.setInt(2, id);
            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    int num = rs.getInt(1);

                    return num;
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

    public int votoPorCandidaro(int id) {
        try (Connection cnx = db.getConnection(BASE_DATOS, USUARIO_BD, CLAVE_BD);
                PreparedStatement stm = cnx.prepareStatement(VOTOS_CANDIDATO)) {
            stm.clearParameters();
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    int num = rs.getInt(1);

                    return num;
                }
                cnx.close();
            } catch (Exception e) {
                System.out.println("Excepcion RESUT SET getId" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Excepcion  PreparedStatement getId" + e.getMessage());
        }
        return 0;
    }

}
