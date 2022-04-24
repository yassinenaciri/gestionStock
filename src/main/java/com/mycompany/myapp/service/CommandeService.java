package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Commande;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Commande}.
 */
public interface CommandeService {
    /**
     * Save a commande.
     *
     * @param commande the entity to save.
     * @return the persisted entity.
     */
    Commande save(Commande commande);

    /**
     * Updates a commande.
     *
     * @param commande the entity to update.
     * @return the persisted entity.
     */
    Commande update(Commande commande);

    /**
     * Partially updates a commande.
     *
     * @param commande the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Commande> partialUpdate(Commande commande);

    /**
     * Get all the commandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Commande> findAll(Pageable pageable);

    /**
     * Get all the commandes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Commande> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" commande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Commande> findOne(Long id);

    /**
     * Delete the "id" commande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
