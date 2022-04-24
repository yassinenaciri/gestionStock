package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A FournisseurArticle.
 */
@Entity
@Table(name = "fournisseur_article")
public class FournisseurArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "prix")
    private Float prix;

    @ManyToOne
    @JsonIgnoreProperties(value = { "optionsAchats", "projets" }, allowSetters = true)
    private Article article;

    @ManyToOne
    @JsonIgnoreProperties(value = { "menus" }, allowSetters = true)
    private Fournisseur fournisseur;

    @ManyToMany(mappedBy = "items")
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private Set<Commande> commandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FournisseurArticle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrix() {
        return this.prix;
    }

    public FournisseurArticle prix(Float prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public FournisseurArticle article(Article article) {
        this.setArticle(article);
        return this;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public FournisseurArticle fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public Set<Commande> getCommandes() {
        return this.commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        if (this.commandes != null) {
            this.commandes.forEach(i -> i.removeItems(this));
        }
        if (commandes != null) {
            commandes.forEach(i -> i.addItems(this));
        }
        this.commandes = commandes;
    }

    public FournisseurArticle commandes(Set<Commande> commandes) {
        this.setCommandes(commandes);
        return this;
    }

    public FournisseurArticle addCommandes(Commande commande) {
        this.commandes.add(commande);
        commande.getItems().add(this);
        return this;
    }

    public FournisseurArticle removeCommandes(Commande commande) {
        this.commandes.remove(commande);
        commande.getItems().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FournisseurArticle)) {
            return false;
        }
        return id != null && id.equals(((FournisseurArticle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FournisseurArticle{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            "}";
    }
}
