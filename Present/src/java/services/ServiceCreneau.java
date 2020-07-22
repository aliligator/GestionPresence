/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.Cours;
import bd.Creneau;
import bd.Enseignant;
import bd.Etudiant;
import bd.Groupe;
import bd.HibernateUtil;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yeping
 */
public class ServiceCreneau {

    public void creerCreneau(Cours cours, Enseignant enseignant, Groupe groupe, Date datecre, Long dureecre, Date heuredeb, String typeactivitecre, Set Etrepresents) {

    }

    static public String getHeureFin(Creneau cre) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        int minute = cre.getDureecre();
        Time d = new Time(cre.getHeuredeb().getTime() + minute * 60 * 1000);
        String ds = df.format(d);
        return ds;
    }

    /**
     * get les créneaux d'un cours.
     *
     * @param session
     * @param cours
     * @return
     */
    static public ArrayList<Creneau> getCreneaux(Session session, Cours cours) {
        String sql = "select * from CRENEAU cre where cre.CODEC = ?";
        List<Creneau> lstE = session.createSQLQuery(sql)//
                .addEntity(Creneau.class)//
                .setParameter(0, cours.getCodec())//
                .list();
        return (ArrayList<Creneau>) lstE;
    }

    static public Creneau getLastCreneau(Session session, Cours cours) {
        String sql1 = "select * from CRENEAU cre where cre.CODEC = ? order by CODECRE desc LIMIT 1 ";
        Creneau lastCre = (Creneau) session.createSQLQuery(sql1)//
                .addEntity(Creneau.class)//
                .setParameter(0, cours.getCodec())//
                .uniqueResult();
        return lastCre;

    }

    /**
     * get les creneaux d'un cours pour un groupe lequel appartient un etudiant.
     *
     * @param session
     * @param cours
     * @param e
     * @return
     */
    static public ArrayList<Creneau> getCreneaux(Session session, Cours cours, Etudiant e) {
        // les creneau que cet etudiant participe
        String sql1 = "select * from creneau c, Etrepresent pre where c.codec= :codec and pre.NUMETUDIANT= :nume and  pre.codecre=c.codecre";

        List<Creneau> lstCre = session.createSQLQuery(sql1)//
                .addEntity(Creneau.class)//
                .setParameter("codec", cours.getCodec())//
                .setParameter("nume", e.getNumetudiant())//
                .list();
        return (ArrayList<Creneau>) lstCre;

    }

    /**
     * get les créneaux dans la date choisi.
     *
     * @param session
     * @param datechoisi
     * @return
     */
    public static ArrayList<Creneau> listeCreneau(Session session, String datechoisi) {

        Query query = session.createQuery("from Creneau c where  c.datecre = :date");
        query.setParameter("date", datechoisi);
        ArrayList<Creneau> lcr = (ArrayList<Creneau>) query.list();
        return lcr;
    }

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        Cours c = (Cours) session.get(Cours.class, 1);

        Etudiant e = (Etudiant) session.get(Etudiant.class, 308);
        ArrayList<Creneau> lstcre = getCreneaux(session, c, e);
        System.out.println(lstcre.size());

        System.out.println(c.getCodec());
        t.commit();
    }
}
