/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.etudiant;

import bd.Creneau;
import bd.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.SaisirPres;

/**
 * Pour etuidiant les creneaus d'activite qu'ils doivent presenter
 *
 * @author yuxuan
 */
public class ServletCrenPres extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        /*-- Session pour hibernate--*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_creneau>");

            /*----- Récupération des paramètres -----*/
            String datechoisi = request.getParameter("datechoisi");
            int idEtu = (int) request.getSession().getAttribute("idetu");

            try {
                /*----- Lecture de liste de creneau dans la BD -----*/
                Set<Creneau> lCreneau = SaisirPres.droitPresence(session, idEtu, datechoisi);

                for (Creneau creneau : lCreneau) {
                    out.println("<creneau>");
                    out.println("<id>" + creneau.getCodecre() + "</id>");
                    //out.println("<nom>" + creneau.getNomcre() + "</nom>");
                    out.println("<type>" + creneau.getTypeactivitecre() + "</type>");
                    out.println("<grp>" + creneau.getGroupe().getLibelleg() + "</grp>");
                    out.println("<h>" + creneau.getHeuredeb() + "</h>");
                    out.println("<duree>" + services.ServiceCreneau.getHeureFin(creneau)+ "</duree>");
                    out.println("</creneau>");
                }
            } catch (Exception ex) {
                out.println("<creneau>Erreur - " + ex.getMessage() + "</creneau>");
            }

            out.println("</liste_creneau>");
        }
        t.commit();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
