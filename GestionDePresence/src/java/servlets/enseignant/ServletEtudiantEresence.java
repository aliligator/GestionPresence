/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.enseignant;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.Bdforcours;

/**
 *
 * @author Bonso
 */
public class ServletEtudiantEresence extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String idcours = request.getParameter("idcours");
        String datedebut = request.getParameter("datedebut");
        String datefin = request.getParameter("datefin");       
        try (PrintWriter out = response.getWriter()) {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_etudiant>");

            ArrayList<String> listeEtudiant = Bdforcours.getEtudiant(Integer.valueOf(idcours), datedebut, datefin);
            for (int i = 0; i < listeEtudiant.size();) {
                out.println("<etudiant>");
                out.println("<DateC>" + listeEtudiant.get(i) + "</DateC>");
                out.println("<NomE>" + listeEtudiant.get(i + 1) + "</NomE>");
                out.println("<PrenomE>" + listeEtudiant.get(i + 2) + "</PrenomE>");
                out.println("<TypeE>" + listeEtudiant.get(i + 3) + "</TypeE>");
                out.println("<Presence>" + listeEtudiant.get(i + 4) + "</Presence>");
                out.println("</etudiant>");
                i = i + 5;

            }
            out.println("</liste_etudiant>");

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
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServletEtudiantEresence.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServletEtudiantEresence.class.getName()).log(Level.SEVERE, null, ex);
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
