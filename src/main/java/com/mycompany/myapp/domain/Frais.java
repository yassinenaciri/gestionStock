package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Frais.
 */
@Entity
@Table(name = "frais")
public class Frais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "prix")
    private Float prix;

    @Column(name = "desscription")
    private String desscription;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fraisSupplementaires", "items", "articles", "responsables" }, allowSetters = true)
    private Projet projet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Frais id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrix() {
        return this.prix;
    }

    public Frais prix(Float prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getDesscription() {
        return this.desscription;
    }

    public Frais desscription(String desscription) {
        this.setDesscription(desscription);
        return this;
    }

    public void setDesscription(String desscription) {
        this.desscription = desscription;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Frais projet(Projet projet) {
        this.setProjet(projet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Frais)) {
            return false;
        }
        return id != null && id.equals(((Frais) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Frais{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            ", desscription='" + getDesscription() + "'" +
            "}";
    }
}
