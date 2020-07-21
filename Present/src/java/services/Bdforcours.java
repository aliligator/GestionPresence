/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author Bonso
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Bonso
 */
public class Bdforcours {

    /*---------*/
 /* Données */
 /*---------*/

 /*----- Connexion -----*/
    private static Connection cx = null;

    /*----- Données de connexion -----*/
    private static final String URL = "jdbc:mysql://localhost:3306/gestionpresence";
    private static final String LOGIN = "gestionnaire";
    private static final String PASSWORD = "slucropluvo6";

    /*----------*/
 /* Méthodes */
 /*----------*/
    /**
     * Crée la connexion avec la base de données.
     */
    private static void connexion() throws ClassNotFoundException, SQLException {
        /*----- Chargement du pilote pour la BD -----*/
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Exception connexion() : Pilote MySql introuvable - " + ex.getMessage());
        }

        /*----- Ouverture de la connexion -----*/
        try {
            Bdforcours.cx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException ex) {
            throw new SQLException("Exception connexion() : Problème de connexion à la base de données - " + ex.getMessage());
        }
    }

    /* ---- Afficher les cours de l'enseignant -----*/
    public static ArrayList<String> getCours(int idEnseig) throws ClassNotFoundException, SQLException {
        /*----- Création de la connexion à la base de données -----*/
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> listeCours = new ArrayList<>();
        String sql = "select c.NomC from COURS c, DISPENSER d, ENSEIGNANT e "
                + "where d.CODEC = c.CODEC "
                + "and d.CODEE = e.CODEE "
                + "and  e.CODEE =? ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sql)) {
            /*----- Exécution de la requête -----*/
            st.setString(1, idEnseig + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    listeCours.add(rs.getString(1));
                }
            }

        }
        return listeCours;

    }

    /* ---- Afficher les cours de par formation -----*/
    public static ArrayList<String> getCoursFormation(int idFormation) throws ClassNotFoundException, SQLException {
        /*----- Création de la connexion à la base de données -----*/
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> listeCoursF = new ArrayList<>();
        String sql = "select c.CODEC,c.NomC from COURS c where c.CODEF = ? ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sql)) {
            /*----- Exécution de la requête -----*/
            st.setString(1, idFormation + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    listeCoursF.add(rs.getString(1));
                    listeCoursF.add(rs.getString(2));

                }

            }

        }
        return listeCoursF;

    }

    ;
        /*---Afficher les dates de cours entre la datedébut et la datefin  ---*/
        public static ArrayList<String> getDateCreneau(String nomcours, String datedebut, String datefin) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> listeDateCours = new ArrayList<>();
        String sql2 = "select cr.DATECRE "
                + "from CRENEAU cr, COURS c "
                + "where cr.CODEC = c.CODEC "
                + "and c.NomC = ? "
                + "and cr.DATECRE > ? "
                + "and cr.DATECRE in (select cr2.DATECRE from CRENEAU cr2 where cr2.DATECRE < ?) ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sql2)) {
            /*----- Exécution de la requête -----*/
            st.setString(1, nomcours);
            st.setString(2, datedebut);
            st.setString(3, datefin);
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    listeDateCours.add(rs.getString(1));
                }
            }
        }

        return listeDateCours;
    }

    public static ArrayList<String> getEtudiant(String nomcours, String datedebut, String datefin) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> listeEtudiants = new ArrayList<>();
        String sql3 = " select cr.DATECRE, etu.NOMETU, etu.PRENOMETU, etu.TYPEETU, ep.PRESENCE from ETUDIANT etu, ETREPRESENT ep, CRENEAU cr, COURS c where etu.NUMETUDIANT = ep.NUMETUDIANT "
                + "and ep.CODECRE = cr.CODECRE "
                + "and c.CODEC = cr.CODEC "
                + "and c.NomC = ? "
                + "and cr.DATECRE > ? "
                + "and cr.DATECRE in (select cr2.DATECRE from CRENEAU cr2 where cr2.DATECRE < ?) ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sql3)) {
            st.setString(1, nomcours);
            st.setString(2, datedebut);
            st.setString(3, datefin);
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    listeEtudiants.add(rs.getString(1));
                    listeEtudiants.add(rs.getString(2));
                    listeEtudiants.add(rs.getString(3));
                    listeEtudiants.add(rs.getString(4));
                    listeEtudiants.add(rs.getString(5));
                }
            }
        }
        return listeEtudiants;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<String> s = getEtudiant(1, "2020-03-01", "2020-03-31");
        for (String string : s) {
            System.out.println(string);
        }
    }

    public static ArrayList<String> getEtudiant(int codec, String datedebut, String datefin) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> listeEtudiants = new ArrayList<>();
        String sql3 = " select cr.DATECRE, etu.NOMETU, etu.PRENOMETU, etu.TYPEETU, ep.PRESENCE from ETUDIANT etu, ETREPRESENT ep, CRENEAU cr, COURS c where etu.NUMETUDIANT = ep.NUMETUDIANT "
                + "and ep.CODECRE = cr.CODECRE "
                + "and c.CODEC = cr.CODEC "
                + "and c.CODEC = ? "
                + "and cr.DATECRE > ? "
                + "and cr.DATECRE in (select cr2.DATECRE from CRENEAU cr2 where cr2.DATECRE < ?) ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sql3)) {
            st.setString(1, String.valueOf(codec));
            st.setString(2, datedebut);
            st.setString(3, datefin);
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    listeEtudiants.add(rs.getString(1));
                    listeEtudiants.add(rs.getString(2));
                    listeEtudiants.add(rs.getString(3));
                    listeEtudiants.add(rs.getString(4));
                    listeEtudiants.add(rs.getString(5));
                }
            }
        }
        return listeEtudiants;
    }

    public static ArrayList<String> getGroup(int idEnseig) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> listegroupe = new ArrayList<>();
        String sqlg = "select distinct g.CODEG,g.LIBELLEG from GROUPE g, CORRESPONDRE cs, COURS c, FORMATION f, ETRERESPONSABLE er where g.CODEG = cs.CODEG "
                + "and cs.CODEC = c.CODEC "
                + "and c.CODEF = f.CODEF "
                + "and f.CODEF = er.CODEF "
                + "and er.CODEE = ? "
                + "ORDER BY g.CODEG ASC";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqlg)) {
            /*----- Exécution de la requête -----*/
            st.setString(1, idEnseig + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    //   listegroupe.add(rs.getString(1));
                    listegroupe.add(rs.getString(1));
                    listegroupe.add(rs.getString(2));
                }
            }

        }
        return listegroupe;

    }

    public static ArrayList<String> getEtudiantFormation(int idEnseig) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> etu = new ArrayList<>();
        String sqle = "select etu.NUMETUDIANT,etu.NOMETU, etu.PRENOMETU, etu.TYPEETU from ETUDIANT etu, FORMATION f, ETRERESPONSABLE er where etu.CODEF = f.CODEF "
                + " and f.CODEF = er.CODEF "
                + " and er.CODEE = ? ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqle)) {
            /*----- Exécution de la requête -----*/
            st.setString(1, idEnseig + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    etu.add(rs.getString(1));
                    etu.add(rs.getString(2));
                    etu.add(rs.getString(3));
                    etu.add(rs.getString(4));
                }

            }
        }
        return etu;
    }

    public static ArrayList<String> getEtudiantFormationGroupe(int idEnseig) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> etu = new ArrayList<>();
        String sqle = "select etu.NUMETUDIANT,etu.NOMETU, etu.PRENOMETU, etu.TYPEETU, g.LIBELLEG from ETUDIANT etu, FORMATION f, ETRERESPONSABLE er, APPARTENIRGROUPE ag, GROUPE g where etu.CODEF = f.CODEF "
                + "and f.CODEF = er.CODEF "
                + "and etu.NUMETUDIANT = ag.NUMETUDIANT "
                + "and ag.CODEG = g.CODEG "
                + "and er.CODEE = ? ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqle)) {
            /*----- Exécution de la requête -----*/
            st.setString(1, idEnseig + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    etu.add(rs.getString(1));
                    etu.add(rs.getString(2));
                    etu.add(rs.getString(3));
                    etu.add(rs.getString(4));
                    etu.add(rs.getString(5));
                }

            }
        }
        return etu;
    }

    public static int supprimer(int idG) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        String sqld = "Delete from APPARTENIRGROUPE where CODEG = ? ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqld)) {
            st.setInt(1, idG);
            return st.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Exception supprimer()  : Problème SQL -  " + ex.getMessage());

        }

    }

    public static int affecterEtudianrGroupe(int idEtu, int idG) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        String sqla = "insert into APPARTENIRGROUPE(NUMETUDIANT, CODEG) values(?,?)";
        // Ouverture de l'espace de requête
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqla, Statement.RETURN_GENERATED_KEYS)) {
            /*----- Insertion du message -----*/
            st.setInt(1, idEtu);
            st.setInt(2, idG);
            st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);

            }
        }
        return 0;

    }

    public static ArrayList<String> getEtudiantchoisitemporaire(int idEtu) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> ec = new ArrayList<>();
        String sqle2 = "select e.NOMETU, e.PRENOMETU from ETUDIANT e where e.NUMETUDIANT = ?";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqle2)) {
            st.setString(1, idEtu + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    ec.add(rs.getString(1));
                    ec.add(rs.getString(2));
                }
            }
        }
        return ec;

    }

    public static ArrayList<String> getEtudiantparGroupe(int idG) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> eg = new ArrayList<>();
        String sqlg = "select e.NOMETU, e.PRENOMETU from ETUDIANT e, APPARTENIRGROUPE ap where e.NUMETUDIANT = ap.NUMETUDIANT "
                + "and ap.CODEG = ? ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqlg)) {
            st.setString(1, idG + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    eg.add(rs.getString(1));
                    eg.add(rs.getString(2));
                }
            }
        }
        return eg;

    }

    public static ArrayList<String> getFormation(int idEnseig) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        ArrayList<String> f = new ArrayList<>();
        String sqlf = "select f.CODEF,f.NOMF from FORMATION f, ETRERESPONSABLE er where f.CODEF = er.CODEF "
                + "and er.CODEE = ? ";
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqlf)) {
            st.setString(1, idEnseig + "");
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) {
                    f.add(rs.getString(1));
                    f.add(rs.getString(2));
                }
            }
        }
        return f;

    }

    public static int AjouterGroupe(String libelleG) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        String sqlag = "INSERT INTO GROUPE(LIBELLEG) VALUES(?) ";
        // Ouverture de l'espace de requête
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqlag, Statement.RETURN_GENERATED_KEYS)) {
            /*----- Insertion du message -----*/
            st.setString(1, libelleG);
            st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);

            }
        }
        return 0;
    }

    public static int AjouterAssociation(int idC, int idG) throws ClassNotFoundException, SQLException {
        if (Bdforcours.cx == null) {
            Bdforcours.connexion();
        }
        String sqlag = "INSERT INTO CORRESPONDRE(CODEC,CODEG) VALUES(?,?) ";
        // Ouverture de l'espace de requête
        try (PreparedStatement st = Bdforcours.cx.prepareStatement(sqlag)) {
            /*----- Insertion du message -----*/
            st.setInt(1, idC);
            st.setInt(2, idG);
            st.executeUpdate();
        }
        return 0;

    }

}
