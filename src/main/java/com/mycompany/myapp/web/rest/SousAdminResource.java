package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SousAdmin;
import com.mycompany.myapp.repository.SousAdminRepository;
import com.mycompany.myapp.service.SousAdminService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SousAdmin}.
 */
@RestController
@RequestMapping("/api")
public class SousAdminResource {

    private final Logger log = LoggerFactory.getLogger(SousAdminResource.class);

    private static final String ENTITY_NAME = "sousAdmin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SousAdminService sousAdminService;

    private final SousAdminRepository sousAdminRepository;

    public SousAdminResource(SousAdminService sousAdminService, SousAdminRepository sousAdminRepository) {
        this.sousAdminService = sousAdminService;
        this.sousAdminRepository = sousAdminRepository;
    }

    /**
     * {@code POST  /sous-admins} : Create a new sousAdmin.
     *
     * @param sousAdmin the sousAdmin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sousAdmin, or with status {@code 400 (Bad Request)} if the sousAdmin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sous-admins")
    public ResponseEntity<SousAdmin> createSousAdmin(@RequestBody SousAdmin sousAdmin) throws URISyntaxException {
        log.debug("REST request to save SousAdmin : {}", sousAdmin);
        if (sousAdmin.getId() != null) {
            throw new BadRequestAlertException("A new sousAdmin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SousAdmin result = sousAdminService.save(sousAdmin);
        return ResponseEntity
            .created(new URI("/api/sous-admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sous-admins/:id} : Updates an existing sousAdmin.
     *
     * @param id the id of the sousAdmin to save.
     * @param sousAdmin the sousAdmin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousAdmin,
     * or with status {@code 400 (Bad Request)} if the sousAdmin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sousAdmin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sous-admins/{id}")
    public ResponseEntity<SousAdmin> updateSousAdmin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SousAdmin sousAdmin
    ) throws URISyntaxException {
        log.debug("REST request to update SousAdmin : {}, {}", id, sousAdmin);
        if (sousAdmin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousAdmin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousAdminRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SousAdmin result = sousAdminService.update(sousAdmin);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sousAdmin.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sous-admins/:id} : Partial updates given fields of an existing sousAdmin, field will ignore if it is null
     *
     * @param id the id of the sousAdmin to save.
     * @param sousAdmin the sousAdmin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousAdmin,
     * or with status {@code 400 (Bad Request)} if the sousAdmin is not valid,
     * or with status {@code 404 (Not Found)} if the sousAdmin is not found,
     * or with status {@code 500 (Internal Server Error)} if the sousAdmin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sous-admins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SousAdmin> partialUpdateSousAdmin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SousAdmin sousAdmin
    ) throws URISyntaxException {
        log.debug("REST request to partial update SousAdmin partially : {}, {}", id, sousAdmin);
        if (sousAdmin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousAdmin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousAdminRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SousAdmin> result = sousAdminService.partialUpdate(sousAdmin);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sousAdmin.getId().toString())
        );
    }

    /**
     * {@code GET  /sous-admins} : get all the sousAdmins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sousAdmins in body.
     */
    @GetMapping("/sous-admins")
    public ResponseEntity<List<SousAdmin>> getAllSousAdmins(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SousAdmins");
        Page<SousAdmin> page = sousAdminService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sous-admins/:id} : get the "id" sousAdmin.
     *
     * @param id the id of the sousAdmin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sousAdmin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sous-admins/{id}")
    public ResponseEntity<SousAdmin> getSousAdmin(@PathVariable Long id) {
        log.debug("REST request to get SousAdmin : {}", id);
        Optional<SousAdmin> sousAdmin = sousAdminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sousAdmin);
    }

    /**
     * {@code DELETE  /sous-admins/:id} : delete the "id" sousAdmin.
     *
     * @param id the id of the sousAdmin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sous-admins/{id}")
    public ResponseEntity<Void> deleteSousAdmin(@PathVariable Long id) {
        log.debug("REST request to delete SousAdmin : {}", id);
        sousAdminService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
