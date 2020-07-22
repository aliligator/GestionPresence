package services.gestionfichier;

import java.sql.Time;

/**
 *
 * @author yeping
 *
 * stocker les urls ou les chemins des ressources en constants
 */
public class RessourceConstant {


    /*
        les ressource et constant pour créer les fiches d'émargement.
     */
    //logo ut1c
    public static final String RELATIVE_PATH_LOGO_UT1C = "ressource/images/fiche_individuel/logo_ut1c.Png";
    //logo fcv2a
    public static final String RELATIVE_PATH_LOGO_FCV2A = "ressource/images/fiche_individuel/logo_fcv2a.Png";
    //logo UE
    public static final String RELATIVE_PATH_LOGO_UE = "ressource/images/fiche_individuel/logo_ue.Png";
    //l'emplacement relative des fiches d'émargement
    public static final String RELATIVE_PATH_FICHE_EMARGEMENT_GENERE = "ressource/fiche_individuel/generer";
    public static final String RELATIVE_PATH_FICHE_EMARGEMENT = "ressource/fiche_individuel";
    // segement des horaires pour calculer les présences dans fichier d'émargement
    public static final Time MATIN = new Time(8, 0, 0);
    public static final Time MIDI = new Time(14, 0, 0);
    public static final Time SOIR = new Time(17, 0, 0);

    /*
    l'emplacement pour stocker les fichier d'importation
     */
    public static final String RELATIVE_PATH_CSV = "ressource/csv";
}
