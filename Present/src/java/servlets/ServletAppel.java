package servlets;

import bd.Cours;
import bd.Enseignant;
import bd.Etudiant;
import bd.Formation;
import bd.Groupe;
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

public class ServletAppel extends HttpServlet {

    ArrayList<Etudiant> optEtudiants = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Session sessH = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = sessH.beginTransaction();
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sessionHttp = request.getSession();
            /*----- Récupération des paramètres -----*/
            int idEns = (int) sessionHttp.getAttribute("idenseig");
            String formation = request.getParameter("formation");
            String groupe = request.getParameter("groupe");
            String cours = request.getParameter("cours");
            String load = request.getParameter("load") == null ? "2" : request.getParameter("load");

            out.println("<?xml version=\"1.0\"?>");
            //premiere load
            if (load.equals("1")) {
                loadPageAppel(out, sessH, idEns);
            }

            if (load.equals("2")) {
                //formation
               
                if (!formation.equals("none")) {
                     Formation f = (Formation) sessH.load(Formation.class, Integer.valueOf(formation));
                    optEtudiants.removeAll(optEtudiants);
                    //Formation f = (Formation) sessH.load(Formation.class, Integer.valueOf(formation));
                    optEtudiants = services.ServiceEtudiant.getEtudiantsForm(sessH, f);
                }
                //groupe
                if (!groupe.equals("none")) {
                    Groupe g = (Groupe) sessH.load(Groupe.class, Integer.valueOf(groupe));
                    //optEtudiants.removeAll(optEtudiants);
                    optEtudiants = services.ServiceEtudiant.getEtudiants(sessH, g);
                }
                
                if (!cours.equals("none")) {
                    Cours c = (Cours) sessH.load(Cours.class, Integer.valueOf(cours));
                }

                //list Etudiant
                out.print("<listetudiant>");
                for (Etudiant e : optEtudiants) {
                    out.print("<etudiant>");
                    out.print("<id>" + e.getNumetudiant() + "</id>");
                    out.print("<nom>" + e.getNometu() + "</nom>");
                    out.print("<prenom>" + e.getPrenometu() + "</prenom>");
                    out.print("<typeetu>" + e.getTypeetu() + "</typeetu>");

                    int nb = 0;
                    try {
                        nb = services.ServicePresence.getAllPresence(sessH, e, c).size();
                    } catch (Exception ex) {
//                        out.print("<lastpre>-</lastpre>");
                        ex.printStackTrace();
                    } finally {
                        if (nb == 0) {
                            out.print("<lastpre>-</lastpre>");
                        } else {
                            out.print("<lastpre>" + services.ServicePresence.getLastPresence(sessH, e, c).getPresence() + "</lastpre>");
                        }
                    }
                    int nbn = 0;
                    try {
                        nbn = services.ServicePresence.getAllPresence(sessH, e, c).size();
                    } catch (Exception ex) {
//                        out.print("<nbpre>-</nbpre>");
                        ex.printStackTrace();
                    } finally {
                        if (nbn == 0) {
                            out.print("<nbpre>-</nbpre>");
                        } else {
                            int nbAbs = services.ServicePresence.getNbAbs(sessH, e, c);
                            int nbAbj = services.ServicePresence.getNbAbj(sessH, e, c);
                            int nbR = services.ServicePresence.getNbRetard(sessH, e, c);
                            out.print("<nbpre>" + nbAbs + "</nbpre>");
                        }
                    }

                    out.print("</etudiant>");
                }
                out.println("</listetudiant>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } // fermer session hibernate
        finally {
            t.commit();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void loadPageAppel(final PrintWriter out, Session session, int userID) {
        //init list etudiant
        // les info pour les choix
        out.print("<info>");
        //navigateur  logger

        Enseignant ens = (Enseignant) session.get(Enseignant.class, userID);
        out.print("<user>");
        out.print("<id>" + ens.getCodee() + "</id>");
        out.print("<nom>" + ens.getNome() + "</nom>");
        out.print("<prenom>" + ens.getPrenome() + "</prenom>");
        out.print("</user>");

        //formation
        out.print("<listformation>");
        for (Formation f : services.ServiceFormation.getAllFormation(session)) {
            out.print("<formation>");
            out.print("<id>" + f.getCodef() + "</id>");
            out.print("<nom>" + f.getNomf() + "</nom>");
            out.print("<type></type>");
            out.print("</formation>");
        }
        out.print("</listformation>");
        //cours
        out.print("<listcours>");
        for (Cours c : services.ServiceCours.getAllCours(session, ens)) {
            out.print("<cours>");
            out.print("<id>" + c.getCodec() + "</id>");
            out.print("<nom>" + c.getNomc() + "</nom>");
            out.print("<type></type>");
            out.print("</cours>");
        }
        out.print("</listcours>");
        //group
        out.print("<listgroupe>");
        for (Groupe g : services.ServiceGroupe.getAllGroupe(session)) {
            out.print("<groupe>");
            out.print("<id>" + g.getCodeg() + "</id>");
            out.print("<lib>" + g.getLibelleg() + "</lib>");
            out.print("</groupe>");

        }
        out.print("</listgroupe>");
        out.print("</info >");
        // end of info
    }
}
