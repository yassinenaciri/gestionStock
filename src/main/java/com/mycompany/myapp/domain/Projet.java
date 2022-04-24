package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "projet")
    @JsonIgnoreProperties(value = { "projet" }, allowSetters = true)
    private Set<Frais> fraisSupplementaires = new HashSet<>();

    @OneToMany(mappedBy = "projet")
    @JsonIgnoreProperties(value = { "paiements", "client", "projet" }, allowSetters = true)
    private Set<ArticleProjet> items = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_projet__articles",
        joinColumns = @JoinColumn(name = "projet_id"),
        inverseJoinColumns = @JoinColumn(name = "articles_id")
    )
    @JsonIgnoreProperties(value = { "optionsAchats", "projets" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(mappedBy = "projets")
    @JsonIgnoreProperties(value = { "projets" }, allowSetters = true)
    private Set<ResponsableProjet> responsables = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Projet nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Projet description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Frais> getFraisSupplementaires() {
        return this.fraisSupplementaires;
    }

    public void setFraisSupplementaires(Set<Frais> frais) {
        if (this.fraisSupplementaires != null) {
            this.fraisSupplementaires.forEach(i -> i.setProjet(null));
        }
        if (frais != null) {
            frais.forEach(i -> i.setProjet(this));
        }
        this.fraisSupplementaires = frais;
    }

    public Projet fraisSupplementaires(Set<Frais> frais) {
        this.setFraisSupplementaires(frais);
        return this;
    }

    public Projet addFraisSupplementaire(Frais frais) {
        this.fraisSupplementaires.add(frais);
        frais.setProjet(this);
        return this;
    }

    public Projet removeFraisSupplementaire(Frais frais) {
        this.fraisSupplementaires.remove(frais);
        frais.setProjet(null);
        return this;
    }

    public Set<ArticleProjet> getItems() {
        return this.items;
    }

    public void setItems(Set<ArticleProjet> articleProjets) {
        if (this.items != null) {
            this.items.forEach(i -> i.setProjet(null));
        }
        if (articleProjets != null) {
            articleProjets.forEach(i -> i.setProjet(this));
        }
        this.items = articleProjets;
    }

    public Projet items(Set<ArticleProjet> articleProjets) {
        this.setItems(articleProjets);
        return this;
    }

    public Projet addItems(ArticleProjet articleProjet) {
        this.items.add(articleProjet);
        articleProjet.setProjet(this);
        return this;
    }

    public Projet removeItems(ArticleProjet articleProjet) {
        this.items.remove(articleProjet);
        articleProjet.setProjet(null);
        return this;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Projet articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public Projet addArticles(Article article) {
        this.articles.add(article);
        article.getProjets().add(this);
        return this;
    }

    public Projet removeArticles(Article article) {
        this.articles.remove(article);
        article.getProjets().remove(this);
        return this;
    }

    public Set<ResponsableProjet> getResponsables() {
        return this.responsables;
    }

    public void setResponsables(Set<ResponsableProjet> responsableProjets) {
        if (this.responsables != null) {
            this.responsables.forEach(i -> i.removeProjets(this));
        }
        if (responsableProjets != null) {
            responsableProjets.forEach(i -> i.addProjets(this));
        }
        this.responsables = responsableProjets;
    }

    public Projet responsables(Set<ResponsableProjet> responsableProjets) {
        this.setResponsables(responsableProjets);
        return this;
    }

    public Projet addResponsables(ResponsableProjet responsableProjet) {
        this.responsables.add(responsableProjet);
        responsableProjet.getProjets().add(this);
        return this;
    }

    public Projet removeResponsables(ResponsableProjet responsableProjet) {
        this.responsables.remove(responsableProjet);
        responsableProjet.getProjets().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
