package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Projet;
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
public class ProjetRepositoryWithBagRelationshipsImpl implements ProjetRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Projet> fetchBagRelationships(Optional<Projet> projet) {
        return projet.map(this::fetchArticles);
    }

    @Override
    public Page<Projet> fetchBagRelationships(Page<Projet> projets) {
        return new PageImpl<>(fetchBagRelationships(projets.getContent()), projets.getPageable(), projets.getTotalElements());
    }

    @Override
    public List<Projet> fetchBagRelationships(List<Projet> projets) {
        return Optional.of(projets).map(this::fetchArticles).orElse(Collections.emptyList());
    }

    Projet fetchArticles(Projet result) {
        return entityManager
            .createQuery("select projet from Projet projet left join fetch projet.articles where projet is :projet", Projet.class)
            .setParameter("projet", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Projet> fetchArticles(List<Projet> projets) {
        return entityManager
            .createQuery("select distinct projet from Projet projet left join fetch projet.articles where projet in :projets", Projet.class)
            .setParameter("projets", projets)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
