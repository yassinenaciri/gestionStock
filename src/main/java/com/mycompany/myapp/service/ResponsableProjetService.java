package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ResponsableProjet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ResponsableProjet}.
 */
public interface ResponsableProjetService {
    /**
     * Save a responsableProjet.
     *
     * @param responsableProjet the entity to save.
     * @return the persisted entity.
     */
    ResponsableProjet save(ResponsableProjet responsableProjet);

    /**
     * Updates a responsableProjet.
     *
     * @param responsableProjet the entity to update.
     * @return the persisted entity.
     */
    ResponsableProjet update(ResponsableProjet responsableProjet);

    /**
     * Partially updates a responsableProjet.
     *
     * @param responsableProjet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsableProjet> partialUpdate(ResponsableProjet responsableProjet);

    /**
     * Get all the responsableProjets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsableProjet> findAll(Pageable pageable);

    /**
     * Get all the responsableProjets with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsableProjet> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsableProjet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsableProjet> findOne(Long id);

    /**
     * Delete the "id" responsableProjet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
