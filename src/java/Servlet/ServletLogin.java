package Servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Gestor.GestorUsuarios;
import Gestor.GestorVotacion;
import Modelo.Hora;
import Modelo.Usuario;
import Modelo.Votacion;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@WebServlet(urlPatterns = {"/ServletLogin", "/LogOut"})
public class ServletLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/ServletLogin":
                this.LogIn(request, response);
                break;
            case "/LogOut":
                this.LogOut(request, response);
                break;

        }
    }

    private void LogIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try {
            GestorUsuarios g = GestorUsuarios.obtenerInstancia();
            Usuario user = g.getUsuario(
                    request.getParameter("user"), request.getParameter("password"));
            GestorVotacion vote = GestorVotacion.obtenerInstancia();
            Votacion v = vote.selectUltimo();
            if (user != null) {
                request.getSession(true).setAttribute("usuario", user);
                request.getSession(true).setAttribute("votacion", v);
                if (g.getAdmin(user.getCedula()) == 0) {
                    String c = user.getClave();
                    String ced = user.getCedula();
                    if (!c.equals(ced)) {
                        if (vote.estaAbierto()) {
                            request.getRequestDispatcher("/principal.jsp").forward(request, response);
                        } else {
                            request.setAttribute("error", "Ya cerro la votacion");
                            request.getRequestDispatcher("/index.jsp").forward(request, response);
                        }
                    } else {

                        request.getRequestDispatcher("/usuarios_registro.jsp").forward(request, response);

                    }
                } else {
                    request.getRequestDispatcher("/menu.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Usuario invalido");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private void LogOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        session.removeAttribute("usuario");
        session.invalidate();
        request.getRequestDispatcher("/ServletLogin").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
