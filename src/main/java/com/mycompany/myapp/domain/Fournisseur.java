package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "ice")
    private String ice;

    @Column(name = "tel")
    private String tel;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "fournisseur")
    @JsonIgnoreProperties(value = { "article", "fournisseur", "commandes" }, allowSetters = true)
    private Set<FournisseurArticle> menus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Fournisseur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIce() {
        return this.ice;
    }

    public Fournisseur ice(String ice) {
        this.setIce(ice);
        return this;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getTel() {
        return this.tel;
    }

    public Fournisseur tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Fournisseur adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return this.description;
    }

    public Fournisseur description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FournisseurArticle> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<FournisseurArticle> fournisseurArticles) {
        if (this.menus != null) {
            this.menus.forEach(i -> i.setFournisseur(null));
        }
        if (fournisseurArticles != null) {
            fournisseurArticles.forEach(i -> i.setFournisseur(this));
        }
        this.menus = fournisseurArticles;
    }

    public Fournisseur menus(Set<FournisseurArticle> fournisseurArticles) {
        this.setMenus(fournisseurArticles);
        return this;
    }

    public Fournisseur addMenu(FournisseurArticle fournisseurArticle) {
        this.menus.add(fournisseurArticle);
        fournisseurArticle.setFournisseur(this);
        return this;
    }

    public Fournisseur removeMenu(FournisseurArticle fournisseurArticle) {
        this.menus.remove(fournisseurArticle);
        fournisseurArticle.setFournisseur(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return id != null && id.equals(((Fournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", ice='" + getIce() + "'" +
            ", tel='" + getTel() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
