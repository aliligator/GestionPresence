/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.responsableForm;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ServiceRespSaisir;

/**
 *
 * @author yuxuan
 */
public class ServletSupProjet extends HttpServlet {

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

			/*----- Récupération des paramètres -----*/
			int idsup = Integer.parseInt(request.getParameter("idsup"));
                        
			try {
				/*----- Supprimer le creneau dans la BD -----*/
				int ressup = ServiceRespSaisir.suppCreneau(idsup);
                                if(ressup !=0){
                                    out.println("<ressup>"+"Supprimé"+"<ressup>");}
                                else{
                                    out.println("<ressup>"+"pas réussi"+"<ressup>");
                                }
                            }
			catch (Exception ex)
				{
				out.println("<ressup>Erreur - " + ex.getMessage() + "</ressup>");
				}

			}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {doGet(request, response);}

}
