package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "recu")
    private Boolean recu;

    @Column(name = "quantite")
    private Integer quantite;

    @ManyToMany
    @JoinTable(
        name = "rel_commande__items",
        joinColumns = @JoinColumn(name = "commande_id"),
        inverseJoinColumns = @JoinColumn(name = "items_id")
    )
    @JsonIgnoreProperties(value = { "article", "fournisseur", "commandes" }, allowSetters = true)
    private Set<FournisseurArticle> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Commande nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Commande description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRecu() {
        return this.recu;
    }

    public Commande recu(Boolean recu) {
        this.setRecu(recu);
        return this;
    }

    public void setRecu(Boolean recu) {
        this.recu = recu;
    }

    public Integer getQuantite() {
        return this.quantite;
    }

    public Commande quantite(Integer quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Set<FournisseurArticle> getItems() {
        return this.items;
    }

    public void setItems(Set<FournisseurArticle> fournisseurArticles) {
        this.items = fournisseurArticles;
    }

    public Commande items(Set<FournisseurArticle> fournisseurArticles) {
        this.setItems(fournisseurArticles);
        return this;
    }

    public Commande addItems(FournisseurArticle fournisseurArticle) {
        this.items.add(fournisseurArticle);
        fournisseurArticle.getCommandes().add(this);
        return this;
    }

    public Commande removeItems(FournisseurArticle fournisseurArticle) {
        this.items.remove(fournisseurArticle);
        fournisseurArticle.getCommandes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", recu='" + getRecu() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }
}
