package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Frais;
import com.mycompany.myapp.repository.FraisRepository;
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
 * Integration tests for the {@link FraisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FraisResourceIT {

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;

    private static final String DEFAULT_DESSCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESSCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/frais";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FraisRepository fraisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFraisMockMvc;

    private Frais frais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frais createEntity(EntityManager em) {
        Frais frais = new Frais().prix(DEFAULT_PRIX).desscription(DEFAULT_DESSCRIPTION);
        return frais;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frais createUpdatedEntity(EntityManager em) {
        Frais frais = new Frais().prix(UPDATED_PRIX).desscription(UPDATED_DESSCRIPTION);
        return frais;
    }

    @BeforeEach
    public void initTest() {
        frais = createEntity(em);
    }

    @Test
    @Transactional
    void createFrais() throws Exception {
        int databaseSizeBeforeCreate = fraisRepository.findAll().size();
        // Create the Frais
        restFraisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frais)))
            .andExpect(status().isCreated());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeCreate + 1);
        Frais testFrais = fraisList.get(fraisList.size() - 1);
        assertThat(testFrais.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testFrais.getDesscription()).isEqualTo(DEFAULT_DESSCRIPTION);
    }

    @Test
    @Transactional
    void createFraisWithExistingId() throws Exception {
        // Create the Frais with an existing ID
        frais.setId(1L);

        int databaseSizeBeforeCreate = fraisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frais)))
            .andExpect(status().isBadRequest());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFrais() throws Exception {
        // Initialize the database
        fraisRepository.saveAndFlush(frais);

        // Get all the fraisList
        restFraisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frais.getId().intValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].desscription").value(hasItem(DEFAULT_DESSCRIPTION)));
    }

    @Test
    @Transactional
    void getFrais() throws Exception {
        // Initialize the database
        fraisRepository.saveAndFlush(frais);

        // Get the frais
        restFraisMockMvc
            .perform(get(ENTITY_API_URL_ID, frais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frais.getId().intValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.desscription").value(DEFAULT_DESSCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFrais() throws Exception {
        // Get the frais
        restFraisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFrais() throws Exception {
        // Initialize the database
        fraisRepository.saveAndFlush(frais);

        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();

        // Update the frais
        Frais updatedFrais = fraisRepository.findById(frais.getId()).get();
        // Disconnect from session so that the updates on updatedFrais are not directly saved in db
        em.detach(updatedFrais);
        updatedFrais.prix(UPDATED_PRIX).desscription(UPDATED_DESSCRIPTION);

        restFraisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFrais.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFrais))
            )
            .andExpect(status().isOk());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
        Frais testFrais = fraisList.get(fraisList.size() - 1);
        assertThat(testFrais.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testFrais.getDesscription()).isEqualTo(UPDATED_DESSCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFrais() throws Exception {
        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();
        frais.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frais.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frais))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFrais() throws Exception {
        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();
        frais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frais))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFrais() throws Exception {
        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();
        frais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frais)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFraisWithPatch() throws Exception {
        // Initialize the database
        fraisRepository.saveAndFlush(frais);

        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();

        // Update the frais using partial update
        Frais partialUpdatedFrais = new Frais();
        partialUpdatedFrais.setId(frais.getId());

        restFraisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrais))
            )
            .andExpect(status().isOk());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
        Frais testFrais = fraisList.get(fraisList.size() - 1);
        assertThat(testFrais.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testFrais.getDesscription()).isEqualTo(DEFAULT_DESSCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFraisWithPatch() throws Exception {
        // Initialize the database
        fraisRepository.saveAndFlush(frais);

        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();

        // Update the frais using partial update
        Frais partialUpdatedFrais = new Frais();
        partialUpdatedFrais.setId(frais.getId());

        partialUpdatedFrais.prix(UPDATED_PRIX).desscription(UPDATED_DESSCRIPTION);

        restFraisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrais))
            )
            .andExpect(status().isOk());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
        Frais testFrais = fraisList.get(fraisList.size() - 1);
        assertThat(testFrais.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testFrais.getDesscription()).isEqualTo(UPDATED_DESSCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFrais() throws Exception {
        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();
        frais.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, frais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frais))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFrais() throws Exception {
        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();
        frais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frais))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFrais() throws Exception {
        int databaseSizeBeforeUpdate = fraisRepository.findAll().size();
        frais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(frais)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frais in the database
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFrais() throws Exception {
        // Initialize the database
        fraisRepository.saveAndFlush(frais);

        int databaseSizeBeforeDelete = fraisRepository.findAll().size();

        // Delete the frais
        restFraisMockMvc
            .perform(delete(ENTITY_API_URL_ID, frais.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Frais> fraisList = fraisRepository.findAll();
        assertThat(fraisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
