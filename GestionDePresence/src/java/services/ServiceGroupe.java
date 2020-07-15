/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.Appartenirgroupe;
import bd.Cours;
import bd.Enseignant;
import bd.Formation;
import bd.Groupe;
import bd.HibernateUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yeping
 */
public class ServiceGroupe {

    static public ArrayList<Groupe> getAllGroupe(Session session) {
        String hql = "from Groupe ";
        Query query = session.createQuery(hql);
        List<Groupe> lstG = query.list();
        return (ArrayList<Groupe>) lstG;
    }

    static public ArrayList<Groupe> getAllGroupe(Session session, Cours cours) {
        String hql = "select g from Cours c, Groupe g where c.codec=? and g.codeg in elements(c.groupes)";
        Query query = session.createQuery(hql);
        query.setInteger(0, cours.getCodec());
        List<Groupe> lstG = query.list();
        return (ArrayList<Groupe>) lstG;
    }

    static public ArrayList<Appartenirgroupe> getAllAppartenirGroupe() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        String hql = "from AppartenirGroupe";
        Query query = session.createQuery(hql);
        List<Appartenirgroupe> lstA = query.list();
        t.commit();
        return (ArrayList<Appartenirgroupe>) lstA;
    }

    static public ArrayList<Groupe> getAllGroupe(Session sessH, Enseignant ens) {
        ArrayList<Formation> lstF = ServiceFormation.getFormation(sessH, ens);
        Set<Groupe> lstG = new HashSet<Groupe>();
        for (Formation f : lstF) {
            ArrayList<Cours> lstC = ServiceCours.getAllCours(sessH, f);
            for (Cours c : lstC) {
                ArrayList<Groupe> g = getAllGroupe(sessH, c);
                for (Groupe groupe : g) {
                    lstG.add(groupe);
                }
            }
        }
        ArrayList<Groupe> arrayG = new ArrayList<Groupe>(lstG);
        return arrayG;
    }

    public static void main(String[] args) {
//        Session sessH = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction t = sessH.beginTransaction();
//
//        Enseignant ens = (Enseignant) sessH.get(Enseignant.class, 1);
//
//        System.out.println(c.getNomc());
//        for (Groupe g : getAllGroupe(sessH, c)) {
//            System.out.println(g.getLibelleg());
//        }
//
//        t.commit();
    }
}
