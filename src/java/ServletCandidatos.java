/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Gestor.GestorCandidatos;
import Modelo.Candidato;
import Modelo.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.json.JSONObject;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@WebServlet(urlPatterns = {"/ServletCandidatos"})
@MultipartConfig
public class ServletCandidatos extends HttpServlet {

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
            throws ServletException, IOException, SQLException, InstantiationException, ClassNotFoundException, IllegalAccessException, FileUploadException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            GestorCandidatos g = GestorCandidatos.obtenerInstancia();
            String ced = request.getParameter("cedula"); //<input type="text" name="cedula" id="cedula" class="form-control" >
            int p = Integer.parseInt(request.getParameter("partido"));
            if (ced != "") {
                int id = g.getId(ced);
                Usuario user = g.getUsuario(id);
                if (user != null) {
                    Part part = request.getPart("imagen");// <input type="file" name="imagen"class="form-control-file" id="exampleFormControlFile1">
                    if (part != null && p != 0) {
                        g.agregarCandidato(user, p, part.getInputStream());
                        request.setAttribute("error", "Si");
                    }
                }
            } else {
                request.setAttribute("error", "No");
            }
            request.getRequestDispatcher("/candidatos_registro.jsp").forward(request, response);
        } catch (ClassNotFoundException | IllegalAccessException | SQLException | InstantiationException ex) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
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
        throw new ServletException("Los datos no pueden recibirse via GET.");
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
        } catch (SQLException | InstantiationException | ClassNotFoundException | IllegalAccessException | FileUploadException ex) {
            Logger.getLogger(ServletCandidatos.class.getName()).log(Level.SEVERE, null, ex);
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
