/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.enseignant;

import bd.Cours;
import bd.Creneau;
import bd.Enseignant;
import bd.Etreprersenter;
import bd.EtreprersenterId;
import bd.Etudiant;
import bd.Formation;
import bd.Groupe;
import bd.HibernateUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ServiceLogin;

/**
 *
 * @author yeping
 */
public class ServletComfirmAppel extends HttpServlet {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat heureFormation = new SimpleDateFormat("HH:mm");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //ouvrir une session de hibernate
        Session sessH = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = sessH.beginTransaction();
        try {
            HttpSession sessionHttp = request.getSession(true);
            //get les param
            int idEns = (int) sessionHttp.getAttribute("idenseig");
            String idF = request.getParameter("saisirFormation");
            String idG = request.getParameter("saisirGroupe");
            String idC = request.getParameter("saisirCours");
            String typeAct = request.getParameter("saisirAct");
            String dateSaisi = request.getParameter("date");
            String timeSaisi = request.getParameter("time");
            String timeFinSaisi = request.getParameter("timefin");
            //trouver les objet via les ids.
            Enseignant ens = (Enseignant) sessH.get(Enseignant.class, idEns);
            Formation formation = (Formation) sessH.get(Formation.class, Integer.valueOf(idF));
            Groupe groupe = (Groupe) sessH.get(Groupe.class, Integer.valueOf(idG));
            Cours cours = (Cours) sessH.get(Cours.class, Integer.valueOf(idC));
            Date date = dateFormat.parse(dateSaisi);
            Date heureDeb = heureFormation.parse(timeSaisi);
            Date heureFin = heureFormation.parse(timeFinSaisi);
            Long duree = ((heureFin.getTime() - heureDeb.getTime()) / (1000 * 60));
            //liste etudiant avec leurs presences correspondantes.
            String[] idsEtu = request.getParameterValues("idsEtu[]");
            String[] presEtu = request.getParameterValues("preEtu[]");
            //creer et inserer creneau lors de comfirmer de faire appel
            String typeActi = typeAct.equals("1") ? "seance" : typeAct.equals("2") ? "examen" : "";
            Creneau cre = new Creneau(cours, cours.getNomc(), ens, groupe, date, duree, heureDeb, typeActi);
            sessH.save(cre);
//            cre = services.ServiceCreneau.getLastCreneau(sessH);
            //affecter les etudiant en ce creneau
            for (int i = 0; i < idsEtu.length; i++) {
                Etudiant e = (Etudiant) sessH.get(Etudiant.class, Integer.valueOf(idsEtu[i]));
                Etreprersenter pre = new Etreprersenter(new EtreprersenterId(cre.getCodecre(), e.getNumetudiant()), cre, e);
                switch (presEtu[i]) {
                    case "1":
                        pre.setPresence("PRESENCE");
                        break;
                    case "2":
                        pre.setPresence("ABS");
                        break;
                    case "3":
                        pre.setPresence("ABJ");
                        break;
                    case "4":
                        pre.setPresence("RETARD");
                        break;
                }
                System.out.println(presEtu[i]);
                System.out.println(pre.getPresence());
                System.out.println(pre);
                sessH.save(pre);
            }
            System.out.println("servlets.ServletComfirmAppel.processRequest() : : : : : :" + ServiceLogin.estResponsable(sessH, idEns));
            if (ServiceLogin.estResponsable(sessH, idEns)) {
                request.getRequestDispatcher("/confirmeAppelE?ok=ok").forward(request, response);
            } else {
                request.getRequestDispatcher("/alertConfirm?ok=ok").forward(request, response);
            }

        } catch (Exception e) {
            response.sendError(501, "Il y a un promlème du serveur, veulliez faire appel à la main et puis contacter le DSI ");
            System.err.println("<----------ajout échoué----------->");
            e.printStackTrace();
        } finally {
            t.commit();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
