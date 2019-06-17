package Servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Gestor.GestorCandidatos;
import Gestor.GestorPartidos;
import Gestor.GestorUsuarios;
import Gestor.GestorVotacion;
import Modelo.Candidato;
import Modelo.Hora;
import Modelo.Partido;
import Modelo.Votacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
@WebServlet(urlPatterns = {"/AbrirVotacion", "/ExportarVotacion"})
public class AbrirVotacion extends HttpServlet {

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
            throws ServletException, IOException, InstantiationException, ClassNotFoundException, IllegalAccessException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getServletPath()) {
            case "/RegistroServlet":
                this.AbrirVotacion(request, response);
                break;
            case "/RegistroPrincipal":
                this.ExportarVotacion(request, response);
                break;

        }
    }

    private void AbrirVotacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InstantiationException, ClassNotFoundException, IllegalAccessException, SQLException {

        try (PrintWriter out = response.getWriter()) {
            GestorVotacion votacion = GestorVotacion.obtenerInstancia();
            Votacion v = votacion.selectUltimo();
            if (v == null) {
                int horas = Integer.parseInt(request.getParameter("horas"));
                //cerrar votacion
                votacion.abrirVotacion(horas);
                v = votacion.selectUltimo();
                request.setAttribute("votacion", v);
                request.setAttribute("error", "No");

            } else if (!v.estaAbierto()) {
                int horas = Integer.parseInt(request.getParameter("horas"));
                //cerrar votacion
                votacion.abrirVotacion(horas);
                v = votacion.selectUltimo();
                request.setAttribute("votacion", v);
                request.setAttribute("error", "No");

            } else {

                request.setAttribute("votacion", v);
                request.setAttribute("error", "Si");
            }

            request.getRequestDispatcher("/admin_page.jsp").forward(request, response);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    private void ExportarVotacion(HttpServletRequest request, HttpServletResponse response) {
        try {

            GestorCandidatos gc = GestorCandidatos.obtenerInstancia();
            GestorPartidos gp = GestorPartidos.obtenerInstancia();

            ArrayList<Partido> partidos = gp.listarPartidos();

            for (Partido partido : partidos) {
                ArrayList<Candidato> cands = gc.listarCandidatos(partido.getId());
                for (Candidato c : cands) {
                    gc.readBlob(c.getId());
                }
            }
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException ex) {

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
        } catch (InstantiationException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (InstantiationException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AbrirVotacion.class.getName()).log(Level.SEVERE, null, ex);
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
