package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResponsableProjet;
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
public class ResponsableProjetRepositoryWithBagRelationshipsImpl implements ResponsableProjetRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ResponsableProjet> fetchBagRelationships(Optional<ResponsableProjet> responsableProjet) {
        return responsableProjet.map(this::fetchProjets);
    }

    @Override
    public Page<ResponsableProjet> fetchBagRelationships(Page<ResponsableProjet> responsableProjets) {
        return new PageImpl<>(
            fetchBagRelationships(responsableProjets.getContent()),
            responsableProjets.getPageable(),
            responsableProjets.getTotalElements()
        );
    }

    @Override
    public List<ResponsableProjet> fetchBagRelationships(List<ResponsableProjet> responsableProjets) {
        return Optional.of(responsableProjets).map(this::fetchProjets).orElse(Collections.emptyList());
    }

    ResponsableProjet fetchProjets(ResponsableProjet result) {
        return entityManager
            .createQuery(
                "select responsableProjet from ResponsableProjet responsableProjet left join fetch responsableProjet.projets where responsableProjet is :responsableProjet",
                ResponsableProjet.class
            )
            .setParameter("responsableProjet", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ResponsableProjet> fetchProjets(List<ResponsableProjet> responsableProjets) {
        return entityManager
            .createQuery(
                "select distinct responsableProjet from ResponsableProjet responsableProjet left join fetch responsableProjet.projets where responsableProjet in :responsableProjets",
                ResponsableProjet.class
            )
            .setParameter("responsableProjets", responsableProjets)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
