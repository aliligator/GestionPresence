package bd;
// Generated 16 juil. 2020 15:28:01 by Hibernate Tools 4.3.1



/**
 * Etrepresent generated by hbm2java
 */
public class Etrepresent  implements java.io.Serializable {


     private EtrepresentId id;
     private Creneau creneau;
     private Etudiant etudiant;
     private String presence;

    public Etrepresent() {
    }

    public Etrepresent(EtrepresentId id, Creneau creneau, Etudiant etudiant, String presence) {
       this.id = id;
       this.creneau = creneau;
       this.etudiant = etudiant;
       this.presence = presence;
    }
   
    public EtrepresentId getId() {
        return this.id;
    }
    
    public void setId(EtrepresentId id) {
        this.id = id;
    }
    public Creneau getCreneau() {
        return this.creneau;
    }
    
    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }
    public Etudiant getEtudiant() {
        return this.etudiant;
    }
    
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    public String getPresence() {
        return this.presence;
    }
    
    public void setPresence(String presence) {
        this.presence = presence;
    }




}

