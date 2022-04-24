package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ArticleProjet;
import com.mycompany.myapp.repository.ArticleProjetRepository;
import com.mycompany.myapp.service.ArticleProjetService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ArticleProjet}.
 */
@RestController
@RequestMapping("/api")
public class ArticleProjetResource {

    private final Logger log = LoggerFactory.getLogger(ArticleProjetResource.class);

    private static final String ENTITY_NAME = "articleProjet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleProjetService articleProjetService;

    private final ArticleProjetRepository articleProjetRepository;

    public ArticleProjetResource(ArticleProjetService articleProjetService, ArticleProjetRepository articleProjetRepository) {
        this.articleProjetService = articleProjetService;
        this.articleProjetRepository = articleProjetRepository;
    }

    /**
     * {@code POST  /article-projets} : Create a new articleProjet.
     *
     * @param articleProjet the articleProjet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleProjet, or with status {@code 400 (Bad Request)} if the articleProjet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-projets")
    public ResponseEntity<ArticleProjet> createArticleProjet(@RequestBody ArticleProjet articleProjet) throws URISyntaxException {
        log.debug("REST request to save ArticleProjet : {}", articleProjet);
        if (articleProjet.getId() != null) {
            throw new BadRequestAlertException("A new articleProjet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleProjet result = articleProjetService.save(articleProjet);
        return ResponseEntity
            .created(new URI("/api/article-projets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-projets/:id} : Updates an existing articleProjet.
     *
     * @param id the id of the articleProjet to save.
     * @param articleProjet the articleProjet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleProjet,
     * or with status {@code 400 (Bad Request)} if the articleProjet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleProjet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/article-projets/{id}")
    public ResponseEntity<ArticleProjet> updateArticleProjet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArticleProjet articleProjet
    ) throws URISyntaxException {
        log.debug("REST request to update ArticleProjet : {}, {}", id, articleProjet);
        if (articleProjet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleProjet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleProjetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArticleProjet result = articleProjetService.update(articleProjet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleProjet.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /article-projets/:id} : Partial updates given fields of an existing articleProjet, field will ignore if it is null
     *
     * @param id the id of the articleProjet to save.
     * @param articleProjet the articleProjet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleProjet,
     * or with status {@code 400 (Bad Request)} if the articleProjet is not valid,
     * or with status {@code 404 (Not Found)} if the articleProjet is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleProjet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/article-projets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleProjet> partialUpdateArticleProjet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArticleProjet articleProjet
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArticleProjet partially : {}, {}", id, articleProjet);
        if (articleProjet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleProjet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleProjetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleProjet> result = articleProjetService.partialUpdate(articleProjet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleProjet.getId().toString())
        );
    }

    /**
     * {@code GET  /article-projets} : get all the articleProjets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleProjets in body.
     */
    @GetMapping("/article-projets")
    public ResponseEntity<List<ArticleProjet>> getAllArticleProjets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArticleProjets");
        Page<ArticleProjet> page = articleProjetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-projets/:id} : get the "id" articleProjet.
     *
     * @param id the id of the articleProjet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleProjet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/article-projets/{id}")
    public ResponseEntity<ArticleProjet> getArticleProjet(@PathVariable Long id) {
        log.debug("REST request to get ArticleProjet : {}", id);
        Optional<ArticleProjet> articleProjet = articleProjetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleProjet);
    }

    /**
     * {@code DELETE  /article-projets/:id} : delete the "id" articleProjet.
     *
     * @param id the id of the articleProjet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/article-projets/{id}")
    public ResponseEntity<Void> deleteArticleProjet(@PathVariable Long id) {
        log.debug("REST request to delete ArticleProjet : {}", id);
        articleProjetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
