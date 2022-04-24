package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FournisseurArticle;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FournisseurArticle}.
 */
public interface FournisseurArticleService {
    /**
     * Save a fournisseurArticle.
     *
     * @param fournisseurArticle the entity to save.
     * @return the persisted entity.
     */
    FournisseurArticle save(FournisseurArticle fournisseurArticle);

    /**
     * Updates a fournisseurArticle.
     *
     * @param fournisseurArticle the entity to update.
     * @return the persisted entity.
     */
    FournisseurArticle update(FournisseurArticle fournisseurArticle);

    /**
     * Partially updates a fournisseurArticle.
     *
     * @param fournisseurArticle the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FournisseurArticle> partialUpdate(FournisseurArticle fournisseurArticle);

    /**
     * Get all the fournisseurArticles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FournisseurArticle> findAll(Pageable pageable);

    /**
     * Get the "id" fournisseurArticle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FournisseurArticle> findOne(Long id);

    /**
     * Delete the "id" fournisseurArticle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
