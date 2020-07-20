/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.Cours;
import bd.Enseignant;
import bd.Formation;
import bd.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yeping
 * static public Cours getCours(Session session, int codeC) {
 * String hql = "from Cours c where c.codec = ?"; Query query =
 * session.createQuery(hql).setParameter(0, codeC); List<Cours> lstC =
 * query.list(); return lstC.get(0); }
 */
public class ServiceCours {

    static public ArrayList<Cours> getAllCours(Session session) {
        String hql = "from Cours ";
        Query query = session.createQuery(hql);
        List<Cours> lstC = query.list();
        return (ArrayList<Cours>) lstC;
    }

    static public ArrayList<Cours> getAllCours(Session session, Enseignant ens) {
        String hql = "select c from Cours c, Enseignant e where e.codee=? and c.codec in elements(e.courses)";
        Query query = session.createQuery(hql);
        query.setInteger(0, ens.getCodee());
        List<Cours> lstC = query.list();
        return (ArrayList<Cours>) lstC;
    }
    
    //test pour essayer d'afficher que les cours de laformation sélectionnée
    static public ArrayList<Cours> getAllCoursF(Session session, Enseignant ens, Formation f) {
        String hql = "select c from Cours c, Enseignant e where e.codee=? and c.codec in elements(e.courses) and c.codef=?";
        Query query = session.createQuery(hql);
        query.setInteger(0, ens.getCodee());
        List<Cours> lstC = query.list();
        return (ArrayList<Cours>) lstC;
    }

    static public ArrayList<Cours> getAllCours(Session session, Formation f) {
        String hql = "select c from Cours c,  where c.formation=? ";
        Query query = session.createQuery(hql);
        query.setInteger(0, f.getCodef());
        List<Cours> lstC = query.list();
        return (ArrayList<Cours>) lstC;
    }



    public static void main(String[] args) {
        Session sessH = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = sessH.beginTransaction();
        Enseignant e = (Enseignant) sessH.get(Enseignant.class, 1);
        System.out.println(e.getNome());
        for (Cours c : getAllCours(sessH, e)) {
            System.out.println(c.getNomc());
        }

        t.commit();
    }
}
