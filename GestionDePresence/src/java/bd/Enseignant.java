package bd;
// Generated Mar 19, 2020 9:51:45 AM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Enseignant generated by hbm2java
 */
public class Enseignant implements java.io.Serializable {

    private int codee;
    private String maile;
    private String nome;
    private String prenome;
    private String contacte;
    private String mdpe;
    private String photo;
    private Set courses = new HashSet(0);
    private Set creneaus = new HashSet(0);
    private Set formations = new HashSet(0);

    public Enseignant() {
    }

    public Enseignant(int codee) {
        this.codee = codee;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Enseignant(String maile, String nome, String contacte, String mdpe, String photo) {
        this.maile = maile;
        this.nome = nome;
        this.contacte = contacte;
        this.mdpe = mdpe;
        this.photo = photo;
    }

    public Enseignant(int codee, String maile, String nome, String prenome, String contacte, String mdpe, Set courses, Set creneaus, Set formations) {
        this.codee = codee;
        this.maile = maile;
        this.nome = nome;
        this.prenome = prenome;
        this.contacte = contacte;
        this.mdpe = mdpe;
        this.courses = courses;
        this.creneaus = creneaus;
        this.formations = formations;
    }

    public int getCodee() {
        return this.codee;
    }

    public void setCodee(int codee) {
        this.codee = codee;
    }

    public String getMaile() {
        return this.maile;
    }

    public void setMaile(String maile) {
        this.maile = maile;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrenome() {
        return this.prenome;
    }

    public void setPrenome(String prenome) {
        this.prenome = prenome;
    }

    public String getContacte() {
        return this.contacte;
    }

    public void setContacte(String contacte) {
        this.contacte = contacte;
    }

    public String getMdpe() {
        return this.mdpe;
    }

    public void setMdpe(String mdpe) {
        this.mdpe = mdpe;
    }

    public Set getCourses() {
        return this.courses;
    }

    public void setCourses(Set courses) {
        this.courses = courses;
    }

    public Set getCreneaus() {
        return this.creneaus;
    }

    public void setCreneaus(Set creneaus) {
        this.creneaus = creneaus;
    }

    public Set getFormations() {
        return this.formations;
    }

    public void setFormations(Set formations) {
        this.formations = formations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.codee;
        hash = 97 * hash + Objects.hashCode(this.maile);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.prenome);
        hash = 97 * hash + Objects.hashCode(this.contacte);
        hash = 97 * hash + Objects.hashCode(this.mdpe);
        hash = 97 * hash + Objects.hashCode(this.photo);
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
        final Enseignant other = (Enseignant) obj;
        if (this.codee != other.codee) {
            return false;
        }
        if (!Objects.equals(this.maile, other.maile)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.prenome, other.prenome)) {
            return false;
        }
        if (!Objects.equals(this.contacte, other.contacte)) {
            return false;
        }
        if (!Objects.equals(this.mdpe, other.mdpe)) {
            return false;
        }
        if (!Objects.equals(this.photo, other.photo)) {
            return false;
        }
        return true;
    }

}
