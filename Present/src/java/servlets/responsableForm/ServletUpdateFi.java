/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.responsableForm;

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


/**
 *
 * @author yuxuan
 */
public class ServletUpdateFi extends HttpServlet {

    public static final String DELETE_DIR = "ressource/fiche_individuel";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
        /*-Session de hibernate-*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t =session.beginTransaction();
        
        /*----- Récupération des paramètres -----*/
        String idfiStr = request.getParameter("idfi");
        int idfi = Integer.parseInt(idfiStr);

            //supprimer le fichier qui n'est pas validé dans dossier 
              //retouver url de ce fichier 
              Ficheindividuelle fiche = (Ficheindividuelle)session.load(Ficheindividuelle.class, idfi);
              String url = fiche.getUrlfic();
              
              //nom de ce fichier 
              String fileName = url.substring(26);
              
              //path de ce ficher 
              String applicationPath = getServletContext().getRealPath("");
              String deletPath = applicationPath + File.separator + DELETE_DIR;
              String filePath = deletPath + File.separator+fileName;
              System.out.println(filePath);
              
              //supprimer 
              //FileUtils.delete(filePath);
              t.commit();
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {doGet(request, response);}


}
