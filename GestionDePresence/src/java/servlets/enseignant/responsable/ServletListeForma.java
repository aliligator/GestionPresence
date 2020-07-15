/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.enseignant.responsable;

import bd.Formation;
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
import services.ServiceRespSaisir;

/**
 *
 * @author yuxuan
 */
public class ServletListeForma extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<l_formation>");

            /*----- Récupération des paramètres -----*/
            int idRes = Integer.parseInt(request.getParameter("idRes"));

            /*-- Session pour hibernate--*/
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();

            try {
                /*----- Lecture de liste de creneau dans la BD -----*/
                Set<Formation> lformation = ServiceRespSaisir.listFormation(session, idRes);
                for (Formation f : lformation) {
                    out.println("<formation>");
                    out.println("<idfor>" + f.getCodef() + "</idfor>");
                    out.println("<nomfor>" + f.getNomf() + "</nomfor>");
                    out.println("</formation>");
                }

            } catch (Exception ex) {
                out.println("<formation>" + "Erreur - " + ex.getMessage() + "</formation>");
            }
            t.commit();

            out.println("</l_formation>");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
