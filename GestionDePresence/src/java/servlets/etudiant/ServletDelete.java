/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.etudiant;

import bd.Ficheindividuelle;
import bd.HibernateUtil;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.FileUtils;
import static services.gestionfichier.RessourceConstant.RELATIVE_PATH_FICHE_EMARGEMENT;

/**
 * Servlet pour supprimer un fichier
 *
 * @author yuxuan
 */
public class ServletDelete extends HttpServlet {

    public static String fileName = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEtu = (int) request.getSession().getAttribute("idetu");
        String idfiStr = request.getParameter("idfi");
        int idfi = Integer.parseInt(idfiStr);

        //Recuperer nom de fichier 
        fileName = request.getParameter("fileName");

        //Creer path
        String applicationPath = getServletContext().getRealPath("");
        String deletPath = applicationPath + File.separator + RELATIVE_PATH_FICHE_EMARGEMENT;
        String filePath = deletPath + File.separator + fileName;
        System.out.println(filePath);

        //Suppprimer file
        FileUtils.delete(filePath);

        //supprimer dans base de données 
        //hibernate 
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        try {
            //supprimer la ligne de ce fichier 
            try {
                Ficheindividuelle fichedelet = (Ficheindividuelle) session.load(Ficheindividuelle.class, idfi);
                session.delete(fichedelet);
                request.setAttribute("msg_info", "Votre fichier est bien supprimé");
                request.getRequestDispatcher("consuetu").forward(request, response);
            } catch (Exception ex) {
                System.out.println("ex" + ex.getMessage());
            }

        } catch (Exception e) {
            System.out.println("e" + e.getMessage());
        }
        t.commit();
    }

}
