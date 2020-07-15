/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.etudiant;

import bd.Etudiant;
import bd.Ficheindividuelle;
import bd.HibernateUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;

@WebServlet(name = "servletUpEtu", urlPatterns = {"/servletUpEtu"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, //10 MB
        maxFileSize = 1024 * 1024 * 1000, //1 GB
        maxRequestSize = 1024 * 1024 * 1000)

/**
 *
 * @author yuxuan
 */
public class ServletUpEtu extends HttpServlet {

    HttpSession session = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("text/plain;charset=UTF-8");

        /*-- Session pour hibernate--*/
        Session sessionH = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = sessionH.beginTransaction();

        int idEtu = (int) request.getSession().getAttribute("idetu");

        try (PrintWriter out = response.getWriter()) {
            session = request.getSession(false);
            String folderName = "resource/fiche_individuel";
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + folderName;
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Part filePart = request.getPart("filedeposer");
            String fileName = filePart.getSubmittedFileName();
            String path = folderName + File.separator + fileName;

            String anneemois = request.getParameter("moischoisi");
            String annee = anneemois.substring(0, 4);
            String mois = anneemois.substring(5, 7);
            System.out.println(idEtu);
            System.out.println(uploadPath);

            InputStream is = filePart.getInputStream();
            Files.copy(is, Paths.get(uploadPath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);
            String message = "";
            try {
                //Trouver etudiant
                Etudiant etu = (Etudiant) sessionH.load(Etudiant.class, idEtu);

                //Creer un nouveau Ficheindividuelle
                //Etudiant etudiant, String moisfiche, String anneefiche, String urlfic
                Boolean etat = false;
                Ficheindividuelle ficheindi = new Ficheindividuelle(etu, mois, annee, path, etat);

                //Tester s'il y a déjà ce fichier pour cet etudiant (meme mois et meme annee)
                Set<Ficheindividuelle> fichiers = etu.getFicheindividuelles();
                Boolean estexiste = false;
                for (Ficheindividuelle f : fichiers) {
                    if (mois.equals(f.getMoisfiche()) && annee.equals(f.getAnneefiche())) {
                        estexiste = true;
                    }
                }
                if (estexiste == false) {
                    session.setAttribute("fileName", fileName);
                    sessionH.save(ficheindi);
                    t.commit();
                    message = "Votre fichier individuel: " + fileName + " a bien été déposé.";
                    request.setAttribute("msg_info", message);
                    request.getRequestDispatcher("resupload").forward(request, response);

                } else {
                    message = "Le fichier individuel de " + anneemois + " existe déjà.";
                    request.setAttribute("msg_info", message);
                    request.getRequestDispatcher("resupload").forward(request, response);
                }

            } catch (Exception e) {
                out.println("e" + e.getMessage());
            }

        } catch (Exception ex) {
            System.out.println("ex" + ex.getMessage());
        }
    }

}
