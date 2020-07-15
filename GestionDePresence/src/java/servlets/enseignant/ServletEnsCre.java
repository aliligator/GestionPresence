/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.enseignant;

import bd.Creneau;
import bd.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ServiceCreneau;

/**
 *
 * @author yeping
 */
@WebServlet(name = "servletEnsCre", urlPatterns = {"/servletEnsCre"})
public class ServletEnsCre extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_creneau>");
            /*----- Récupération des paramètres -----*/
            String datechoisi = request.getParameter("date");
            /*-- Session pour hibernate--*/
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();
            try {
                /*----- Lecture de liste de creneau dans la BD -----*/
                ArrayList<Creneau> lCreneau = ServiceCreneau.listeCreneau(session, datechoisi);

                for (Creneau creneau : lCreneau) {
                    out.println("<creneau>");
                    out.println("<id>" + creneau.getCodecre() + "</id>");
                    out.println("<nom>" + creneau.getNomcre() + "</nom>");
                    out.println("<type>" + creneau.getTypeactivitecre() + "</type>");
                    out.println("<ens>" + creneau.getEnseignant().getPrenome() + " " + creneau.getEnseignant().getNome() + "</ens>");
                    out.println("<grp>" + creneau.getGroupe().getLibelleg() + "</grp>");
                    out.println("<date>" + creneau.getDatecre() + "</date>");
                    out.println("<h>" + creneau.getHeuredeb() + "</h>");
                    out.println("<duree>" + ServiceCreneau.getHeureFin(creneau) + "</duree>");
                    out.println("</creneau>");
                }
            } catch (Exception ex) {
                out.println("<creneau>Erreur - " + ex.getMessage() + "</creneau>");
            } finally {
                t.commit();

            }
            out.println("</liste_creneau>");
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
