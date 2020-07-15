package metier;
// Generated Mar 27, 2020 12:45:14 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Creneau generated by hbm2java
 */
public class Creneau  implements java.io.Serializable {


     private String idCreneau;
     private Matiere matiere;
     private String nomCreneau;
     private String salle;
     private String enseignant;
     private String dateDeb;
     private int heureDeb;
     private int duree;
     private String typeActivite;
     private String commantaire;
     private Set affecters = new HashSet(0);
     private Set justifiers = new HashSet(0);
     private Set groupes = new HashSet(0);

    public Creneau() {
    }

	
    public Creneau(String idCreneau, String dateDeb, int heureDeb, int duree) {
        this.idCreneau = idCreneau;
        this.dateDeb = dateDeb;
        this.heureDeb = heureDeb;
        this.duree = duree;
    }
    public Creneau(String idCreneau, Matiere matiere, String nomCreneau, String salle, String enseignant, String dateDeb, int heureDeb, int duree, String typeActivite, String commantaire, Set affecters, Set justifiers, Set groupes) {
       this.idCreneau = idCreneau;
       this.matiere = matiere;
       this.nomCreneau = nomCreneau;
       this.salle = salle;
       this.enseignant = enseignant;
       this.dateDeb = dateDeb;
       this.heureDeb = heureDeb;
       this.duree = duree;
       this.typeActivite = typeActivite;
       this.commantaire = commantaire;
       this.affecters = affecters;
       this.justifiers = justifiers;
       this.groupes = groupes;
    }
   
    public String getIdCreneau() {
        return this.idCreneau;
    }
    
    public void setIdCreneau(String idCreneau) {
        this.idCreneau = idCreneau;
    }
    public Matiere getMatiere() {
        return this.matiere;
    }
    
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
    public String getNomCreneau() {
        return this.nomCreneau;
    }
    
    public void setNomCreneau(String nomCreneau) {
        this.nomCreneau = nomCreneau;
    }
    public String getSalle() {
        return this.salle;
    }
    
    public void setSalle(String salle) {
        this.salle = salle;
    }
    public String getEnseignant() {
        return this.enseignant;
    }
    
    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }
    public String getDateDeb() {
        return this.dateDeb;
    }
    
    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }
    public int getHeureDeb() {
        return this.heureDeb;
    }
    
    public void setHeureDeb(int heureDeb) {
        this.heureDeb = heureDeb;
    }
    public int getDuree() {
        return this.duree;
    }
    
    public void setDuree(int duree) {
        this.duree = duree;
    }
    public String getTypeActivite() {
        return this.typeActivite;
    }
    
    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }
    public String getCommantaire() {
        return this.commantaire;
    }
    
    public void setCommantaire(String commantaire) {
        this.commantaire = commantaire;
    }
    public Set getAffecters() {
        return this.affecters;
    }
    
    public void setAffecters(Set affecters) {
        this.affecters = affecters;
    }
    public Set getJustifiers() {
        return this.justifiers;
    }
    
    public void setJustifiers(Set justifiers) {
        this.justifiers = justifiers;
    }
    public Set getGroupes() {
        return this.groupes;
    }
    
    public void setGroupes(Set groupes) {
        this.groupes = groupes;
    }




}

