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
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;

@WebServlet(name = "servletUpLoadBdFi", urlPatterns = {"/servletUpLoadBdFi"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, //10 MB
        maxFileSize = 1024 * 1024 * 1000, //1 GB
        maxRequestSize = 1024 * 1024 * 1000)

/**
 *
 * @author yuxuan
 */
public class ServletUpLoadBdFi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("text/plain;charset=UTF-8");

        /*-- Session pour hibernate--*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        /*----- Récupération des paramètres -----*/
        String idfiStr = request.getParameter("idfis");
        System.out.println(idfiStr);
        int idfi = Integer.parseInt(idfiStr);

        try (PrintWriter out = response.getWriter()) {
            String folderName = "resource/fiche_individuel";
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + folderName;
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Part filePart = request.getPart("filedeposer");
            String fileName = filePart.getSubmittedFileName();
            String path = folderName + File.separator + fileName;

            System.out.println(uploadPath);

            InputStream is = filePart.getInputStream();
            Files.copy(is, Paths.get(uploadPath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);

            try {
                Ficheindividuelle fiche = (Ficheindividuelle) session.load(Ficheindividuelle.class, idfi);
                fiche.setEtatvalide(Boolean.TRUE);
                fiche.setUrlfic(path);
                out.println("Bien changé");
                request.getRequestDispatcher("vueficheres").forward(request, response);

            } catch (Exception e) {
                out.println("e" + e.getMessage());
            }

        } catch (Exception ex) {
            System.out.println("ex" + ex.getMessage());
        }
        t.commit();
    }

}
