package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

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

    @OneToMany(mappedBy = "client")
    @JsonIgnoreProperties(value = { "paiements", "client", "projet" }, allowSetters = true)
    private Set<ArticleProjet> articles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Client nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Client prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return this.mail;
    }

    public Client mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<ArticleProjet> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<ArticleProjet> articleProjets) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setClient(null));
        }
        if (articleProjets != null) {
            articleProjets.forEach(i -> i.setClient(this));
        }
        this.articles = articleProjets;
    }

    public Client articles(Set<ArticleProjet> articleProjets) {
        this.setArticles(articleProjets);
        return this;
    }

    public Client addArticles(ArticleProjet articleProjet) {
        this.articles.add(articleProjet);
        articleProjet.setClient(this);
        return this;
    }

    public Client removeArticles(ArticleProjet articleProjet) {
        this.articles.remove(articleProjet);
        articleProjet.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", mail='" + getMail() + "'" +
            "}";
    }
}
