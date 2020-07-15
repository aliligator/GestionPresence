package bd;
// Generated Mar 19, 2020 9:51:45 AM by Hibernate Tools 4.3.1

import java.util.Objects;

/**
 * Etreprersenter generated by hbm2java
 */
public class Etreprersenter implements java.io.Serializable {

    private EtreprersenterId id;
    private Creneau creneau;
    private Etudiant etudiant;
    private String presence;

    public Etreprersenter() {
    }

    public Etreprersenter(EtreprersenterId id, Creneau creneau, Etudiant etudiant) {
        this.id = id;
        this.creneau = creneau;
        this.etudiant = etudiant;
    }

    public Etreprersenter(EtreprersenterId id, Creneau creneau, Etudiant etudiant, String presence) {
        this.id = id;
        this.creneau = creneau;
        this.etudiant = etudiant;
        this.presence = presence;
    }

    public EtreprersenterId getId() {
        return this.id;
    }

    public void setId(EtreprersenterId id) {
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
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    @Override
    public String toString() {
        return "Etreprersenter{" + "id=" + id + ", creneau=" + creneau + ", etudiant=" + etudiant + ", presence=" + presence + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.creneau);
        hash = 47 * hash + Objects.hashCode(this.presence);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etreprersenter other = (Etreprersenter) obj;
        if (!Objects.equals(this.presence, other.presence)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}