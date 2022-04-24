package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "desciption")
    private String desciption;

    @OneToMany(mappedBy = "article")
    @JsonIgnoreProperties(value = { "article", "fournisseur", "commandes" }, allowSetters = true)
    private Set<FournisseurArticle> optionsAchats = new HashSet<>();

    @ManyToMany(mappedBy = "articles")
    @JsonIgnoreProperties(value = { "fraisSupplementaires", "items", "articles", "responsables" }, allowSetters = true)
    private Set<Projet> projets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Article id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Article nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDesciption() {
        return this.desciption;
    }

    public Article desciption(String desciption) {
        this.setDesciption(desciption);
        return this;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Set<FournisseurArticle> getOptionsAchats() {
        return this.optionsAchats;
    }

    public void setOptionsAchats(Set<FournisseurArticle> fournisseurArticles) {
        if (this.optionsAchats != null) {
            this.optionsAchats.forEach(i -> i.setArticle(null));
        }
        if (fournisseurArticles != null) {
            fournisseurArticles.forEach(i -> i.setArticle(this));
        }
        this.optionsAchats = fournisseurArticles;
    }

    public Article optionsAchats(Set<FournisseurArticle> fournisseurArticles) {
        this.setOptionsAchats(fournisseurArticles);
        return this;
    }

    public Article addOptionsAchat(FournisseurArticle fournisseurArticle) {
        this.optionsAchats.add(fournisseurArticle);
        fournisseurArticle.setArticle(this);
        return this;
    }

    public Article removeOptionsAchat(FournisseurArticle fournisseurArticle) {
        this.optionsAchats.remove(fournisseurArticle);
        fournisseurArticle.setArticle(null);
        return this;
    }

    public Set<Projet> getProjets() {
        return this.projets;
    }

    public void setProjets(Set<Projet> projets) {
        if (this.projets != null) {
            this.projets.forEach(i -> i.removeArticles(this));
        }
        if (projets != null) {
            projets.forEach(i -> i.addArticles(this));
        }
        this.projets = projets;
    }

    public Article projets(Set<Projet> projets) {
        this.setProjets(projets);
        return this;
    }

    public Article addProjet(Projet projet) {
        this.projets.add(projet);
        projet.getArticles().add(this);
        return this;
    }

    public Article removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.getArticles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", desciption='" + getDesciption() + "'" +
            "}";
    }
}
