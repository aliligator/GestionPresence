package metier;
// Generated Mar 27, 2020 12:45:14 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Ue generated by hbm2java
 */
public class Ue  implements java.io.Serializable {


     private String codeUe;
     private String libelleUe;
     private int coefficienUe;
     private Set matieres = new HashSet(0);

    public Ue() {
    }

	
    public Ue(String codeUe, String libelleUe, int coefficienUe) {
        this.codeUe = codeUe;
        this.libelleUe = libelleUe;
        this.coefficienUe = coefficienUe;
    }
    public Ue(String codeUe, String libelleUe, int coefficienUe, Set matieres) {
       this.codeUe = codeUe;
       this.libelleUe = libelleUe;
       this.coefficienUe = coefficienUe;
       this.matieres = matieres;
    }
   
    public String getCodeUe() {
        return this.codeUe;
    }
    
    public void setCodeUe(String codeUe) {
        this.codeUe = codeUe;
    }
    public String getLibelleUe() {
        return this.libelleUe;
    }
    
    public void setLibelleUe(String libelleUe) {
        this.libelleUe = libelleUe;
    }
    public int getCoefficienUe() {
        return this.coefficienUe;
    }
    
    public void setCoefficienUe(int coefficienUe) {
        this.coefficienUe = coefficienUe;
    }
    public Set getMatieres() {
        return this.matieres;
    }
    
    public void setMatieres(Set matieres) {
        this.matieres = matieres;
    }




}


