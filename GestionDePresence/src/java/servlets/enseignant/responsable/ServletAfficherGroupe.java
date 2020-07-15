/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.enseignant.responsable;

import bd.Groupe;
import bd.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yeping
 */
@WebServlet(name = "servletaffichergroupe", urlPatterns = {"/servletaffichergroupe"})
public class ServletAfficherGroupe extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Session sessH = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = sessH.beginTransaction();
        try (PrintWriter out = response.getWriter()) {
            String[] idetu = request.getParameterValues("dans");
            String idg = request.getParameter("idg");
            Groupe g = (Groupe) sessH.load(Groupe.class, Integer.valueOf(idg));
            for (String string : idetu) {
                System.out.println(string);
            }
            int lenth = idetu.length;
            String[] estDansGroupe = new String[lenth];
            for (int i = 0; i < estDansGroupe.length; i++) {
                int est = services.ServiceEtudiant.estDansGroupe(sessH, Integer.valueOf(idetu[i]), g);
                estDansGroupe[i] = String.valueOf(est);
            }

            out.println("<?xml version=\"1.0\"?>");
            out.print("<estdansgroupe>");

            for (int i = 0; i < estDansGroupe.length; i++) {
                out.print("<est>" + estDansGroupe[i] + "</est>");
            }
            out.print("</estdansgroupe>");

        }
        t.commit();
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
