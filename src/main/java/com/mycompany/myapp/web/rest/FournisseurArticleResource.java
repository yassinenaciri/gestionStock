package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FournisseurArticle;
import com.mycompany.myapp.repository.FournisseurArticleRepository;
import com.mycompany.myapp.service.FournisseurArticleService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FournisseurArticle}.
 */
@RestController
@RequestMapping("/api")
public class FournisseurArticleResource {

    private final Logger log = LoggerFactory.getLogger(FournisseurArticleResource.class);

    private static final String ENTITY_NAME = "fournisseurArticle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FournisseurArticleService fournisseurArticleService;

    private final FournisseurArticleRepository fournisseurArticleRepository;

    public FournisseurArticleResource(
        FournisseurArticleService fournisseurArticleService,
        FournisseurArticleRepository fournisseurArticleRepository
    ) {
        this.fournisseurArticleService = fournisseurArticleService;
        this.fournisseurArticleRepository = fournisseurArticleRepository;
    }

    /**
     * {@code POST  /fournisseur-articles} : Create a new fournisseurArticle.
     *
     * @param fournisseurArticle the fournisseurArticle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fournisseurArticle, or with status {@code 400 (Bad Request)} if the fournisseurArticle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fournisseur-articles")
    public ResponseEntity<FournisseurArticle> createFournisseurArticle(@RequestBody FournisseurArticle fournisseurArticle)
        throws URISyntaxException {
        log.debug("REST request to save FournisseurArticle : {}", fournisseurArticle);
        if (fournisseurArticle.getId() != null) {
            throw new BadRequestAlertException("A new fournisseurArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FournisseurArticle result = fournisseurArticleService.save(fournisseurArticle);
        return ResponseEntity
            .created(new URI("/api/fournisseur-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fournisseur-articles/:id} : Updates an existing fournisseurArticle.
     *
     * @param id the id of the fournisseurArticle to save.
     * @param fournisseurArticle the fournisseurArticle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournisseurArticle,
     * or with status {@code 400 (Bad Request)} if the fournisseurArticle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fournisseurArticle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fournisseur-articles/{id}")
    public ResponseEntity<FournisseurArticle> updateFournisseurArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FournisseurArticle fournisseurArticle
    ) throws URISyntaxException {
        log.debug("REST request to update FournisseurArticle : {}, {}", id, fournisseurArticle);
        if (fournisseurArticle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fournisseurArticle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fournisseurArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FournisseurArticle result = fournisseurArticleService.update(fournisseurArticle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fournisseurArticle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fournisseur-articles/:id} : Partial updates given fields of an existing fournisseurArticle, field will ignore if it is null
     *
     * @param id the id of the fournisseurArticle to save.
     * @param fournisseurArticle the fournisseurArticle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournisseurArticle,
     * or with status {@code 400 (Bad Request)} if the fournisseurArticle is not valid,
     * or with status {@code 404 (Not Found)} if the fournisseurArticle is not found,
     * or with status {@code 500 (Internal Server Error)} if the fournisseurArticle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fournisseur-articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FournisseurArticle> partialUpdateFournisseurArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FournisseurArticle fournisseurArticle
    ) throws URISyntaxException {
        log.debug("REST request to partial update FournisseurArticle partially : {}, {}", id, fournisseurArticle);
        if (fournisseurArticle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fournisseurArticle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fournisseurArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FournisseurArticle> result = fournisseurArticleService.partialUpdate(fournisseurArticle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fournisseurArticle.getId().toString())
        );
    }

    /**
     * {@code GET  /fournisseur-articles} : get all the fournisseurArticles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fournisseurArticles in body.
     */
    @GetMapping("/fournisseur-articles")
    public ResponseEntity<List<FournisseurArticle>> getAllFournisseurArticles(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FournisseurArticles");
        Page<FournisseurArticle> page = fournisseurArticleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fournisseur-articles/:id} : get the "id" fournisseurArticle.
     *
     * @param id the id of the fournisseurArticle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fournisseurArticle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fournisseur-articles/{id}")
    public ResponseEntity<FournisseurArticle> getFournisseurArticle(@PathVariable Long id) {
        log.debug("REST request to get FournisseurArticle : {}", id);
        Optional<FournisseurArticle> fournisseurArticle = fournisseurArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fournisseurArticle);
    }

    /**
     * {@code DELETE  /fournisseur-articles/:id} : delete the "id" fournisseurArticle.
     *
     * @param id the id of the fournisseurArticle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fournisseur-articles/{id}")
    public ResponseEntity<Void> deleteFournisseurArticle(@PathVariable Long id) {
        log.debug("REST request to delete FournisseurArticle : {}", id);
        fournisseurArticleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
