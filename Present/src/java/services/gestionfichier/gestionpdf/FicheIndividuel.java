package services.gestionfichier.gestionpdf;

import bd.Enseignant;
import bd.Etudiant;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Session;
import static services.gestionfichier.RessourceConstant.MATIN;
import static services.gestionfichier.RessourceConstant.MIDI;
import static services.gestionfichier.RessourceConstant.RELATIVE_PATH_FICHE_EMARGEMENT_GENERE;
import static services.gestionfichier.RessourceConstant.RELATIVE_PATH_LOGO_FCV2A;
import static services.gestionfichier.RessourceConstant.RELATIVE_PATH_LOGO_UE;
import static services.gestionfichier.RessourceConstant.RELATIVE_PATH_LOGO_UT1C;
import static services.gestionfichier.RessourceConstant.SOIR;

/**
 *
 * @author yeping
 */
public class FicheIndividuel {

    public static final String TITRE_FICHE_EMARGEMENT = "FEUILLE D'EMARGEMENT \nMENSUELLE";
    public final int idEtu;
    public final String nomF;
    public final String nomFiche;
    public final String url;
    public final String cheminRacine;
    public final String chemin;
    public final Date moisAnnee;

    public static String getTITRE_FICHE_EMARGEMENT() {
        return TITRE_FICHE_EMARGEMENT;
    }

    public int getIdEtu() {
        return idEtu;
    }

    public String getNomF() {
        return nomF;
    }

    public String getNomFiche() {
        return nomFiche;
    }

    public String getUrl() {
        return url;
    }

    public String getChemin() {
        return chemin;
    }

    public Date getMoisAnnee() {
        return moisAnnee;
    }

    public FicheIndividuel(int idEtu, Date date, String cheminRacine) {
        this.idEtu = idEtu;
        int m = date.getMonth();
        String s = String.valueOf(m);
        if (m < 10) {
            s = "0" + String.valueOf(m);
        }
        // etu1_mois03_2020
        this.nomF = "etu" + idEtu + "_mois" + s + "_" + date.getYear();
        // etu1_mois03_2020.pdf
        this.nomFiche = nomF + ".pdf";
        this.moisAnnee = date;
        this.cheminRacine = cheminRacine;
        this.chemin = cheminRacine + File.separator + RELATIVE_PATH_FICHE_EMARGEMENT_GENERE;
        this.url = cheminRacine + File.separator + RELATIVE_PATH_FICHE_EMARGEMENT_GENERE + File.separator + nomFiche;
        System.out.println("démarrage de la  création : " + url);
        System.out.println("Segment du jour :  début du matin" + MATIN + "---> midi" + MIDI + "---> soir" + SOIR);

        //si l'emplacement n'existe pas alors on le crée.
        File repertoire = new File(cheminRacine + File.separator + RELATIVE_PATH_FICHE_EMARGEMENT_GENERE);
        if (!repertoire.exists()) {
            repertoire.mkdirs();
        }
        //si l'emplacement n'existe pas alors on le crée.
    }

    /**
     * créer un fichier stocké sur serveur et en même temps enregistrer l'url en
     * BD.
     *
     * @param session
     * @throws java.io.FileNotFoundException
     * @throws java.net.MalformedURLException
     * @throws Exception
     */
    public void creerFiche(Session session) throws FileNotFoundException, MalformedURLException {

        //les noms à afficher
        String etuNom = "";
        String formation = "";
        String nomRes = "";
        String dateAff = moisAnnee.getMonth() + "-" + moisAnnee.getYear();
//  get les noms à afficher dans bd
        Etudiant etudiant = (Etudiant) session.get(Etudiant.class, idEtu);
        Enseignant responsable = (Enseignant) etudiant.getFormation().getEnseignants().iterator().next();
        etuNom = etudiant.getPrenometu() + " " + etudiant.getNometu();
        formation = etudiant.getFormation().getNomf();
        nomRes = responsable.getPrenome() + " " + responsable.getNome();

        //liste type Long pour calculer
        //liste type String pour afficher
        ArrayList<Long[]> lstPrMois = new ArrayList<Long[]>();
        ArrayList<String[]> lstPreMois = new ArrayList<String[]>();

        //pour chaque jour on créer un liste qui stocker les heures par présence et par matin/aprèsmidi
        for (int i = 0; i < 31; i++) {
//            System.out.println("jour : " + (i + 1));
            Date d = new Date(moisAnnee.getYear(), moisAnnee.getMonth(), i + 1);

            /*
             * 0 : matin pre e
             * 1 : matin pre d
             * 2 : matin abs 
             * 3 : apresmidi pre e
             * 4 : apresmidi pre d
             * 5 : apresmidi abs
             * 6 : pre e total d'un jour
             * 7 : pre d total d'un jour
             * 8 : abs total d'un jour
             */
            Long[] preJour = new Long[9];
            String[] s = new String[9];

            //matin
            // pre E
            preJour[0] = services.ServiceEtudiant.getMinTotalPre(session, etudiant, MATIN, MIDI, d, "PRESENT", "cours");
            s[0] = preJour[0] == 0 ? "" : toHeure(preJour[0].intValue());
            // pre D
            preJour[1] = services.ServiceEtudiant.getMinTotalPre(session, etudiant, MATIN, MIDI, d, "PRESENT", "documentation");
            s[1] = preJour[1] == 0 ? "" : toHeure(preJour[1].intValue());
            // abs 
            preJour[2] = services.ServiceEtudiant.getMinTotalPre(session, etudiant, MATIN, MIDI, d, "ABSENT", "documentation")
                    + services.ServiceEtudiant.getMinTotalPre(session, etudiant, MATIN, MIDI, d, "ABJ", "documentation")
                    + services.ServiceEtudiant.getMinTotalPre(session, etudiant, MATIN, MIDI, d, "ABS", "cours")
                    + services.ServiceEtudiant.getMinTotalPre(session, etudiant, MATIN, MIDI, d, "ABJ", "cours");
            s[2] = preJour[2] == 0 ? "" : toHeure(preJour[2].intValue());
            //après midi
            //pre E
            preJour[3] = services.ServiceEtudiant.getMinTotalPre(session, etudiant, MIDI, SOIR, d, "PRESENT", "cours");
            s[3] = preJour[3] == 0 ? "" : toHeure(preJour[3].intValue());
            //pre D
            preJour[4] = services.ServiceEtudiant.getMinTotalPre(session, etudiant, MIDI, SOIR, d, "PRESENT", "documentation");
            s[4] = preJour[4] == 0 ? "" : toHeure(preJour[4].intValue());
            //abs
            preJour[5] = services.ServiceEtudiant.getMinTotalPre(session, etudiant, MIDI, SOIR, d, "ABSENT", "documentation")
                    + services.ServiceEtudiant.getMinTotalPre(session, etudiant, MIDI, SOIR, d, "ABJ", "documentation")
                    + services.ServiceEtudiant.getMinTotalPre(session, etudiant, MIDI, SOIR, d, "ABS", "cours")
                    + services.ServiceEtudiant.getMinTotalPre(session, etudiant, MIDI, SOIR, d, "ABJ", "cours");
            s[5] = preJour[5] == 0 ? "" : toHeure(preJour[5].intValue());
            //pre E total par jour
            preJour[6] = preJour[0] + preJour[3];
            s[6] = preJour[6] == 0 ? "" : toHeure(preJour[6].intValue());
            //pre D total par jour 
            preJour[7] = preJour[1] + preJour[4];
            s[7] = preJour[7] == 0 ? "" : toHeure(preJour[7].intValue());
            //abs total par jour
            preJour[8] = preJour[2] + preJour[5];
            s[8] = preJour[8] == 0 ? "" : toHeure(preJour[8].intValue());
            //ajouter dans list pour calculer total
            lstPrMois.add(preJour);
            //ajouter dans list pour afficher
            lstPreMois.add(s);
        }
        //total 
        Long[] totall = new Long[]{0L, 0L, 0L};
        String[] total = new String[3];
        //total de la première quinzaine de mois 
        Long[] totallpm = new Long[]{0L, 0L, 0L};
        String[] totalpm = new String[3];
        //total de la deuxième quinzaine de mois
        Long[] totalldm = new Long[]{0L, 0L, 0L};
        String[] totaldm = new String[3];

        //pour chauqe jour
        for (int i = 0; i < 31; i++) {
            Long[] unjour = lstPrMois.get(i);
            //pre E total
            totall[0] += unjour[0] + unjour[3];
            //pre D total
            totall[1] += unjour[1] + unjour[4];
            //abs total
            totall[2] += unjour[2] + unjour[5];

            if (i == 14) {
                // premier moitié
                totallpm[0] = totall[0];
                totallpm[1] = totall[1];
                totallpm[2] = totall[2];
            }
            if (i == 30) {
                //deuxième moité
                totalldm[0] += totall[0] - totallpm[0];
                totalldm[1] += totall[1] - totallpm[1];
                totalldm[2] += totall[2] - totallpm[2];
            }
        }
        //// tostring pour afficher
        //total
        total[0] = toHeure(totall[0].intValue());
        total[1] = toHeure(totall[1].intValue());
        total[2] = toHeure(totall[2].intValue());
        //total premier moitié
        totalpm[0] = toHeure(totallpm[0].intValue());
        totalpm[1] = toHeure(totallpm[1].intValue());
        totalpm[2] = toHeure(totallpm[2].intValue());
        //total deuxieme moitié
        totaldm[0] = toHeure(totalldm[0].intValue());
        totaldm[1] = toHeure(totalldm[1].intValue());
        totaldm[2] = toHeure(totalldm[2].intValue());
        /*    
        *Création du  PDF
         */
        System.out.println("url : " + url);
        PdfWriter writer = new PdfWriter(this.url);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4);
        doc.setMargins(35, 20, 30, 20);
        /**
         * génération logo du document
         */
//logos ut1c       // logo UE 
        ImageData logoUt1cData = ImageDataFactory.create(cheminRacine + File.separator + RELATIVE_PATH_LOGO_UT1C);
        ImageData logoFcv2aData = ImageDataFactory.create(cheminRacine + File.separator + RELATIVE_PATH_LOGO_FCV2A);
        ImageData logoUEData = ImageDataFactory.create(cheminRacine + File.separator + RELATIVE_PATH_LOGO_UE);
        Image logoUt1c = new Image(logoUt1cData).setFixedPosition(20, 760).scaleToFit(40, 40);
        Image logoFcv2a = new Image(logoFcv2aData).setFixedPosition(60, 760).scaleToFit(40, 40);
        Image logoUE = new Image(logoUEData).setFixedPosition(510, 770).scaleToFit(45, 45);
        doc.add(logoUt1c);
        doc.add(logoFcv2a);
        doc.add(logoUE);
        /**
         * entête
         */
        //formalism de l'entête
        Table entete = new Table(1).useAllAvailableWidth().setBorder(Border.NO_BORDER);
        // titre
        Cell titreCell = new Cell().add(new Paragraph(TITRE_FICHE_EMARGEMENT).setFontSize(8)).
                setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).
                setHeight(35);
        entete.addCell(titreCell);
        Paragraph textlogoeu = new Paragraph("UNION EUROPEENNE\nFonds Social Européen").setFontSize(6);
        Cell textLogoUECell = new Cell().
                add(textlogoeu).
                setTextAlignment(TextAlignment.RIGHT).
                setHeight(18);
        entete.addCell(textLogoUECell);
        //ajouter nom de la formation
        Paragraph specialite = new Paragraph(formation).setFontSize(8);
        Cell specialiteCell = new Cell().
                add(specialite).
                setTextAlignment(TextAlignment.LEFT).
                setHeight(12);
        entete.addCell(specialiteCell);
        //ajouter date
        Paragraph d = new Paragraph(new Text(dateAff)).setFontSize(6);
        Cell dateCell = new Cell().
                add(d).
                setTextAlignment(TextAlignment.RIGHT).
                setHeight(10);
        entete.addCell(dateCell);
        //ajouter nom d'étudiant
        Paragraph nomEtu = new Paragraph(etuNom).setFontSize(8);
        Cell nomEtuCell = new Cell().
                add(nomEtu).
                setTextAlignment(TextAlignment.LEFT).
                setHeight(12);
        entete.addCell(nomEtuCell);
        RemoveBorder(entete);
        doc.add(entete);
        /**
         * table D'EMARGEMENT
         */
        UnitValue[] unitValue = new UnitValue[]{
            UnitValue.createPercentValue((float) 5),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 12),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 12),
            UnitValue.createPercentValue((float) 6),
            UnitValue.createPercentValue((float) 7),
            UnitValue.createPercentValue((float) 7),
            UnitValue.createPercentValue((float) 7)};

        // 1er page
        Table corp1 = new Table(unitValue).useAllAvailableWidth();
        Aligne(corp1, TextAlignment.CENTER);
        //en-tête
        Cell dCell = new Cell(2, 0).add(new Paragraph("Date")).setFontSize(6);
        Cell matinCell = new Cell(0, 5).add(new Paragraph("Matin")).setFontSize(6);
        Cell apresmidiCell = new Cell(0, 5).add(new Paragraph("Après-midi")).setFontSize(6);
        Cell heTotalCell = new Cell(2, 0).add(new Paragraph("Total\nheures E\npar jour")).setFontSize(6);
        Cell hdTotal = new Cell(2, 0).add(new Paragraph("Total\nheures D\npar jour")).setFontSize(6);
        Cell habsTotal = new Cell(2, 0).add(new Paragraph("Total\nheures\nAbs par jour")).setFontSize(6);
        corp1.addCell(dCell);
        corp1.addCell(matinCell);
        corp1.addCell(apresmidiCell);
        corp1.addCell(heTotalCell);
        corp1.addCell(hdTotal);
        corp1.addCell(habsTotal);
        //
        String he = "heures*\nE";
        String hd = "heures*\nD";
        String habs = "heures*\nAbs";
        String signature = "Signature\nstagiaire";
        String site = "Site**";
        for (int i = 0; i < 2; i++) {
            corp1.addCell(he).setFontSize(6);
            corp1.addCell(hd).setFontSize(6);
            corp1.addCell(habs).setFontSize(6);
            corp1.addCell(signature).setFontSize(6);
            corp1.addCell(site).setFontSize(6);
        }
        //// chaque jour
        int jourDuMois = 0;
        while (jourDuMois < 15) {
            corp1.addCell(new Cell().add(new Paragraph(String.valueOf(jourDuMois + 1))));
            corp1.addCell(lstPreMois.get(jourDuMois)[0]);
            corp1.addCell(lstPreMois.get(jourDuMois)[1]);
            corp1.addCell(lstPreMois.get(jourDuMois)[2]);
            corp1.addCell(new Cell());
            corp1.addCell(new Cell());
            corp1.addCell(lstPreMois.get(jourDuMois)[3]);
            corp1.addCell(lstPreMois.get(jourDuMois)[4]);
            corp1.addCell(lstPreMois.get(jourDuMois)[5]);
            corp1.addCell(new Cell());
            corp1.addCell(new Cell());
            corp1.addCell(lstPreMois.get(jourDuMois)[6]);
            corp1.addCell(lstPreMois.get(jourDuMois)[7]);
            corp1.addCell(lstPreMois.get(jourDuMois)[8]);
            jourDuMois++;
        }
        Height(corp1, 30f);
        Text lg1 = new Text("E : pour Enseignement\n(TD-TP-PT -accompagnement ou examen\n"
                + "D : pour Documentation sur site\n"
                + "Abs : pour Absence");
        Cell legend1 = new Cell(3, 5).add(new Paragraph("*Nombre d'heure par type d'activité : \n"))
                .add(new Paragraph(lg1).setFixedLeading(7));

        Text lg2 = new Text("UT1\n"
                + "Lycée Balma\n"
                + "IUT Rodez\n"
                + "CFPA Auzeville\n"
                + "EPLEA Capou Montauban\n"
                + "Lieu du stage..");
        Cell legend2 = new Cell(3, 5).add(new Paragraph("**Indiquez pour chaque demi-journée le site de l'activité\n"))
                .add(new Paragraph(lg2).setFixedLeading(7));
        Cell sousTotal = new Cell().add(new Paragraph("SOUS\nTOTAUX"));
        Cell cache = new Cell(2, 4).setBorder(Border.NO_BORDER);
        corp1.addCell(legend1);
        corp1.addCell(legend2);
        corp1.addCell(sousTotal);
        corp1.addCell(totalpm[0]);
        corp1.addCell(totalpm[1]);
        corp1.addCell(totalpm[2]);
        corp1.addCell(cache);

        doc.add(corp1.setExtendBottomRow(true));
        //2er page
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Table corp2 = new Table(unitValue).useAllAvailableWidth();
        Aligne(corp2, TextAlignment.CENTER);
        // en-tête
        corp2.addCell(dCell.clone(true));
        corp2.addCell(matinCell.clone(true));
        corp2.addCell(apresmidiCell.clone(true));
        corp2.addCell(heTotalCell.clone(true));
        corp2.addCell(hdTotal.clone(true));
        corp2.addCell(habsTotal.clone(true));
        for (int i = 0; i < 2; i++) {
            corp2.addCell(he).setFontSize(6);
            corp2.addCell(hd).setFontSize(6);
            corp2.addCell(habs).setFontSize(6);
            corp2.addCell(signature).setFontSize(6);
            corp2.addCell(site).setFontSize(6);
        }
        while (jourDuMois < 31) {
            corp2.addCell(new Cell().add(new Paragraph(String.valueOf(jourDuMois + 1))));
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[0]));
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[1]));
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[2]));
            corp2.addCell(new Cell());
            corp2.addCell(new Cell());
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[3]));
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[4]));
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[5]));
            corp2.addCell(new Cell());
            corp2.addCell(new Cell());
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[6]));
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[7]));
            corp2.addCell(String.valueOf(lstPreMois.get(jourDuMois)[8]));
            jourDuMois++;
        }
        Paragraph zoneRes = new Paragraph("Responsable Pédagogique\n" + nomRes);
        Cell signatureRes = new Cell(0, 9).setBorderBottom(Border.NO_BORDER).add(zoneRes).setTextAlignment(TextAlignment.LEFT);
        corp2.addCell(signatureRes);
        corp2.addCell(new Cell().setBorder(Border.NO_BORDER));
        corp2.addCell(sousTotal.clone(true));
        corp2.addCell(totaldm[0]);
        corp2.addCell(totaldm[1]);
        corp2.addCell(totaldm[2]);
        corp2.addCell(new Cell(0, 4).setBorderTop(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).add(new Paragraph("Date : ")).setTextAlignment(TextAlignment.LEFT));
        corp2.addCell(new Cell(0, 5).setBorderTop(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).add(new Paragraph("Signature : ")).setTextAlignment(TextAlignment.LEFT));
        corp2.addCell(new Cell(0, 5).setBorder(Border.NO_BORDER));
        corp2.addCell(new Cell(0, 9).setBorderTop(Border.NO_BORDER));
        corp2.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("TOTAUX MENSUELS")));
        corp2.addCell(new Cell().setBorder(Border.NO_BORDER));
        corp2.addCell(total[0]);
        corp2.addCell(total[1]);
        corp2.addCell(total[2]);
        corp2.addCell(new Cell(0, 14).setBorder(Border.NO_BORDER));
        Height(corp2, 30f);
        doc.add(corp2);
        doc.close();
        System.out.println("Fiche créé : " + url);
    }

    private String toHeure(int l) {
        int h = l / 60;
        int min = l % 60;
        return min < 10 ? h + "h0" + min : h + "h" + min;
    }

    private static void RemoveBorder(Table table) {
        for (IElement iElement : table.getChildren()) {
            ((Cell) iElement).setBorder(Border.NO_BORDER);
        }
    }

    private static void Aligne(Table table, TextAlignment ta) {
        for (IElement iElement : table.getChildren()) {
            ((Cell) iElement).setTextAlignment(ta);
        }
    }

    private static void Height(Table table, Float height) {
        for (IElement iElement : table.getChildren()) {
            ((Cell) iElement).setHeight(height);
        }
    }

}
