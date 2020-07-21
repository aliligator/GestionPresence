/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.responsableForm;

import bd.Groupe;
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
 * servelt pour affichier la liste de groupe des étudiants d'une formation 
 * @author yxuuan
 */
public class ServletListeGrp extends HttpServlet {
    
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
			out.println("<l_groupe>");

			/*----- Récupération des paramètres -----*/
			int idRes = Integer.parseInt(request.getParameter("idRes"));
                        
                        
                        /*-- Session pour hibernate--*/
                        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                        Transaction t =session.beginTransaction();
                        
			try {
				/*----- Lecture de liste de creneau dans la BD -----*/
				Set<Groupe> groups = ServiceRespSaisir.listeGroup(session, idRes);
                                for (Groupe g : groups){
                                     out.println("<groupe>");
                                     out.println("<idgrp>"+g.getCodeg()+"</idgrp>");
                                     out.println("<nomgrp>"+g.getLibelleg()+"</nomgrp>");
                                     out.println("</groupe>");
                                }
                               
                            }
			catch (Exception ex)
				{
				out.println("<groupe>"+"Erreur - " + ex.getMessage()+"</groupe>");
				}
                        t.commit();

			out.println("</l_groupe>");
                        } 
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {doGet(request, response);}

}
