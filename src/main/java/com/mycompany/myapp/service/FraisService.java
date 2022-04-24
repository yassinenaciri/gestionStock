package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Frais;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Frais}.
 */
public interface FraisService {
    /**
     * Save a frais.
     *
     * @param frais the entity to save.
     * @return the persisted entity.
     */
    Frais save(Frais frais);

    /**
     * Updates a frais.
     *
     * @param frais the entity to update.
     * @return the persisted entity.
     */
    Frais update(Frais frais);

    /**
     * Partially updates a frais.
     *
     * @param frais the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Frais> partialUpdate(Frais frais);

    /**
     * Get all the frais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Frais> findAll(Pageable pageable);

    /**
     * Get the "id" frais.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Frais> findOne(Long id);

    /**
     * Delete the "id" frais.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
