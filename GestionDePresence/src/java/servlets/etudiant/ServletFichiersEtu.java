/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.etudiant;

import bd.Ficheindividuelle;
import bd.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.SaisirPres;

/**
 * servlet pour transmettre les données de fichiers déposés
 *
 * @author yuxuan
 */
public class ServletFichiersEtu extends HttpServlet {

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
            out.println("<liste_fichier>");

            /*----- Récupération des paramètres -----*/
            String annee = request.getParameter("anneesaisie");
            int idEtu = (int) request.getSession().getAttribute("idetu");

        
            try {
                /*----- Lecture de liste de creneau dans la BD -----*/
                List<Ficheindividuelle> lfichier = SaisirPres.listeFichier(session, idEtu, annee);

                for (Ficheindividuelle fiche : lfichier) {
                    out.println("<fichier>");
                    out.println("<idfichier>"+fiche.getCodefi()+"</idfichier>");
                    out.println("<annee>" + fiche.getAnneefiche() + "</annee>");
                    out.println("<mois>" + fiche.getMoisfiche() + "</mois>");
                    out.println("<url>" + fiche.getUrlfic() + "</url>");
                    out.println("<nom>" + fiche.getUrlfic().substring(26) + "</nom>");
                    out.println("<etat>" +fiche.getEtatvalide()+ "</etat>");           
                    out.println("</fichier>");
                }
            } catch (Exception ex) {
                out.println("<fichier>Erreur - " + ex.getMessage() + "</fichier>");
            }

            out.println("</liste_fichier>");
        }
        t.commit();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
