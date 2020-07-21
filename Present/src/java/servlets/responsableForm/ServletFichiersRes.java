/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.responsableForm;

import bd.Etudiant;
import bd.Ficheindividuelle;
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
public class ServletFichiersRes extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
            /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
            /*-- Session pour hibernate--*/
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction t = session.beginTransaction();
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_fichieretu>");

            /*----- Récupération des paramètres -----*/
            String moisannee = request.getParameter("moisannee");
            String annne = moisannee.substring(0,4);
            String mois = moisannee.substring(5,7);
            int idResponsable = (int) request.getSession().getAttribute("idenseig");

        
            try {
                /*----- Lecture de liste de creneau dans la BD -----*/
                Set<Etudiant> letudiant = ServiceRespSaisir.lEtudiant(session, idResponsable);
                  for(Etudiant etuu : letudiant){
                        Ficheindividuelle fiche = ServiceRespSaisir.lficher_etu(etuu, mois, annne);
                        if(fiche !=null){
                            out.println("<etufichier>");
                            out.println("<numetu>"+etuu.getNumetudiant()+"</numetu>");
                            out.println("<nometu>"+etuu.getNometu()+"</nometu>");
                            out.println("<prenometu>"+etuu.getPrenometu()+"</prenometu>");
                            out.println("<idfi>"+fiche.getCodefi()+"</idfi>");
                            out.println("<nomfi>"+fiche.getUrlfic().substring(26)+"</nomfi>");
                            out.println("<etat>"+fiche.getEtatvalide()+"</etat>");
                            out.println("</etufichier>");
                        }
                      
                    }

                }catch (Exception ex) {
                out.println("<etufichier>Erreur - " + ex.getMessage() + "</etufichier>");
            }

            out.println("</liste_fichieretu>");
        }
       
        t.commit();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {doGet(request, response);}

}
