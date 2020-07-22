/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.etudiant;

import bd.HibernateUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static services.gestionfichier.RessourceConstant.RELATIVE_PATH_FICHE_EMARGEMENT_GENERE;
import services.gestionfichier.gestionpdf.FicheIndividuel;

/**
 *
 * @author yeping
 */
public class ServletEtuDownload extends HttpServlet {

    public static int BUFFER_SIZE = 1024 * 100;
    public static String fileName = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*-- Session pour hibernate--*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        String applicationPath = getServletContext().getRealPath("");
        String filePath = applicationPath + RELATIVE_PATH_FICHE_EMARGEMENT_GENERE;

        //
        String anneeMois = request.getParameter("anneeMois");
        String annee = anneeMois.substring(0, 4);
        String mois = anneeMois.substring(5, 7);
        String idetu = request.getParameter("idetu");
        fileName = "etu" + idetu + "_" + "mois" + mois + "_" + annee + ".pdf";
        System.out.println("servlet : file name" + fileName);

        String relativeUrl = RELATIVE_PATH_FICHE_EMARGEMENT_GENERE + File.separator + fileName;
        String fileUrl = filePath + File.separator + fileName;
//
        System.out.println("servlet : absolute file url : " + fileUrl);
        System.out.println("servlet : absolute file path : " + filePath);
//        File fileEmplacement = new File(filePath);
//        if (!fileEmplacement.exists()) {
//            fileEmplacement.mkdirs();
//        }

        Date d = new Date(Integer.valueOf(annee), Integer.valueOf(mois), 1);
        FicheIndividuel f = new FicheIndividuel(Integer.valueOf(idetu), d, applicationPath.substring(0, applicationPath.length() - 1));

        try {
            f.creerFiche(session);
            System.out.println("servlet : fiche générée");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            t.commit();

            response.setContentType("application/xml;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<url>");
            out.println(relativeUrl);
            out.println("</url>");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
