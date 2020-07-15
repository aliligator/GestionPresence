package servlets.enseignant;

import bd.Cours;
import bd.Enseignant;
import bd.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ServiceCours;

/**
 *
 * @author Bonso
 */
public class ServletConsultAppel extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try (PrintWriter out = response.getWriter()) {
            //get sessionHttp
            HttpSession sessionHttp = request.getSession();
            /*----- Récupération des paramètres -----*/
            int idEnseignant = (int) sessionHttp.getAttribute("idenseig");
            Enseignant ens = (Enseignant) session.load(Enseignant.class, idEnseignant);
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_cours>");
            //            ArrayList<String> lstcours = Bdforcours.getCours(idEnseignant);
            ArrayList<Cours> lstcours = ServiceCours.getAllCours(session, ens);
            for (Cours cours : lstcours) {
                out.println("<cours>" + cours.getNomc() + "</cours>");
            }
            for (Cours cours : lstcours) {
                out.println("<id>" + cours.getCodec() + "</id>");
            }
            out.println("</liste_cours>");

            //         for (Cours c : services.serviceCours.getAllCours(session)) {
            //         out.print("<cours>");
            //         out.print("<id>" + c.getCodec() + "</id>");
            //         out.print("<nom>" + c.getNomc() + "</nom>");
            //         out.print("<type>" + c.getTypec() + "</type>");
            //         out.print("</cours>");
            //     }
            //         out.print("</liste_cours>");
        }
        t.commit();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
