package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Projet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Projet}.
 */
public interface ProjetService {
    /**
     * Save a projet.
     *
     * @param projet the entity to save.
     * @return the persisted entity.
     */
    Projet save(Projet projet);

    /**
     * Updates a projet.
     *
     * @param projet the entity to update.
     * @return the persisted entity.
     */
    Projet update(Projet projet);

    /**
     * Partially updates a projet.
     *
     * @param projet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Projet> partialUpdate(Projet projet);

    /**
     * Get all the projets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Projet> findAll(Pageable pageable);

    /**
     * Get all the projets with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Projet> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" projet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Projet> findOne(Long id);

    /**
     * Delete the "id" projet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
