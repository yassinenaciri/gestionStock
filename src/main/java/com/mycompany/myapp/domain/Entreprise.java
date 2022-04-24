package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "entreprises")
    @JsonIgnoreProperties(value = { "entreprises" }, allowSetters = true)
    private Set<Admin> admins = new HashSet<>();

    @OneToMany(mappedBy = "entreprises")
    @JsonIgnoreProperties(value = { "entreprises" }, allowSetters = true)
    private Set<SousAdmin> sousAdmins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entreprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Entreprise nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Admin> getAdmins() {
        return this.admins;
    }

    public void setAdmins(Set<Admin> admins) {
        if (this.admins != null) {
            this.admins.forEach(i -> i.setEntreprises(null));
        }
        if (admins != null) {
            admins.forEach(i -> i.setEntreprises(this));
        }
        this.admins = admins;
    }

    public Entreprise admins(Set<Admin> admins) {
        this.setAdmins(admins);
        return this;
    }

    public Entreprise addAdmin(Admin admin) {
        this.admins.add(admin);
        admin.setEntreprises(this);
        return this;
    }

    public Entreprise removeAdmin(Admin admin) {
        this.admins.remove(admin);
        admin.setEntreprises(null);
        return this;
    }

    public Set<SousAdmin> getSousAdmins() {
        return this.sousAdmins;
    }

    public void setSousAdmins(Set<SousAdmin> sousAdmins) {
        if (this.sousAdmins != null) {
            this.sousAdmins.forEach(i -> i.setEntreprises(null));
        }
        if (sousAdmins != null) {
            sousAdmins.forEach(i -> i.setEntreprises(this));
        }
        this.sousAdmins = sousAdmins;
    }

    public Entreprise sousAdmins(Set<SousAdmin> sousAdmins) {
        this.setSousAdmins(sousAdmins);
        return this;
    }

    public Entreprise addSousAdmin(SousAdmin sousAdmin) {
        this.sousAdmins.add(sousAdmin);
        sousAdmin.setEntreprises(this);
        return this;
    }

    public Entreprise removeSousAdmin(SousAdmin sousAdmin) {
        this.sousAdmins.remove(sousAdmin);
        sousAdmin.setEntreprises(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return id != null && id.equals(((Entreprise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
