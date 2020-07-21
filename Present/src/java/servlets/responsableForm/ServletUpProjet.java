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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yuxuan
 */
public class ServletUpProjet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
                 /*----- Type de la réponse -----*/
		response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter())
			{
                            /*----- Ecriture de la page XML -----*/
                            out.println("<?xml version=\"1.0\"?>");
                            out.println("<un_crenau>");

                            /*----- Récupération des paramètres -----*/
                            int idup = Integer.parseInt(request.getParameter("idup"));
                            
                            /*-Session de hibernate-*/
                            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                            Transaction t =session.beginTransaction();
                            
                            /*-----Création d'une format de date-----*/

                            try {
                                    /*----- Trouver le creneau choisi dans la BD -----*/
                                        Creneau cre = (Creneau)session.load(Creneau.class,idup);
                                        //out.println("<nom>"+cre.getNomcre()+"</nom>");
                                        out.println("<type>"+cre.getTypeactivitecre()+"</type>");
                                        out.println("<groupe>"+cre.getGroupe().getCodeg()+"</groupe>");
                                        out.println("<date>"+cre.getDatecre()+"</date>");
                                        out.println("<heure>"+cre.getHeuredeb()+"</heure>");
                                        out.println("<duree>"+services.ServiceCreneau.getHeureFin(cre)+"</duree>");
                                    }
                            catch (Exception ex)
                                    {
                                    out.println("<resultat>Erreur - " + ex.getMessage() + "</resultat>");
                                    }

                            out.println("</un_crenau>");
                            t.commit();
                        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {doGet(request, response);}


}
