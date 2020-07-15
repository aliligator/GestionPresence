/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.Enseignant;
import bd.Formation;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author yeping
 */
public class ServiceFormation {

    static public ArrayList<Formation> getAllFormation(Session session) {
        String hql = "from Formation ";
        Query query = session.createQuery(hql);
        List<Formation> lstF = query.list();
        return (ArrayList<Formation>) lstF;
    }

    static public ArrayList<Formation> getFormation(Session session, Enseignant ens) {
        String sql = "select * from formation f , etreresponsable er where f.codef=er.codef and er.codee=?";
        Query query = session.createSQLQuery(sql).addEntity(Formation.class).setParameter(0, ens.getCodee());
        List<Formation> lstF = query.list();
        return (ArrayList<Formation>) lstF;
    }

    public static void main(String[] args) {

//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction t = session.beginTransaction();
//        Formation f = getFormation(session, 1);
//        for (Iterator iterator = f.getEtudiants().iterator(); iterator.hasNext();) {
//            Etudiant next = (Etudiant) iterator.next();
//            System.out.println(next.getNometu());
//        }
//
//        t.commit();

    }
}
