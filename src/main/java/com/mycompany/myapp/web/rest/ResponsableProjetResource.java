package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ResponsableProjet;
import com.mycompany.myapp.repository.ResponsableProjetRepository;
import com.mycompany.myapp.service.ResponsableProjetService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ResponsableProjet}.
 */
@RestController
@RequestMapping("/api")
public class ResponsableProjetResource {

    private final Logger log = LoggerFactory.getLogger(ResponsableProjetResource.class);

    private static final String ENTITY_NAME = "responsableProjet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsableProjetService responsableProjetService;

    private final ResponsableProjetRepository responsableProjetRepository;

    public ResponsableProjetResource(
        ResponsableProjetService responsableProjetService,
        ResponsableProjetRepository responsableProjetRepository
    ) {
        this.responsableProjetService = responsableProjetService;
        this.responsableProjetRepository = responsableProjetRepository;
    }

    /**
     * {@code POST  /responsable-projets} : Create a new responsableProjet.
     *
     * @param responsableProjet the responsableProjet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsableProjet, or with status {@code 400 (Bad Request)} if the responsableProjet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsable-projets")
    public ResponseEntity<ResponsableProjet> createResponsableProjet(@RequestBody ResponsableProjet responsableProjet)
        throws URISyntaxException {
        log.debug("REST request to save ResponsableProjet : {}", responsableProjet);
        if (responsableProjet.getId() != null) {
            throw new BadRequestAlertException("A new responsableProjet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsableProjet result = responsableProjetService.save(responsableProjet);
        return ResponseEntity
            .created(new URI("/api/responsable-projets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsable-projets/:id} : Updates an existing responsableProjet.
     *
     * @param id the id of the responsableProjet to save.
     * @param responsableProjet the responsableProjet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsableProjet,
     * or with status {@code 400 (Bad Request)} if the responsableProjet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsableProjet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsable-projets/{id}")
    public ResponseEntity<ResponsableProjet> updateResponsableProjet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponsableProjet responsableProjet
    ) throws URISyntaxException {
        log.debug("REST request to update ResponsableProjet : {}, {}", id, responsableProjet);
        if (responsableProjet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsableProjet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsableProjetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsableProjet result = responsableProjetService.update(responsableProjet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responsableProjet.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsable-projets/:id} : Partial updates given fields of an existing responsableProjet, field will ignore if it is null
     *
     * @param id the id of the responsableProjet to save.
     * @param responsableProjet the responsableProjet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsableProjet,
     * or with status {@code 400 (Bad Request)} if the responsableProjet is not valid,
     * or with status {@code 404 (Not Found)} if the responsableProjet is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsableProjet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsable-projets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsableProjet> partialUpdateResponsableProjet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponsableProjet responsableProjet
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponsableProjet partially : {}, {}", id, responsableProjet);
        if (responsableProjet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsableProjet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsableProjetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsableProjet> result = responsableProjetService.partialUpdate(responsableProjet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responsableProjet.getId().toString())
        );
    }

    /**
     * {@code GET  /responsable-projets} : get all the responsableProjets.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsableProjets in body.
     */
    @GetMapping("/responsable-projets")
    public ResponseEntity<List<ResponsableProjet>> getAllResponsableProjets(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ResponsableProjets");
        Page<ResponsableProjet> page;
        if (eagerload) {
            page = responsableProjetService.findAllWithEagerRelationships(pageable);
        } else {
            page = responsableProjetService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsable-projets/:id} : get the "id" responsableProjet.
     *
     * @param id the id of the responsableProjet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsableProjet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsable-projets/{id}")
    public ResponseEntity<ResponsableProjet> getResponsableProjet(@PathVariable Long id) {
        log.debug("REST request to get ResponsableProjet : {}", id);
        Optional<ResponsableProjet> responsableProjet = responsableProjetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsableProjet);
    }

    /**
     * {@code DELETE  /responsable-projets/:id} : delete the "id" responsableProjet.
     *
     * @param id the id of the responsableProjet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsable-projets/{id}")
    public ResponseEntity<Void> deleteResponsableProjet(@PathVariable Long id) {
        log.debug("REST request to delete ResponsableProjet : {}", id);
        responsableProjetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
