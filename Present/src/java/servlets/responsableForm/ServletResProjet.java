/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.responsableForm;

import bd.Creneau;
import bd.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ServiceRespSaisir;

/**
 *
 * @author yuxuan
 */
public class ServletResProjet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_creneau>");

            /*----- Récupération des paramètres -----*/
            String datechoisi = request.getParameter("datechoisi");
            int idRespon = (int) request.getSession().getAttribute("idenseig");

            /*-- Session pour hibernate--*/
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();
            try {
                /*----- Lecture de liste de creneau dans la BD -----*/
                ArrayList<Creneau> lCreneau = ServiceRespSaisir.listeCreneau(session, idRespon, datechoisi);

                for (Creneau creneau : lCreneau) {
                    out.println("<creneau>");
                    out.println("<id>" + creneau.getCodecre() + "</id>");
                    //out.println("<nom>" + creneau.getNomcre() + "</nom>");
                    out.println("<type>" + creneau.getTypeactivitecre() + "</type>");
                    out.println("<grp>" + creneau.getGroupe().getLibelleg() + "</grp>");
                    out.println("<date>" + creneau.getDatecre() + "</date>");
                    out.println("<h>" + creneau.getHeuredeb() + "</h>");
                    out.println("<duree>" + services.ServiceCreneau.getHeureFin(creneau) + "</duree>");
                    out.println("</creneau>");
                }
            } catch (Exception ex) {
                out.println("<creneau>Erreur - " + ex.getMessage() + "</creneau>");
            }

            out.println("</liste_creneau>");
            t.commit();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
