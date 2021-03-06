package bd;
// Generated 16 juil. 2020 15:28:01 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Creneau generated by hbm2java
 */
public class Creneau  implements java.io.Serializable {


     private Integer codecre;
     private Cours cours;
     private Enseignant enseignant;
     private Groupe groupe;
     private Date datecre;
     private Integer dureecre;
     private Date heuredeb;
     private String typeactivitecre;
     private Set etrepresents = new HashSet(0);

    public Creneau() {
    }

    public Creneau(Cours cours, Enseignant enseignant, Groupe groupe, Date datecre, Integer dureecre, Date heuredeb, String typeactivitecre, Set etrepresents) {
       this.cours = cours;
       this.enseignant = enseignant;
       this.groupe = groupe;
       this.datecre = datecre;
       this.dureecre = dureecre;
       this.heuredeb = heuredeb;
       this.typeactivitecre = typeactivitecre;
       this.etrepresents = etrepresents;
    }

    public Creneau(Cours cours, Enseignant enseignant, Groupe groupe, Date datecre, Integer dureecre, Date heuredeb, String typeactivitecre) {
        this.cours = cours;
        this.enseignant = enseignant;
        this.groupe = groupe;
        this.datecre = datecre;
        this.dureecre = dureecre;
        this.heuredeb = heuredeb;
        this.typeactivitecre = typeactivitecre;
    }
    
    
    


   
    public Integer getCodecre() {
        return this.codecre;
    }
    
    public void setCodecre(Integer codecre) {
        this.codecre = codecre;
    }
    public Cours getCours() {
        return this.cours;
    }
    
    public void setCours(Cours cours) {
        this.cours = cours;
    }
    public Enseignant getEnseignant() {
        return this.enseignant;
    }
    
    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
    public Groupe getGroupe() {
        return this.groupe;
    }
    
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    public Date getDatecre() {
        return this.datecre;
    }
    
    public void setDatecre(Date datecre) {
        this.datecre = datecre;
    }
    public Integer getDureecre() {
        return this.dureecre;
    }
    
    public void setDureecre(Integer dureecre) {
        this.dureecre = dureecre;
    }
    public Date getHeuredeb() {
        return this.heuredeb;
    }
    
    public void setHeuredeb(Date heuredeb) {
        this.heuredeb = heuredeb;
    }
    public String getTypeactivitecre() {
        return this.typeactivitecre;
    }
    
    public void setTypeactivitecre(String typeactivitecre) {
        this.typeactivitecre = typeactivitecre;
    }
    public Set getEtrepresents() {
        return this.etrepresents;
    }
    
    public void setEtrepresents(Set etrepresents) {
        this.etrepresents = etrepresents;
    }




}


