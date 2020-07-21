package servlets.responsableForm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static services.gestionfichier.RessourceConstant.RELATIVE_PATH_CSV;
import services.gestionfichier.gestioncsv.Importations;

/**
 * Servlet implementation class Test
 */
@MultipartConfig
public class ServletUploadXls extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final int TAILLE_TAMPON = 10240;
    public static String CHEMIN_FICHIERS = ""; // A changer

    public ServletUploadXls() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/jsp/importationDonnees.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
        String folderName = RELATIVE_PATH_CSV;
        CHEMIN_FICHIERS = request.getServletContext().getRealPath("") + File.separator + folderName;
        //Récupère l'identifiant du responsable de formation en session
        int idResponsable = (int) request.getSession().getAttribute("idenseig");
        System.out.println("request.getContentType(): " + request.getContentType());
        try {
            //Récupère l'id de la formation du responsable en session
            int idFormation = Importations.retournerIdFormation(idResponsable);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Récupère le type d'import depuis le radiobutton
        //Récupère le type d'import depuis le radiobutton
        //Récupère le type d'import depuis le radiobutton
        //Récupère le type d'import depuis le radiobutton

        InputStream impo = request.getPart("import").getInputStream();
        InputStreamReader r = new InputStreamReader(impo);
        BufferedReader reader = new BufferedReader(r);
        StringBuffer sb = new StringBuffer();
        String imp = reader.readLine();
        System.out.println(sb.toString());

        System.out.println("radio botton : " + imp);
        // On récupère le champ du fichier
        Part part = request.getPart("fichier");
        System.out.println("fichier : " + part.getContentType());

        // On vérifie qu'on a bien reçu un fichier
        String nomFichier = part.getSubmittedFileName();
        System.out.println("nomfichier : " + nomFichier);
        // Si on a bien un fichier
        if (nomFichier != null && !nomFichier.isEmpty()) {
            String nomChamp = part.getName();
            System.out.println("nomChamp : " + nomChamp);
            // Corrige un bug du fonctionnement d'Internet Explorer
            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
                    .substring(nomFichier.lastIndexOf('\\') + 1);
            System.out.println("subnomfichier : " + nomFichier);
            // On écrit définitivement le fichier sur le disque
            ecrireFichier(part, nomFichier, CHEMIN_FICHIERS);

            request.setAttribute(nomChamp, nomFichier);

            String adressFichier = CHEMIN_FICHIERS + File.separator + nomFichier;

            //Selon le type d'import depuis le radiobutton
            switch (imp) {
                case "cours": {
                    try {
                        Importations.remplirCours(idResponsable, adressFichier);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

                case "etudiant": {
                    try {
                        Importations.remplirEtudiant(idResponsable, adressFichier);
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "enseignant": {
                    try {
                        Importations.ajouterProf(adressFichier);
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "periode": {
                    try {
                        Importations.ajouterPeriode(idResponsable, adressFichier);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServletUploadXls.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            }

            request.getRequestDispatcher("/importAlert?ok=ok").forward(request, response);
            this.getServletContext().getRequestDispatcher("/importationDonnees").forward(request, response);
        }
    }

    private void ecrireFichier(Part part, String nomFichier, String chemin) throws IOException {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + File.separator + nomFichier)), TAILLE_TAMPON);

            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } finally {
            try {
                sortie.close();
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
            try {
                entree.close();
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
    }

    private static String getNomFichier(Part part) {
        for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
            if (contentDisposition.trim().startsWith("filename")) {
                return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
