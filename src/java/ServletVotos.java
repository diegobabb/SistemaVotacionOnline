/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Gestor.GestorCandidatos;
import Gestor.GestorPartidos;
import Modelo.Candidato;
import Modelo.Partido;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@WebServlet(urlPatterns = {"/ServletVotos"})
public class ServletVotos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            GestorCandidatos g = GestorCandidatos.obtenerInstancia();
            GestorPartidos gp = GestorPartidos.obtenerInstancia();
            ArrayList<Partido> partidos = gp.listarPartidos();
            JSONArray arrayPartido = new JSONArray();
            for (Partido partido : partidos) {
                ArrayList<Candidato> cands = g.listarCandidatos(partido.getId());
                JSONObject partidoENJSON = new JSONObject();
                partidoENJSON.put("id", partido.getId());
                partidoENJSON.put("nombre", partido.getNombre());
                partidoENJSON.put("siglas", partido.getSiglas());
                JSONArray arrayCandidato = new JSONArray();
                if (cands.size() > 0) {
                    for (Candidato c : cands) {
                        JSONObject candidatoENJOSN = new JSONObject();
                        candidatoENJOSN.put("id", c.getId());
                        candidatoENJOSN.put("nombre", c.getNombre());
                        candidatoENJOSN.put("apellido1", c.getApellido1());
                        candidatoENJOSN.put("apellido2", c.getApellido2());
                        arrayCandidato.put(candidatoENJOSN);

                    }
                    partidoENJSON.put("candidatos", arrayCandidato);

                    arrayPartido.put(partidoENJSON);
                }
            }
            out.print(arrayPartido);
            out.close();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException ex) {
            System.out.println("Excepcion getImg" + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletVotos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletVotos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
