package Servlet;

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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@WebServlet(urlPatterns = {"/SevlrtCargarImagen", "/CargarImgPartido"})
public class SevlrtCargarImagen extends HttpServlet {

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
            case "/SevlrtCargarImagen":
                this.ImgCandidato(request, response);
                break;
            case "/CargarImgPartido":
                this.ImgPartido(request, response);
                break;

        }
    }

    private void ImgCandidato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try {
            GestorCandidatos g = GestorCandidatos.obtenerInstancia();
            int id = Integer.parseInt(request.getParameter("id"));
            Candidato c = g.getCandidato(id);

            byte byteArray[] = g.getImg(c);
            OutputStream os = response.getOutputStream();
            response.setContentType("image/jpg");
            os.write(byteArray);
            os.flush();
            os.close();
        } catch (IOException | InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException ex) {
            System.out.println("Excepcion getImg" + ex.getMessage());
        }
    }

    private void ImgPartido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            GestorPartidos g = GestorPartidos.obtenerInstancia();
            int id = Integer.parseInt(request.getParameter("id"));
            Partido c = g.getPartido(id);

            byte byteArray[] = g.getImg(c);
            OutputStream os = response.getOutputStream();
            response.setContentType("image/jpg");
            os.write(byteArray);
            os.flush();
            os.close();
        } catch (IOException | InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException ex) {
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
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(SevlrtCargarImagen.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(SevlrtCargarImagen.class.getName()).log(Level.SEVERE, null, ex);
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
