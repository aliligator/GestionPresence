/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.etudiant;

import bd.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.SaisirPres;

/**
 * Servlet pour réaliser les functions de la déclaration de présence des
 * activites des étuidiants
 *
 * @author yuxuan
 */
public class ServeltEtuPreAct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<resPres>");

            /*----- Récupération des paramètres -----*/
            int idCreChoisi = Integer.parseInt(request.getParameter("idcrechoisi"));
            int idEtu = (int) request.getSession().getAttribute("idetu");

            /*-- Session pour hibernate--*/
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();

            try {
                /*----- Lecture de liste de creneau dans la BD -----*/
                SaisirPres.saisirPres(session, idEtu, idCreChoisi);
                String message = "Votre précence du créneau a bien été saisie";
                out.println("<res>" + message + "</res>");
            } catch (Exception ex) {
                out.println("<res>" + "Erreur - " + ex.getMessage() + "</res>");
            }
            t.commit();

            out.println("</resPres>");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
