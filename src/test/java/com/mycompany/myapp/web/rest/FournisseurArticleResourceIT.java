package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FournisseurArticle;
import com.mycompany.myapp.repository.FournisseurArticleRepository;
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
 * Integration tests for the {@link FournisseurArticleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FournisseurArticleResourceIT {

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;

    private static final String ENTITY_API_URL = "/api/fournisseur-articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FournisseurArticleRepository fournisseurArticleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFournisseurArticleMockMvc;

    private FournisseurArticle fournisseurArticle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FournisseurArticle createEntity(EntityManager em) {
        FournisseurArticle fournisseurArticle = new FournisseurArticle().prix(DEFAULT_PRIX);
        return fournisseurArticle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FournisseurArticle createUpdatedEntity(EntityManager em) {
        FournisseurArticle fournisseurArticle = new FournisseurArticle().prix(UPDATED_PRIX);
        return fournisseurArticle;
    }

    @BeforeEach
    public void initTest() {
        fournisseurArticle = createEntity(em);
    }

    @Test
    @Transactional
    void createFournisseurArticle() throws Exception {
        int databaseSizeBeforeCreate = fournisseurArticleRepository.findAll().size();
        // Create the FournisseurArticle
        restFournisseurArticleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isCreated());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeCreate + 1);
        FournisseurArticle testFournisseurArticle = fournisseurArticleList.get(fournisseurArticleList.size() - 1);
        assertThat(testFournisseurArticle.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void createFournisseurArticleWithExistingId() throws Exception {
        // Create the FournisseurArticle with an existing ID
        fournisseurArticle.setId(1L);

        int databaseSizeBeforeCreate = fournisseurArticleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurArticleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFournisseurArticles() throws Exception {
        // Initialize the database
        fournisseurArticleRepository.saveAndFlush(fournisseurArticle);

        // Get all the fournisseurArticleList
        restFournisseurArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseurArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }

    @Test
    @Transactional
    void getFournisseurArticle() throws Exception {
        // Initialize the database
        fournisseurArticleRepository.saveAndFlush(fournisseurArticle);

        // Get the fournisseurArticle
        restFournisseurArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, fournisseurArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseurArticle.getId().intValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFournisseurArticle() throws Exception {
        // Get the fournisseurArticle
        restFournisseurArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFournisseurArticle() throws Exception {
        // Initialize the database
        fournisseurArticleRepository.saveAndFlush(fournisseurArticle);

        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();

        // Update the fournisseurArticle
        FournisseurArticle updatedFournisseurArticle = fournisseurArticleRepository.findById(fournisseurArticle.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseurArticle are not directly saved in db
        em.detach(updatedFournisseurArticle);
        updatedFournisseurArticle.prix(UPDATED_PRIX);

        restFournisseurArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFournisseurArticle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFournisseurArticle))
            )
            .andExpect(status().isOk());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
        FournisseurArticle testFournisseurArticle = fournisseurArticleList.get(fournisseurArticleList.size() - 1);
        assertThat(testFournisseurArticle.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    void putNonExistingFournisseurArticle() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();
        fournisseurArticle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fournisseurArticle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFournisseurArticle() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();
        fournisseurArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFournisseurArticle() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();
        fournisseurArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurArticleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFournisseurArticleWithPatch() throws Exception {
        // Initialize the database
        fournisseurArticleRepository.saveAndFlush(fournisseurArticle);

        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();

        // Update the fournisseurArticle using partial update
        FournisseurArticle partialUpdatedFournisseurArticle = new FournisseurArticle();
        partialUpdatedFournisseurArticle.setId(fournisseurArticle.getId());

        restFournisseurArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseurArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseurArticle))
            )
            .andExpect(status().isOk());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
        FournisseurArticle testFournisseurArticle = fournisseurArticleList.get(fournisseurArticleList.size() - 1);
        assertThat(testFournisseurArticle.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void fullUpdateFournisseurArticleWithPatch() throws Exception {
        // Initialize the database
        fournisseurArticleRepository.saveAndFlush(fournisseurArticle);

        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();

        // Update the fournisseurArticle using partial update
        FournisseurArticle partialUpdatedFournisseurArticle = new FournisseurArticle();
        partialUpdatedFournisseurArticle.setId(fournisseurArticle.getId());

        partialUpdatedFournisseurArticle.prix(UPDATED_PRIX);

        restFournisseurArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseurArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseurArticle))
            )
            .andExpect(status().isOk());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
        FournisseurArticle testFournisseurArticle = fournisseurArticleList.get(fournisseurArticleList.size() - 1);
        assertThat(testFournisseurArticle.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    void patchNonExistingFournisseurArticle() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();
        fournisseurArticle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fournisseurArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFournisseurArticle() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();
        fournisseurArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isBadRequest());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFournisseurArticle() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurArticleRepository.findAll().size();
        fournisseurArticle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurArticleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurArticle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FournisseurArticle in the database
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFournisseurArticle() throws Exception {
        // Initialize the database
        fournisseurArticleRepository.saveAndFlush(fournisseurArticle);

        int databaseSizeBeforeDelete = fournisseurArticleRepository.findAll().size();

        // Delete the fournisseurArticle
        restFournisseurArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, fournisseurArticle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FournisseurArticle> fournisseurArticleList = fournisseurArticleRepository.findAll();
        assertThat(fournisseurArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
