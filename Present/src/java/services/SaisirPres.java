/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.Creneau;
import bd.Enseignant;
import bd.Etrepresent;
import bd.EtrepresentId;
import bd.Etudiant;
import bd.Ficheindividuelle;
import bd.Formation;
import bd.Groupe;
import bd.HibernateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Les fonctions pour réaliser la déclaration de précences des activités pour
 * étudiant
 *
 * @author yuxuan
 */
public class SaisirPres {

    public static String retrouverEtudiant(int idEtudiant) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Etudiant e = (Etudiant) session.load(Etudiant.class, idEtudiant);
        return e.getNometu() + " " + e.getPrenometu();

    }

    public static void saisirPres(Session session, int idEtudiant, int idCreneau) {
        // Etrepresenceid
        //int codecre, int numetudiant
        EtrepresentId etreID = new EtrepresentId(idCreneau, idEtudiant);
        //EtrepresentId id, Creneau creneau, Etudiant etudiant
        Etudiant etu = (Etudiant) session.load(Etudiant.class, idEtudiant);
        Creneau cre = (Creneau) session.load(Creneau.class, idCreneau);
        Etrepresent etresaisir = new Etrepresent(etreID, cre, etu, "PRESENT");
        session.save(etresaisir);

    }

    public static Set<Creneau> droitPresence(Session session, int idEtudiant, String dateChoisiStr) throws ParseException {

        Set<Creneau> lcres = new HashSet<Creneau>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //Touver la formation de ce Etudiant
        Etudiant etu = (Etudiant) session.load(Etudiant.class, idEtudiant);
        Formation forma = etu.getFormation();
        //Trouver responsable de cette formation 
        Set<Enseignant> ens = forma.getEnseignants();
        //Trouver les groups qu'il appartient
        Query query = session.createQuery("select a.groupe from Appartenirgroupe a where a.etudiant = :etu");
        query.setParameter("etu", etu);
        List<Groupe> grps = query.list();
        //Trouver les créneaus crées par le responsable de la formation et pour un groupe
        for (Enseignant en : ens) {
            for (Groupe grp : grps) {
                Query query1 = session.createQuery("from Creneau c where c.enseignant = :en and c.datecre = :datecre and "
                        + "c.groupe = :grp and c.typeactivitecre in(:projet,:conf) order by c.heuredeb ASC");
                query1.setParameter("en", en);
                query1.setParameter("grp", grp);
                query1.setParameter("datecre", df.parse(dateChoisiStr));
                query1.setParameter("projet", "projet");
                query1.setParameter("conf", "conference");
                List<Creneau> cres = query1.list();
                for (Creneau cr : cres) {
                    lcres.add(cr);
                }
            }
        }
        for (Creneau cre : lcres) {
            Iterator<Etrepresent> iterator = cre.getEtrepresents().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getEtudiant().getNumetudiant() == idEtudiant) {
                    lcres.remove(cre);
                }
            }
        }

        return lcres;
    }

    // liste de fichiers individuelless de cet etuidiant
    public static List<Ficheindividuelle> listeFichier(Session session, int idEtu, String annee) {
        Etudiant etu = (Etudiant) session.load(Etudiant.class, idEtu);
        Query query = session.createQuery("from Ficheindividuelle f where f.etudiant = :etudiant and f.anneefiche = :annee ");
        query.setParameter("etudiant", etu);
        query.setParameter("annee", annee);
        List<Ficheindividuelle> fichiers = query.list();
        return fichiers;
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat dfh = new SimpleDateFormat("HH:mm:ss");
        /*----- Ouverture de la session et de la transaction -----*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

        Etudiant etu = (Etudiant) session.load(Etudiant.class, 1);
        List<Ficheindividuelle> fichiers = listeFichier(session, etu.getNumetudiant(), "2020");
        for (Ficheindividuelle f : fichiers) {
            System.out.println(f.getUrlfic().substring(26));
        }

    }
}
