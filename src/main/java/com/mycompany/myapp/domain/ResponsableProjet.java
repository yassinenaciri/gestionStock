package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ResponsableProjet.
 */
@Entity
@Table(name = "responsable_projet")
public class ResponsableProjet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "mail")
    private String mail;

    @ManyToMany
    @JoinTable(
        name = "rel_responsable_projet__projets",
        joinColumns = @JoinColumn(name = "responsable_projet_id"),
        inverseJoinColumns = @JoinColumn(name = "projets_id")
    )
    @JsonIgnoreProperties(value = { "fraisSupplementaires", "items", "articles", "responsables" }, allowSetters = true)
    private Set<Projet> projets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResponsableProjet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ResponsableProjet nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public ResponsableProjet prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return this.mail;
    }

    public ResponsableProjet mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Projet> getProjets() {
        return this.projets;
    }

    public void setProjets(Set<Projet> projets) {
        this.projets = projets;
    }

    public ResponsableProjet projets(Set<Projet> projets) {
        this.setProjets(projets);
        return this;
    }

    public ResponsableProjet addProjets(Projet projet) {
        this.projets.add(projet);
        projet.getResponsables().add(this);
        return this;
    }

    public ResponsableProjet removeProjets(Projet projet) {
        this.projets.remove(projet);
        projet.getResponsables().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsableProjet)) {
            return false;
        }
        return id != null && id.equals(((ResponsableProjet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsableProjet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", mail='" + getMail() + "'" +
            "}";
    }
}
