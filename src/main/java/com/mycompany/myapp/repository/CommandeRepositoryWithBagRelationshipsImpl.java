package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Commande;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CommandeRepositoryWithBagRelationshipsImpl implements CommandeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Commande> fetchBagRelationships(Optional<Commande> commande) {
        return commande.map(this::fetchItems);
    }

    @Override
    public Page<Commande> fetchBagRelationships(Page<Commande> commandes) {
        return new PageImpl<>(fetchBagRelationships(commandes.getContent()), commandes.getPageable(), commandes.getTotalElements());
    }

    @Override
    public List<Commande> fetchBagRelationships(List<Commande> commandes) {
        return Optional.of(commandes).map(this::fetchItems).orElse(Collections.emptyList());
    }

    Commande fetchItems(Commande result) {
        return entityManager
            .createQuery(
                "select commande from Commande commande left join fetch commande.items where commande is :commande",
                Commande.class
            )
            .setParameter("commande", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Commande> fetchItems(List<Commande> commandes) {
        return entityManager
            .createQuery(
                "select distinct commande from Commande commande left join fetch commande.items where commande in :commandes",
                Commande.class
            )
            .setParameter("commandes", commandes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
