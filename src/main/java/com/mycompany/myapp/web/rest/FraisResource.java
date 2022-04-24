package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Frais;
import com.mycompany.myapp.repository.FraisRepository;
import com.mycompany.myapp.service.FraisService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Frais}.
 */
@RestController
@RequestMapping("/api")
public class FraisResource {

    private final Logger log = LoggerFactory.getLogger(FraisResource.class);

    private static final String ENTITY_NAME = "frais";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraisService fraisService;

    private final FraisRepository fraisRepository;

    public FraisResource(FraisService fraisService, FraisRepository fraisRepository) {
        this.fraisService = fraisService;
        this.fraisRepository = fraisRepository;
    }

    /**
     * {@code POST  /frais} : Create a new frais.
     *
     * @param frais the frais to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frais, or with status {@code 400 (Bad Request)} if the frais has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frais")
    public ResponseEntity<Frais> createFrais(@RequestBody Frais frais) throws URISyntaxException {
        log.debug("REST request to save Frais : {}", frais);
        if (frais.getId() != null) {
            throw new BadRequestAlertException("A new frais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Frais result = fraisService.save(frais);
        return ResponseEntity
            .created(new URI("/api/frais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frais/:id} : Updates an existing frais.
     *
     * @param id the id of the frais to save.
     * @param frais the frais to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frais,
     * or with status {@code 400 (Bad Request)} if the frais is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frais couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frais/{id}")
    public ResponseEntity<Frais> updateFrais(@PathVariable(value = "id", required = false) final Long id, @RequestBody Frais frais)
        throws URISyntaxException {
        log.debug("REST request to update Frais : {}, {}", id, frais);
        if (frais.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frais.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Frais result = fraisService.update(frais);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, frais.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /frais/:id} : Partial updates given fields of an existing frais, field will ignore if it is null
     *
     * @param id the id of the frais to save.
     * @param frais the frais to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frais,
     * or with status {@code 400 (Bad Request)} if the frais is not valid,
     * or with status {@code 404 (Not Found)} if the frais is not found,
     * or with status {@code 500 (Internal Server Error)} if the frais couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/frais/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Frais> partialUpdateFrais(@PathVariable(value = "id", required = false) final Long id, @RequestBody Frais frais)
        throws URISyntaxException {
        log.debug("REST request to partial update Frais partially : {}, {}", id, frais);
        if (frais.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frais.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Frais> result = fraisService.partialUpdate(frais);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, frais.getId().toString())
        );
    }

    /**
     * {@code GET  /frais} : get all the frais.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frais in body.
     */
    @GetMapping("/frais")
    public ResponseEntity<List<Frais>> getAllFrais(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Frais");
        Page<Frais> page = fraisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frais/:id} : get the "id" frais.
     *
     * @param id the id of the frais to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frais, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frais/{id}")
    public ResponseEntity<Frais> getFrais(@PathVariable Long id) {
        log.debug("REST request to get Frais : {}", id);
        Optional<Frais> frais = fraisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frais);
    }

    /**
     * {@code DELETE  /frais/:id} : delete the "id" frais.
     *
     * @param id the id of the frais to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frais/{id}")
    public ResponseEntity<Void> deleteFrais(@PathVariable Long id) {
        log.debug("REST request to delete Frais : {}", id);
        fraisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
