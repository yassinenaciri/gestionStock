package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.SousAdmin;
import com.mycompany.myapp.repository.SousAdminRepository;
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
 * Integration tests for the {@link SousAdminResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SousAdminResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sous-admins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SousAdminRepository sousAdminRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSousAdminMockMvc;

    private SousAdmin sousAdmin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousAdmin createEntity(EntityManager em) {
        SousAdmin sousAdmin = new SousAdmin().nom(DEFAULT_NOM).prenom(DEFAULT_PRENOM).mail(DEFAULT_MAIL);
        return sousAdmin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousAdmin createUpdatedEntity(EntityManager em) {
        SousAdmin sousAdmin = new SousAdmin().nom(UPDATED_NOM).prenom(UPDATED_PRENOM).mail(UPDATED_MAIL);
        return sousAdmin;
    }

    @BeforeEach
    public void initTest() {
        sousAdmin = createEntity(em);
    }

    @Test
    @Transactional
    void createSousAdmin() throws Exception {
        int databaseSizeBeforeCreate = sousAdminRepository.findAll().size();
        // Create the SousAdmin
        restSousAdminMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousAdmin)))
            .andExpect(status().isCreated());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeCreate + 1);
        SousAdmin testSousAdmin = sousAdminList.get(sousAdminList.size() - 1);
        assertThat(testSousAdmin.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSousAdmin.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testSousAdmin.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    void createSousAdminWithExistingId() throws Exception {
        // Create the SousAdmin with an existing ID
        sousAdmin.setId(1L);

        int databaseSizeBeforeCreate = sousAdminRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousAdminMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousAdmin)))
            .andExpect(status().isBadRequest());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSousAdmins() throws Exception {
        // Initialize the database
        sousAdminRepository.saveAndFlush(sousAdmin);

        // Get all the sousAdminList
        restSousAdminMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousAdmin.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)));
    }

    @Test
    @Transactional
    void getSousAdmin() throws Exception {
        // Initialize the database
        sousAdminRepository.saveAndFlush(sousAdmin);

        // Get the sousAdmin
        restSousAdminMockMvc
            .perform(get(ENTITY_API_URL_ID, sousAdmin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sousAdmin.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL));
    }

    @Test
    @Transactional
    void getNonExistingSousAdmin() throws Exception {
        // Get the sousAdmin
        restSousAdminMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSousAdmin() throws Exception {
        // Initialize the database
        sousAdminRepository.saveAndFlush(sousAdmin);

        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();

        // Update the sousAdmin
        SousAdmin updatedSousAdmin = sousAdminRepository.findById(sousAdmin.getId()).get();
        // Disconnect from session so that the updates on updatedSousAdmin are not directly saved in db
        em.detach(updatedSousAdmin);
        updatedSousAdmin.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).mail(UPDATED_MAIL);

        restSousAdminMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSousAdmin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSousAdmin))
            )
            .andExpect(status().isOk());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
        SousAdmin testSousAdmin = sousAdminList.get(sousAdminList.size() - 1);
        assertThat(testSousAdmin.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSousAdmin.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSousAdmin.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    void putNonExistingSousAdmin() throws Exception {
        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();
        sousAdmin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousAdminMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sousAdmin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousAdmin))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSousAdmin() throws Exception {
        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();
        sousAdmin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousAdminMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousAdmin))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSousAdmin() throws Exception {
        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();
        sousAdmin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousAdminMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousAdmin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSousAdminWithPatch() throws Exception {
        // Initialize the database
        sousAdminRepository.saveAndFlush(sousAdmin);

        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();

        // Update the sousAdmin using partial update
        SousAdmin partialUpdatedSousAdmin = new SousAdmin();
        partialUpdatedSousAdmin.setId(sousAdmin.getId());

        restSousAdminMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousAdmin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousAdmin))
            )
            .andExpect(status().isOk());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
        SousAdmin testSousAdmin = sousAdminList.get(sousAdminList.size() - 1);
        assertThat(testSousAdmin.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSousAdmin.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testSousAdmin.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    void fullUpdateSousAdminWithPatch() throws Exception {
        // Initialize the database
        sousAdminRepository.saveAndFlush(sousAdmin);

        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();

        // Update the sousAdmin using partial update
        SousAdmin partialUpdatedSousAdmin = new SousAdmin();
        partialUpdatedSousAdmin.setId(sousAdmin.getId());

        partialUpdatedSousAdmin.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).mail(UPDATED_MAIL);

        restSousAdminMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousAdmin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousAdmin))
            )
            .andExpect(status().isOk());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
        SousAdmin testSousAdmin = sousAdminList.get(sousAdminList.size() - 1);
        assertThat(testSousAdmin.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSousAdmin.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSousAdmin.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    void patchNonExistingSousAdmin() throws Exception {
        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();
        sousAdmin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousAdminMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sousAdmin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousAdmin))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSousAdmin() throws Exception {
        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();
        sousAdmin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousAdminMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousAdmin))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSousAdmin() throws Exception {
        int databaseSizeBeforeUpdate = sousAdminRepository.findAll().size();
        sousAdmin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousAdminMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sousAdmin))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousAdmin in the database
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSousAdmin() throws Exception {
        // Initialize the database
        sousAdminRepository.saveAndFlush(sousAdmin);

        int databaseSizeBeforeDelete = sousAdminRepository.findAll().size();

        // Delete the sousAdmin
        restSousAdminMockMvc
            .perform(delete(ENTITY_API_URL_ID, sousAdmin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SousAdmin> sousAdminList = sousAdminRepository.findAll();
        assertThat(sousAdminList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
