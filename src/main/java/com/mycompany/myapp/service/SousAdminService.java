package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SousAdmin;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SousAdmin}.
 */
public interface SousAdminService {
    /**
     * Save a sousAdmin.
     *
     * @param sousAdmin the entity to save.
     * @return the persisted entity.
     */
    SousAdmin save(SousAdmin sousAdmin);

    /**
     * Updates a sousAdmin.
     *
     * @param sousAdmin the entity to update.
     * @return the persisted entity.
     */
    SousAdmin update(SousAdmin sousAdmin);

    /**
     * Partially updates a sousAdmin.
     *
     * @param sousAdmin the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SousAdmin> partialUpdate(SousAdmin sousAdmin);

    /**
     * Get all the sousAdmins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SousAdmin> findAll(Pageable pageable);

    /**
     * Get the "id" sousAdmin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SousAdmin> findOne(Long id);

    /**
     * Delete the "id" sousAdmin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
