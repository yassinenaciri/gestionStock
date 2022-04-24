package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ResponsableProjet;
import com.mycompany.myapp.repository.ResponsableProjetRepository;
import com.mycompany.myapp.service.ResponsableProjetService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ResponsableProjetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResponsableProjetResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/responsable-projets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsableProjetRepository responsableProjetRepository;

    @Mock
    private ResponsableProjetRepository responsableProjetRepositoryMock;

    @Mock
    private ResponsableProjetService responsableProjetServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsableProjetMockMvc;

    private ResponsableProjet responsableProjet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsableProjet createEntity(EntityManager em) {
        ResponsableProjet responsableProjet = new ResponsableProjet().nom(DEFAULT_NOM).prenom(DEFAULT_PRENOM).mail(DEFAULT_MAIL);
        return responsableProjet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsableProjet createUpdatedEntity(EntityManager em) {
        ResponsableProjet responsableProjet = new ResponsableProjet().nom(UPDATED_NOM).prenom(UPDATED_PRENOM).mail(UPDATED_MAIL);
        return responsableProjet;
    }

    @BeforeEach
    public void initTest() {
        responsableProjet = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsableProjet() throws Exception {
        int databaseSizeBeforeCreate = responsableProjetRepository.findAll().size();
        // Create the ResponsableProjet
        restResponsableProjetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isCreated());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsableProjet testResponsableProjet = responsableProjetList.get(responsableProjetList.size() - 1);
        assertThat(testResponsableProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testResponsableProjet.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testResponsableProjet.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    void createResponsableProjetWithExistingId() throws Exception {
        // Create the ResponsableProjet with an existing ID
        responsableProjet.setId(1L);

        int databaseSizeBeforeCreate = responsableProjetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsableProjetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResponsableProjets() throws Exception {
        // Initialize the database
        responsableProjetRepository.saveAndFlush(responsableProjet);

        // Get all the responsableProjetList
        restResponsableProjetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsableProjet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsableProjetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsableProjetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsableProjetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsableProjetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsableProjetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsableProjetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsableProjetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsableProjetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getResponsableProjet() throws Exception {
        // Initialize the database
        responsableProjetRepository.saveAndFlush(responsableProjet);

        // Get the responsableProjet
        restResponsableProjetMockMvc
            .perform(get(ENTITY_API_URL_ID, responsableProjet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsableProjet.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL));
    }

    @Test
    @Transactional
    void getNonExistingResponsableProjet() throws Exception {
        // Get the responsableProjet
        restResponsableProjetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResponsableProjet() throws Exception {
        // Initialize the database
        responsableProjetRepository.saveAndFlush(responsableProjet);

        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();

        // Update the responsableProjet
        ResponsableProjet updatedResponsableProjet = responsableProjetRepository.findById(responsableProjet.getId()).get();
        // Disconnect from session so that the updates on updatedResponsableProjet are not directly saved in db
        em.detach(updatedResponsableProjet);
        updatedResponsableProjet.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).mail(UPDATED_MAIL);

        restResponsableProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResponsableProjet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResponsableProjet))
            )
            .andExpect(status().isOk());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
        ResponsableProjet testResponsableProjet = responsableProjetList.get(responsableProjetList.size() - 1);
        assertThat(testResponsableProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testResponsableProjet.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testResponsableProjet.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    void putNonExistingResponsableProjet() throws Exception {
        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();
        responsableProjet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsableProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsableProjet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsableProjet() throws Exception {
        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();
        responsableProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsableProjet() throws Exception {
        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();
        responsableProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableProjetMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsableProjetWithPatch() throws Exception {
        // Initialize the database
        responsableProjetRepository.saveAndFlush(responsableProjet);

        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();

        // Update the responsableProjet using partial update
        ResponsableProjet partialUpdatedResponsableProjet = new ResponsableProjet();
        partialUpdatedResponsableProjet.setId(responsableProjet.getId());

        partialUpdatedResponsableProjet.mail(UPDATED_MAIL);

        restResponsableProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsableProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsableProjet))
            )
            .andExpect(status().isOk());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
        ResponsableProjet testResponsableProjet = responsableProjetList.get(responsableProjetList.size() - 1);
        assertThat(testResponsableProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testResponsableProjet.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testResponsableProjet.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    void fullUpdateResponsableProjetWithPatch() throws Exception {
        // Initialize the database
        responsableProjetRepository.saveAndFlush(responsableProjet);

        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();

        // Update the responsableProjet using partial update
        ResponsableProjet partialUpdatedResponsableProjet = new ResponsableProjet();
        partialUpdatedResponsableProjet.setId(responsableProjet.getId());

        partialUpdatedResponsableProjet.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).mail(UPDATED_MAIL);

        restResponsableProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsableProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsableProjet))
            )
            .andExpect(status().isOk());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
        ResponsableProjet testResponsableProjet = responsableProjetList.get(responsableProjetList.size() - 1);
        assertThat(testResponsableProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testResponsableProjet.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testResponsableProjet.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    void patchNonExistingResponsableProjet() throws Exception {
        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();
        responsableProjet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsableProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsableProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsableProjet() throws Exception {
        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();
        responsableProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsableProjet() throws Exception {
        int databaseSizeBeforeUpdate = responsableProjetRepository.findAll().size();
        responsableProjet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableProjetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsableProjet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsableProjet in the database
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsableProjet() throws Exception {
        // Initialize the database
        responsableProjetRepository.saveAndFlush(responsableProjet);

        int databaseSizeBeforeDelete = responsableProjetRepository.findAll().size();

        // Delete the responsableProjet
        restResponsableProjetMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsableProjet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsableProjet> responsableProjetList = responsableProjetRepository.findAll();
        assertThat(responsableProjetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
