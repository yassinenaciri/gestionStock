package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Entreprise;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Entreprise}.
 */
public interface EntrepriseService {
    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save.
     * @return the persisted entity.
     */
    Entreprise save(Entreprise entreprise);

    /**
     * Updates a entreprise.
     *
     * @param entreprise the entity to update.
     * @return the persisted entity.
     */
    Entreprise update(Entreprise entreprise);

    /**
     * Partially updates a entreprise.
     *
     * @param entreprise the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Entreprise> partialUpdate(Entreprise entreprise);

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Entreprise> findAll(Pageable pageable);

    /**
     * Get the "id" entreprise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Entreprise> findOne(Long id);

    /**
     * Delete the "id" entreprise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
