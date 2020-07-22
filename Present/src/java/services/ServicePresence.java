/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.Cours;
import bd.Creneau;
import bd.Etrepresent;
import bd.Etudiant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author 
 */
public class ServicePresence {

    /**
     * Return EtrePresenter d'un étudiant pour un créneau.
     *
     * @param session
     * @param e
     * @param c
     * @return
     * @throws Exception si l'étudiant n'est pas dans le créneau.
     */
    public static Etrepresent getPresence(Session session, Etudiant e, Creneau c) {

        String sql = "select * from ETREPRESENT ep where ep.numetudiant = :numetu and ep.codecre= :codecre";

        List<Etrepresent> lstEp = session.createSQLQuery(sql)//
                .addEntity(Etrepresent.class)//
                .setParameter("numetu", e.getNumetudiant())//
                .setParameter("codecre", c.getCodecre())//
                .list();
        return lstEp.get(0);

    }

    /**
     * Pour un étudiant, get la dernière présence pour un cours.
     *
     * @param session
     * @param e
     * @param cours
     * @return
     * @throws Exception
     */
    public static Etrepresent getLastPresence(Session session, Etudiant e, Cours cours) throws Exception {

        return getPresence(session, e, ServiceCreneau.getLastCreneau(session, cours));
    }

    /**
     * Get tous les présences d'un étudiant pour un cours.
     *
     * @param session
     * @param e
     * @param cours
     * @return
     */
    public static ArrayList<Etrepresent> getAllPresence(Session session, Etudiant e, Cours cours) {
        //liste des présences de cet étudiant.
        ArrayList<Etrepresent> lstPre = new ArrayList<Etrepresent>();
        ArrayList<Creneau> lstCre = new ArrayList<>();
        lstCre = ServiceCreneau.getCreneaux(session, cours, e);
        for (Creneau creneau : lstCre) {
            System.out.println(creneau.getCodecre());
            lstPre.add(getPresence(session, e, creneau));
        }
        return lstPre;
    }

    /**
     * get nombre de l'état "présence" pour un cours d'un éetudiant.
     *
     * @param session
     * @param e
     * @param cours
     * @return
     */
    public static int getNbPre(Session session, Etudiant e, Cours cours) {
        int sum = 0;
        ArrayList<Etrepresent> lstPre = getAllPresence(session, e, cours);
        for (Etrepresent pre : lstPre) {
            if (pre.getPresence().equals("PRESENT")) {
                sum += 1;
            }
        }
        return sum;
    }

    /**
     * get nombre de l'état "ABJ" pour un cours d'un éetudiant.
     *
     * @param session
     * @param e
     * @param cours
     * @return
     */
    public static int getNbAbj(Session session, Etudiant e, Cours cours) {
        int sum = 0;
        ArrayList<Etrepresent> lstPre = getAllPresence(session, e, cours);
        for (Etrepresent pre : lstPre) {
            if (pre.getPresence().equals("ABJ")) {
                sum += 1;
            }
        }
        return sum;
    }

    /**
     * get nombre de l'état "ABS" pour un cours d'un éetudiant.
     *
     * @param session
     * @param e
     * @param cours
     * @return
     */
    public static int getNbAbs(Session session, Etudiant e, Cours cours) {
        int sum = 0;
        ArrayList<Etrepresent> lstPre = getAllPresence(session, e, cours);
        for (Etrepresent pre : lstPre) {
            if (pre.getPresence().equals("ABS")) {
                sum += 1;
            }
        }
        return sum;
    }

    /**
     * get nombre de l'état "RETARD" pour un cours d'un éetudiant.
     *
     * @param session
     * @param e
     * @param cours
     * @return
     */
    public static int getNbRetard(Session session, Etudiant e, Cours cours) {
        int sum = 0;
        ArrayList<Etrepresent> lstPre = getAllPresence(session, e, cours);
        for (Etrepresent pre : lstPre) {
            if ("RETARD".equals(pre.getPresence())) {
                sum += 1;
            }
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction t = session.beginTransaction();
//        Cours c = ServiceCours.getCours(session, 1);
//        Etudiant e = ServiceEtudiant.getEtudiant(session, 308);
//
//        System.out.println(e.getNumetudiant());
//        System.out.println("nb présence" + getAllPresence(session, e, c).size());
//
//        for (Etrepresent ep : getAllPresence(session, e, c)) {
//            System.out.println(ep.getPresence());
//        }
//        int nbPre = getNbPre(session, e, c);
//        int nbAbs = services.ServicePresence.getNbAbs(session, e, c);
//        int nbAbj = services.ServicePresence.getNbAbj(session, e, c);
//        int nbR = services.ServicePresence.getNbRetard(session, e, c);
//        System.out.println("<nbpre>" + nbPre + "/" + nbAbs + "/" + nbAbj + "/" + nbR + "</nbpre>");
//        t.commit();

    }
}
