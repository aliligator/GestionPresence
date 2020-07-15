/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.Appartenirgroupe;
import bd.Etudiant;
import bd.Formation;
import bd.Groupe;
import bd.HibernateUtil;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yeping
 */
public class ServiceEtudiant {

    static public ArrayList<Etudiant> getAllEtudiants(Session session) {
        String hql = "from Etudiant";
        Query query = session.createQuery(hql);
        List<Etudiant> lstEtu = query.list();
        return (ArrayList<Etudiant>) lstEtu;
    }

    static public ArrayList<Etudiant> getEtudiants(Session session, Groupe groupe) {
        String sql = "select * from etudiant e, appartenirgroupe a where a.numetudiant = e.numetudiant and a.codeg =?";
        List<Etudiant> lstE = session.createSQLQuery(sql)//
                .addEntity(Etudiant.class)//
                .setParameter(0, groupe.getCodeg())//
                .list();
        return (ArrayList<Etudiant>) lstE;
    }

    /**
     * retourner si un étudiant est dans le groupe.
     *
     * @param session
     * @param numEtu
     * @param g
     * @return
     */
    static public int estDansGroupe(Session session, int numEtu, Groupe g) {
        String sql = "select * from appartenirgroupe ap where ap.numetudiant = ? and ap.codeg =?";
        List<Appartenirgroupe> lstE = session.createSQLQuery(sql)//
                .addEntity(Appartenirgroupe.class)//
                .setParameter(0, numEtu).
                setParameter(1, g.getCodeg())//
                .list();
        if (lstE == null || lstE.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    static public ArrayList<Etudiant> getEtudiants(Session session, Formation formation) {
        String sql = "select * from etudiant e where e.codef=?";
        List<Etudiant> lstE = session.createSQLQuery(sql)//
                .addEntity(Etudiant.class)//
                .setParameter(0, formation.getCodef())//
                .list();
        return (ArrayList<Etudiant>) lstE;
    }

    /**
     * pour un étudiant, une durée donnée(heuredeb-->heureFIn), dans un jour
     * trouver les heures totaux d'un état de présence.
     *
     * @param session
     * @param etu
     * @param timeDeb
     * @param timeFin
     * @param date
     * @param typeCreneau documentation ou cours
     * @param Presence "PRESENCE","ABS","ABJ","RETARD"
     * @return min totaux ou 0
     */
    static public Long getMinTotalPre(Session session, Etudiant etu, Time timeDeb, Time timeFin, Date date, String Presence, String typeCreneau) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        int annee = date.getYear();
        int mois = date.getMonth();
        int jour = date.getDate();
        String sql = "";
        if (typeCreneau.equals("documentation")) {
            sql = "SELECT SUM(DUREECRE)\n"
                    + "FROM creneau\n"
                    + "WHERE CODECRE in"
                    + "(select distinct cre.CODECRE\n"
                    + "from creneau cre, cours c,etreprersenter pre \n"
                    + "where cre.CODECRE=pre.CODECRE\n"
                    + "and cre.HEUREDEB>=?\n"
                    + "and cre.HEUREDEB<=?\n"
                    + "and cre.typeactivitecre in (\"projet\",\"conference\")" + "\n"
                    + "and pre.NUMETUDIANT=" + etu.getNumetudiant() + "\n "
                    + "and month(cre.datecre)=" + mois + "\n"
                    + "and year(cre.datecre)=" + annee + "\n"
                    + "and day(cre.datecre)=" + jour + "\n"
                    + "and pre.presence=? )";
        } else if (typeCreneau.equals("cours")) {
            sql = "SELECT SUM(DUREECRE)\n"
                    + "FROM creneau\n"
                    + "WHERE CODECRE in"
                    + "(select distinct cre.CODECRE\n"
                    + "from creneau cre, etreprersenter pre \n"
                    + "where cre.CODECRE=pre.CODECRE\n"
                    + "and cre.HEUREDEB>=?\n"
                    + "and cre.HEUREDEB<=?\n"
                    + "and cre.typeactivitecre in (\"cours\",\"seance\")" + "\n"
                    + "and pre.NUMETUDIANT=" + etu.getNumetudiant() + "\n "
                    + "and month(cre.datecre)=" + mois + "\n"
                    + "and year(cre.datecre)=" + annee + "\n"
                    + "and day(cre.datecre)=" + jour + "\n"
                    + "and pre.presence=?)";
        }
        String hDeb = dateFormat.format(timeDeb);
        String hFin = dateFormat.format(timeFin);

        List<BigDecimal> rsl = new ArrayList<BigDecimal>();

        rsl = (List<BigDecimal>) session.createSQLQuery(sql)
                .setString(0, hDeb)
                .setString(1, hFin)
                .setString(2, Presence)//
                .list();

        return rsl.get(0) == null ? 0L : rsl.get(0).longValue();
    }

    public static void main(String[] args) {
        Session sessH = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = sessH.beginTransaction();
        Etudiant e = (Etudiant) sessH.load(Etudiant.class, 289);

        System.out.println(getMinTotalPre(sessH, e, new Time(8, 0, 0), new Time(14, 0, 0), new Date(2020, 3, 1), "PRESENCE", "documentation"));
//        Groupe g = (Groupe) sessH.load(Groupe.class, 3);
//        System.out.println(estDansGroupe(sessH, 1, g));
//        System.out.println(getMinTotalPre(sessH, e, new Time(14, 0, 0), new Time(17, 0, 0), new Date(2020, 3, 28), "ABS", "cours"));
        //Set<Etreprersenter> lstP = es.get(0).getEtreprersenters();
        t.commit();
//       Iterator<Etreprersenter> iterator = lstP.iterator();
//        
//        for (iterator; iterator.hasNext()) {
//            Etreprersenter next = iterator.next();
//            
//        }
//               gs.add( serviceGroupe.getGroupe(sessH, 2));
//        for (Etudiant e : getEtudiants(sessH, serviceGroupe.getGroupe(sessH, 3))) {
//            System.out.println(e.getNometu());
//        }
//        for (Etudiant e : getEtudiants(sessH, serviceFormation.getFormation(sessH, 1))) {
//            System.out.println(e.getNometu());
//        }

    }

}
