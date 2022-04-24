package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ArticleProjet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ArticleProjet}.
 */
public interface ArticleProjetService {
    /**
     * Save a articleProjet.
     *
     * @param articleProjet the entity to save.
     * @return the persisted entity.
     */
    ArticleProjet save(ArticleProjet articleProjet);

    /**
     * Updates a articleProjet.
     *
     * @param articleProjet the entity to update.
     * @return the persisted entity.
     */
    ArticleProjet update(ArticleProjet articleProjet);

    /**
     * Partially updates a articleProjet.
     *
     * @param articleProjet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArticleProjet> partialUpdate(ArticleProjet articleProjet);

    /**
     * Get all the articleProjets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleProjet> findAll(Pageable pageable);

    /**
     * Get the "id" articleProjet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleProjet> findOne(Long id);

    /**
     * Delete the "id" articleProjet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
