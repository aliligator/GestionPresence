/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.gestionfichier.gestioncsv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

/**
 *
 * @author MIAGE UT1
 */
public class Importations {

    private static Connection cx = null;

    private static final String URL = "jdbc:mysql://localhost:3306/db_21701558?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "";

    //Connexion à la bd 
    private static void connexion() throws ClassNotFoundException, SQLException {
        /*----- Chargement du pilote pour la BD -----*/
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Exception connexion() : Pilote MySql introuvable - " + ex.getMessage());
        }

        /*----- Ouverture de la connexion -----*/
        try {
            cx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException ex) {
            throw new SQLException("Exception connexion() : Problème de connexion à la base de données - " + ex.getMessage());
        }
    }

    public static int retournerIdFormation(int idEnseignant) throws ClassNotFoundException, SQLException {
        if (cx == null) {
            connexion();
        }

        String sql = "SELECT CODEF FROM etreresponsable WHERE CODEE = ?";

        ArrayList<Integer> liste = new ArrayList<>();

        try (PreparedStatement st = cx.prepareStatement(sql)) {
            /*----- Exécution de la requête -----*/
            st.setInt(1, idEnseignant);
            try (ResultSet rs = st.executeQuery()) {
                /*----- Lecture du contenu du ResultSet -----*/
                while (rs.next()) //System.out.println(rs.getString(1) +" " + rs.getString(2));
                {
                    liste.add(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            // Il faudrait tester si la connexion est ouverte ????????????
            throw new SQLException("Exception lireCitations() : Problème SQL - " + ex.getMessage());
        }
        int idFormation = liste.get(0);
        return idFormation;
    }

    //Ajoute les cours dans la bd selon un identifiant de formation
    public static void remplirCours(int idFormation, String lienExcel) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

        if (cx == null) {
            connexion();
        }
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(lienExcel));
        ArrayList<String> listeCours = new ArrayList<>();
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(0);

        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();
        int cols = 0; // No of columns

        //Remplit la liste des cours dans une array liste et remplit la liste de cours
        for (int i = 1; i < rows; i++) {
            cell = sheet.getRow(i).getCell(1);
            listeCours.add(cell.getStringCellValue());
        }

        //Insère dans la table cours tout les cours
        for (String cours : listeCours) {

            String sql = "INSERT INTO COURS VALUES (0,?,?,null,null)";

            try (PreparedStatement st = cx.prepareStatement(sql)) {
                /*----- Exécution de la requête -----*/
                st.setString(1, cours);
                st.setInt(2, idFormation);
                st.executeUpdate();
            } catch (SQLException ex) {
                // Il faudrait tester si la connexion est ouverte ????????????
                throw new SQLException("Exception remplir cours() : Problème SQL - " + ex.getMessage());
            }
        }

    }
    //Insère dans la base de données les étudiants 

    public static void remplirEtudiant(int idFormation, String lienExcel) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

        if (cx == null) {
            connexion();
        }
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(lienExcel));
        ArrayList<Integer> numEtu = new ArrayList<>();
        ArrayList<String> nomEtu = new ArrayList<>();
        ArrayList<String> prenomEtu = new ArrayList<>();
        ArrayList<String> typeFormation = new ArrayList<>();
        ArrayList<String> societe = new ArrayList<>();
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(0);

        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();
        int cols = 0; // No of columns

        //Récupère les informations des étudiants dans plusieurs arrayList
        for (int i = 1; i < rows; i++) {
            cell = sheet.getRow(i).getCell(0);
            HSSFCell cellnom = sheet.getRow(i).getCell(1);
            HSSFCell cellprenom = sheet.getRow(i).getCell(2);
            HSSFCell cellformation = sheet.getRow(i).getCell(3);
            HSSFCell cellsociete = sheet.getRow(i).getCell(4);
            //Gérer exception Int
            try {
                cell.setCellType(CellType.STRING);
                int result = Integer.parseInt(cell.getStringCellValue());
                numEtu.add(result);
            } catch (NullPointerException NPE) {
                numEtu.add(0);
            }

            nomEtu.add(cellnom.getStringCellValue());
            prenomEtu.add(cellprenom.getStringCellValue());
            typeFormation.add(cellformation.getStringCellValue());

            try {
                societe.add(cellsociete.getStringCellValue());
            } catch (NullPointerException NPE) {
                societe.add("NON");
            }
        }
        System.out.println("numEtu " + numEtu.size());
        System.out.println("nomEtu " + nomEtu.size());
        System.out.println("prenomEtu " + prenomEtu.size());
        System.out.println("TypeFormation " + typeFormation.size());
        System.out.println("Societe " + societe.size());
        System.out.println("Num etu " + numEtu.size());
        System.out.println("nb ligne " + rows);
        //Insère dans la table cours tout les cours
        for (int i = 1; i < rows - 1; i++) {

            String sql = "INSERT INTO ETUDIANT  (NUMETUDIANT,CODEF,MAILETU,MDPETU,NOMETU,PRENOMETU,PHOTOETU,ADRESSESOC,MAILSOC,RAISONSOC,TYPEETU)  VALUES (0,?,?,?,?,?,'default.jpg',null,null,?,?)";

            try (PreparedStatement st = cx.prepareStatement(sql)) {

                //st.setInt(1, numEtu.get(i));
                st.setInt(1, idFormation);
                st.setString(2, prenomEtu.get(i).toLowerCase() + "." + nomEtu.get(i).toLowerCase() + "@ut-capitole.fr");
                st.setString(3, "abc1234");
                st.setString(4, nomEtu.get(i));
                st.setString(5, prenomEtu.get(i));
                st.setString(6, societe.get(i));
                st.setString(7, typeFormation.get(i));
                st.executeUpdate();
            } catch (SQLException ex) {
                // Il faudrait tester si la connexion est ouverte ????????????
                throw new SQLException("Exception remplir cours() : Problème SQL - " + ex.getMessage());
            }
        }

    }

    public static void ajouterPeriode(int idFormation, String lienExcel) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException, ParseException {

        if (cx == null) {
            connexion();
        }
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(lienExcel));
        ArrayList<java.sql.Date> dateDebut = new ArrayList<>();
        ArrayList<java.sql.Date> dateFin = new ArrayList<>();
        ArrayList<String> libPeriode = new ArrayList<>();
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);

        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();
        int cols = 0; // No of columns

        //Récupère les informations des périodes
        // Ajouter test si format de cellule est bien date
        for (int i = 1; i < rows; i++) {
            HSSFCell celldateDeb = sheet.getRow(i).getCell(1);
            HSSFCell celldateFin = sheet.getRow(i).getCell(2);
            HSSFCell cellLibPeriode = sheet.getRow(i).getCell(3);
            //Gérer exception Int
            try {
                Date ddeb = celldateDeb.getDateCellValue();
                java.sql.Date sqlDate = new java.sql.Date(ddeb.getTime());
                dateDebut.add(sqlDate);
            } catch (NullPointerException NPE) {
                String ddeb = "00/00/0000";
                Date datedeb = new SimpleDateFormat("dd/MM/yyyy").parse(ddeb);
                java.sql.Date sqlDate = new java.sql.Date(datedeb.getTime());
                dateDebut.add(sqlDate);
            }

            try {
                Date dfin = celldateFin.getDateCellValue();
                //Date datefin =new SimpleDateFormat("dd/MM/yyyy").parse(dfin);
                java.sql.Date sqlDate = new java.sql.Date(dfin.getTime());
                dateFin.add(sqlDate);
            } catch (NullPointerException NPE) {
                String dfin = "00/00/0000";
                Date datefin = new SimpleDateFormat("dd/MM/yyyy").parse(dfin);
                java.sql.Date sqlDate = new java.sql.Date(datefin.getTime());
                dateDebut.add(sqlDate);
            }

            try {
                libPeriode.add(cellLibPeriode.getStringCellValue());
            } catch (NullPointerException NPE) {
                libPeriode.add("Pas de libellé");
            }
        }

        //Insère dans la table cours tout les cours
        for (int i = 1; i < rows - 1; i++) {

            String sql = "INSERT INTO PERIODE VALUES (0,?,?,?,?)";

            try (PreparedStatement st = cx.prepareStatement(sql)) {

                //st.setInt(1, numEtu.get(i));
                st.setInt(1, idFormation);
                st.setDate(2, dateDebut.get(i));
                st.setDate(3, dateFin.get(i));
                st.setString(4, libPeriode.get(i));

                st.executeUpdate();
            } catch (SQLException ex) {
                // Il faudrait tester si la connexion est ouverte ????????????
                throw new SQLException("Exception remplir cours() : Problème SQL - " + ex.getMessage());
            }
        }

    }

    public static void ajouterProf(String lienExcel) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

        if (cx == null) {
            connexion();
        }
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(lienExcel));
        ArrayList<String> nomProf = new ArrayList<>();
        ArrayList<String> prenomProf = new ArrayList<>();
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(0);

        int rows; // No of rows
        int max1 = 999; // Identifiant max 
        rows = sheet.getPhysicalNumberOfRows();
        int cols = 0; // No of columns
        String mdp = "abc1234";

        //Récupère les informations des profs dans plusieurs arrayList
        for (int i = 1; i < rows; i++) {

            HSSFCell cellnom = sheet.getRow(i).getCell(1);
            HSSFCell cellprenom = sheet.getRow(i).getCell(2);
            //Gérer exception Int
            try {
                cellnom.setCellType(CellType.STRING);
                String resultNom = cellnom.getStringCellValue();

                cellprenom.setCellType(CellType.STRING);
                String resultPrenom = cellprenom.getStringCellValue();

                String sql = "SELECT CODEE from enseignant WHERE NOME = ? AND PRENOME = ?";

                try (PreparedStatement st = cx.prepareStatement(sql)) {

                    st.setString(1, resultNom);
                    st.setString(2, resultPrenom);

                    ResultSet rs = st.executeQuery();
                    //System.out.println(rs.getString(1));
                    if (!rs.next()) {
                        String sql2 = "SELECT MAX(CODEE) from enseignant";
                        try (PreparedStatement st2 = cx.prepareStatement(sql2)) {
                            /*----- Exécution de la requête -----*/

                            try (ResultSet rs2 = st2.executeQuery()) {
                                /*----- Lecture du contenu du ResultSet -----*/
                                while (rs2.next()) {
                                    max1 = rs2.getInt(1) + 1;
                                }
                                System.out.println(max1);
                            }
                        } catch (SQLException ex) {
                            // Il faudrait tester si la connexion est ouverte ????????????
                            throw new SQLException("Exception lireCitations() : Problème SQL - " + ex.getMessage());
                        }

                        String sql1 = "INSERT INTO enseignant (CODEE,MAILE,NOME,PRENOME,CONTACTE,MDPE,PHOTO) VALUES (?,?,?,?,null,?,'default.jpg') ";

                        try (PreparedStatement st1 = cx.prepareStatement(sql1)) {

                            st1.setInt(1, max1);
                            st1.setString(2, resultPrenom.toLowerCase() + "." + resultNom.toLowerCase() + "@ut-capitole.fr");
                            st1.setString(3, resultNom);
                            st1.setString(4, resultPrenom);
                            st1.setString(5, mdp);

                            st1.executeUpdate();
                        }
                    }
                } catch (SQLException ex) {
                    // Il faudrait tester si la connexion est ouverte ????????????
                    throw new SQLException("Exception ajouterProf() : Problème SQL - " + ex.getMessage());
                }

            } catch (NullPointerException NPE) {

            }

        }
    }

}
