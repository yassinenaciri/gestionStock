package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ArticleProjet.
 */
@Entity
@Table(name = "article_projet")
public class ArticleProjet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "vendu")
    private Boolean vendu;

    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = { "item" }, allowSetters = true)
    private Set<Paiement> paiements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles" }, allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fraisSupplementaires", "items", "articles", "responsables" }, allowSetters = true)
    private Projet projet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArticleProjet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ArticleProjet nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public ArticleProjet description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVendu() {
        return this.vendu;
    }

    public ArticleProjet vendu(Boolean vendu) {
        this.setVendu(vendu);
        return this;
    }

    public void setVendu(Boolean vendu) {
        this.vendu = vendu;
    }

    public Set<Paiement> getPaiements() {
        return this.paiements;
    }

    public void setPaiements(Set<Paiement> paiements) {
        if (this.paiements != null) {
            this.paiements.forEach(i -> i.setItem(null));
        }
        if (paiements != null) {
            paiements.forEach(i -> i.setItem(this));
        }
        this.paiements = paiements;
    }

    public ArticleProjet paiements(Set<Paiement> paiements) {
        this.setPaiements(paiements);
        return this;
    }

    public ArticleProjet addPaiements(Paiement paiement) {
        this.paiements.add(paiement);
        paiement.setItem(this);
        return this;
    }

    public ArticleProjet removePaiements(Paiement paiement) {
        this.paiements.remove(paiement);
        paiement.setItem(null);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArticleProjet client(Client client) {
        this.setClient(client);
        return this;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public ArticleProjet projet(Projet projet) {
        this.setProjet(projet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleProjet)) {
            return false;
        }
        return id != null && id.equals(((ArticleProjet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleProjet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", vendu='" + getVendu() + "'" +
            "}";
    }
}
