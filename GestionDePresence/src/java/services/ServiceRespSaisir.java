/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * les functions pour l'ajout ou la modification d'un créneau par responsable
 * les functions pour upload et download de fichier des etudiants
 *
 * @author yuxuan
 */
public class ServiceRespSaisir {

    //méthode pour afficher le nom et prénom de responsable
    public static String retrouverResoponsable(int idResponsable) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Enseignant e = (Enseignant) session.get(Enseignant.class, idResponsable);
        return e.getNome() + " " + e.getPrenome();

    }

    //méthode pour afficher les créneaus créés par le responsable 
    public static ArrayList<Creneau> listeCreneau(Session session, int idResponsable, String datechoisi) {

        Query query = session.createQuery("from Creneau c where c.enseignant.codee = :coderes and c.datecre = :date "
                + "and c.typeactivitecre in(:projet,:conf) order by c.heuredeb ASC");
        query.setParameter("coderes", idResponsable);
        query.setParameter("date", datechoisi);
        query.setParameter("projet", "projet");
        query.setParameter("conf", "conference");
        ArrayList<Creneau> lcr = (ArrayList<Creneau>) query.list();
        return lcr;
    }

    //méthode pour trouver les formations gérées par le responsable 
    public static Set<Formation> listFormation(Session session, int idResponsable) {
        Enseignant e = (Enseignant) session.get(Enseignant.class, idResponsable);
        Set<Formation> lf = e.getFormations();
        return lf;
    }

    //méthode pour afficher les groupes des formations gérées par le responsable 
    public static Set<Groupe> listeGroup(Session session, int idResponsable) {
        Query query1 = session.createQuery("from Etudiant etu where etu.formation.codef = :cdf");
        query1.setParameter("cdf", idResponsable);
        List<Etudiant> letu = query1.list();
        Set<Groupe> grps = new HashSet<Groupe>();
        for (Etudiant etu : letu) {
            //int idetu = etu.getNumetudiant();
            Query query2 = session.createQuery("select distinct a.groupe from Appartenirgroupe a where a.etudiant = :etudiant ");
            query2.setParameter("etudiant", etu);
            List<Groupe> apgrps = query2.list();
            for (Groupe aps : apgrps) {
                grps.add(aps);
            }
        }
        return grps;
    }

    // méthode pour ajouter un créneau  
    public static void ajoutCreneau(Session session, String nom, String typeAct, int idGrp, String date, String heure, String dur, int idRespon) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfh = new SimpleDateFormat("HH:mm");
        //creer nouveau creneau
        //Cours cours, String nomcre, Enseignant enseignant, Groupe groupe, Date datecre, Long dureecre, Date heuredeb, String typeactivitecre
        Cours c = null;
        Enseignant e = (Enseignant) session.load(Enseignant.class, idRespon);
        Groupe g = (Groupe) session.load(Groupe.class, idGrp);
        //heure fin - debut
        Long duree = Long.parseLong(dur.substring(0, 2)) * 60L + Long.parseLong(dur.substring(4)) - Long.parseLong(heure.substring(0, 2)) * 60L - Long.parseLong(heure.substring(4));
        Creneau c1 = new Creneau(c, nom, e, g, df.parse(date), duree, dfh.parse(heure), typeAct);
        session.save(c1);
    }

    public static void upCreneau(Session session, int idCreneau, String nom, String typeAct, int idGrp, String date, String heure, String dur) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfh = new SimpleDateFormat("HH:mm");
        //Creneau
        Creneau creup = (Creneau) session.load(Creneau.class, idCreneau);
        //Groupe 
        Groupe g = (Groupe) session.load(Groupe.class, idGrp);
        Long mindebut = Long.parseLong(heure.substring(0, 2)) * 60L + Long.parseLong(heure.substring(3, 5));
        Long minfin = Long.parseLong(dur.substring(0, 2)) * 60L + Long.parseLong(dur.substring(3, 5));
        Long dure = minfin - mindebut;
        //Update
        creup.setNomcre(nom);
        creup.setTypeactivitecre(typeAct);
        creup.setGroupe(g);
        creup.setDatecre(df.parse(date));
        creup.setHeuredeb(dfh.parse(heure));
        creup.setDureecre(dure);

    }

    //méthode pour examiner le conflit entre les créneau et le créneau ajouté
    //conflit de temps et group
    public static int exmainerCreneau(Session session, String date, String heure, String dur, int idGrp, int idRespon) throws ParseException {
        int estconflit = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfh = new SimpleDateFormat("HH:mm");

        Long mindebutnew = Long.parseLong(heure.substring(0, 2)) * 60L + Long.parseLong(heure.substring(3, 5));
        Long minfinnew = Long.parseLong(dur.substring(0, 2)) * 60L + Long.parseLong(dur.substring(3, 5));
        System.out.println(mindebutnew);
        System.out.println(minfinnew);
        //liste de Créneau crées par ce responsable
        Query query = session.createQuery("from Creneau c where c.enseignant.codee = :idRes and c.typeactivitecre in (:projet,:conf)");
        query.setParameter("idRes", idRespon);
        query.setParameter("projet", "projet");
        query.setParameter("conf", "conference");
        List<Creneau> listcre = query.list();
        Groupe g = (Groupe) session.load(Groupe.class, idGrp);
        for (Creneau cre : listcre) {
            Date dateStr = cre.getDatecre();
            Groupe gcre = cre.getGroupe();
            Date hcre = cre.getHeuredeb();
            String hcreStr = dfh.format(hcre);
            Long mindebut = Long.parseLong(hcreStr.substring(0, 2)) * 60L + Long.parseLong(hcreStr.substring(3, 5));
            Long midur = cre.getDureecre();
            Long minfin = mindebut + midur;
            if ((dateStr.equals(df.parse(date))) && (gcre.equals(g))) {
                //heure debet de nouveau crenau au mi
                if (mindebut <= mindebutnew && mindebutnew < minfin) {
                    estconflit = 1;
                }
                if (mindebutnew < mindebut && mindebut < minfinnew) {
                    estconflit = 1;
                }
            }
        }
        return estconflit;
    }

    // deux methodes pour afficher etudiants et leurs fichiers individuels 
    public static Set<Etudiant> lEtudiant(Session session, int idResponsable) {
        Set<Etudiant> letus = new HashSet<Etudiant>();
        //touver formation de responsable 
        Enseignant en = (Enseignant) session.load(Enseignant.class, idResponsable);
        Set<Formation> formas = en.getFormations();
        for (Formation fo : formas) {
            //Trouver tous les etudiant de cette formation
            Query query = session.createQuery("from Etudiant e where e.formation = :formation order by e.numetudiant ASC");
            query.setParameter("formation", fo);
            List<Etudiant> etus = query.list();
            for (Etudiant e : etus) {
                letus.add(e);
            }
        }
        return letus;
    }

    public static Ficheindividuelle lficher_etu(Etudiant etu, String mois, String annee) {
        Ficheindividuelle ficher = null;
        Set<Ficheindividuelle> fichers = etu.getFicheindividuelles();
        for (Ficheindividuelle fi : fichers) {
            if (fi.getAnneefiche().equals(annee) && fi.getMoisfiche().equals(mois)) {
                ficher = fi;
            }
        }
        return ficher;
    }

    //Lien avec données
    private static final String URL = "jdbc:mysql://localhost:3306/db_21701558";
    private static final String LOGIN = "21701558";
    private static final String PASSWORD = "02343W";

    private static Connection cx;

    //Conexion à la BD
    private static void connextion() throws Exception {
        //chargement du pilote
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(Bd.class.getName()).log(Level.SEVERE, null, ex);
            // pour développeurs afficher des errors : trâces 
            throw new Exception("Exception connexion -" + ex.getMessage());
        }

        //Ouverture de la connexion
        try {
            ServiceRespSaisir.cx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException sqle) {
            throw new Exception("Exception connection -" + sqle.getMessage());
        }

    }

    //méthode pour supprmier un créneau
    public static int suppCreneau(int idCreneau) throws Exception {
        if (ServiceRespSaisir.cx == null) {
            ServiceRespSaisir.connextion();
        }

        int res = 0;
        //Requete SQL
        String sql = "DELETE FROM CRENEAU WHERE CRENEAU.CODECRE = ?";

        try (PreparedStatement st = ServiceRespSaisir.cx.prepareStatement(sql)) {
            //Mise a juor des parametres de la requete et insertion
            st.setInt(1, idCreneau);
            res = st.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Execption enregistreMessages() - " + sqle.getMessage());
        }

        return res;
    }

}
