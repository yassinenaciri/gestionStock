package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ArticleProjet;
import com.mycompany.myapp.repository.ArticleProjetRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArticleProjetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleProjetResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VENDU = false;
    private static final Boolean UPDATED_VENDU = true;

    private static final String ENTITY_API_URL = "/api/article-projets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticleProjetRepository articleProjetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleProjetMockMvc;

    private ArticleProjet articleProjet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleProjet createEntity(EntityManager em) {
        ArticleProjet articleProjet = new ArticleProjet().nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION).vendu(DEFAULT_VENDU);
        return articleProjet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleProjet createUpdatedEntity(EntityManager em) {
        ArticleProjet articleProjet = new ArticleProjet().nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).vendu(UPDATED_VENDU);
        return articleProjet;
    }

    @BeforeEach
    public void initTest() {
        articleProjet = createEntity(em);
    }

    @Test
    @Transactional
    void createArticleProjet() throws Exception {
        int databaseSizeBeforeCreate = articleProjetRepository.findAll().size();
        // Create the ArticleProjet
        restArticleProjetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleProjet)))
            .andExpect(status().isCreated());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleProjet testArticleProjet = articleProjetList.get(articleProjetList.size() - 1);
        assertThat(testArticleProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testArticleProjet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArticleProjet.getVendu()).isEqualTo(DEFAULT_VENDU);
    }

    @Test
    @Transactional
    void createArticleProjetWithExistingId() throws Exception {
        // Create the ArticleProjet with an existing ID
        articleProjet.setId(1L);

        int databaseSizeBeforeCreate = articleProjetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleProjetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleProjet)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArticleProjets() throws Exception {
        // Initialize the database
        articleProjetRepository.saveAndFlush(articleProjet);

        // Get all the articleProjetList
        restArticleProjetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleProjet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].vendu").value(hasItem(DEFAULT_VENDU.booleanValue())));
    }

    @Test
    @Transactional
    void getArticleProjet() throws Exception {
        // Initialize the database
        articleProjetRepository.saveAndFlush(articleProjet);

        // Get the articleProjet
        restArticleProjetMockMvc
            .perform(get(ENTITY_API_URL_ID, articleProjet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleProjet.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.vendu").value(DEFAULT_VENDU.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingArticleProjet() throws Exception {
        // Get the articleProjet
        restArticleProjetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArticleProjet() throws Exception {
        // Initialize the database
        articleProjetRepository.saveAndFlush(articleProjet);

        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();

        // Update the articleProjet
        ArticleProjet updatedArticleProjet = articleProjetRepository.findById(articleProjet.getId()).get();
        // Disconnect from session so that the updates on updatedArticleProjet are not directly saved in db
        em.detach(updatedArticleProjet);
        updatedArticleProjet.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).vendu(UPDATED_VENDU);

        restArticleProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArticleProjet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArticleProjet))
            )
            .andExpect(status().isOk());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
        ArticleProjet testArticleProjet = articleProjetList.get(articleProjetList.size() - 1);
        assertThat(testArticleProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testArticleProjet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticleProjet.getVendu()).isEqualTo(UPDATED_VENDU);
    }

    @Test
    @Transactional
    void putNonExistingArticleProjet() throws Exception {
        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();
        articleProjet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleProjet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleProjet() throws Exception {
        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();
        articleProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleProjet() throws Exception {
        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();
        articleProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleProjetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleProjet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleProjetWithPatch() throws Exception {
        // Initialize the database
        articleProjetRepository.saveAndFlush(articleProjet);

        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();

        // Update the articleProjet using partial update
        ArticleProjet partialUpdatedArticleProjet = new ArticleProjet();
        partialUpdatedArticleProjet.setId(articleProjet.getId());

        partialUpdatedArticleProjet.description(UPDATED_DESCRIPTION);

        restArticleProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleProjet))
            )
            .andExpect(status().isOk());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
        ArticleProjet testArticleProjet = articleProjetList.get(articleProjetList.size() - 1);
        assertThat(testArticleProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testArticleProjet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticleProjet.getVendu()).isEqualTo(DEFAULT_VENDU);
    }

    @Test
    @Transactional
    void fullUpdateArticleProjetWithPatch() throws Exception {
        // Initialize the database
        articleProjetRepository.saveAndFlush(articleProjet);

        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();

        // Update the articleProjet using partial update
        ArticleProjet partialUpdatedArticleProjet = new ArticleProjet();
        partialUpdatedArticleProjet.setId(articleProjet.getId());

        partialUpdatedArticleProjet.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).vendu(UPDATED_VENDU);

        restArticleProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleProjet))
            )
            .andExpect(status().isOk());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
        ArticleProjet testArticleProjet = articleProjetList.get(articleProjetList.size() - 1);
        assertThat(testArticleProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testArticleProjet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticleProjet.getVendu()).isEqualTo(UPDATED_VENDU);
    }

    @Test
    @Transactional
    void patchNonExistingArticleProjet() throws Exception {
        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();
        articleProjet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleProjet() throws Exception {
        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();
        articleProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleProjet() throws Exception {
        int databaseSizeBeforeUpdate = articleProjetRepository.findAll().size();
        articleProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleProjetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(articleProjet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleProjet in the database
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleProjet() throws Exception {
        // Initialize the database
        articleProjetRepository.saveAndFlush(articleProjet);

        int databaseSizeBeforeDelete = articleProjetRepository.findAll().size();

        // Delete the articleProjet
        restArticleProjetMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleProjet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleProjet> articleProjetList = articleProjetRepository.findAll();
        assertThat(articleProjetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
