package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Fichier;
import com.niveka.repository.FichierRepository;
import com.niveka.repository.search.FichierSearchRepository;
import com.niveka.service.FichierService;
import com.niveka.service.dto.FichierDTO;
import com.niveka.service.mapper.FichierMapper;
import com.niveka.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;


import static com.niveka.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FichierResource REST controller.
 *
 * @see FichierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class FichierResourceIntTest {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_AT = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_AT = "BBBBBBBBBB";

    @Autowired
    private FichierRepository fichierRepository;

    @Autowired
    private FichierMapper fichierMapper;

    @Autowired
    private FichierService fichierService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.FichierSearchRepositoryMockConfiguration
     */
    @Autowired
    private FichierSearchRepository mockFichierSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restFichierMockMvc;

    private Fichier fichier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichierResource fichierResource = new FichierResource(fichierService);
        this.restFichierMockMvc = MockMvcBuilders.standaloneSetup(fichierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fichier createEntity() {
        Fichier fichier = new Fichier()
            .path(DEFAULT_PATH)
            .type(DEFAULT_TYPE)
            .size(DEFAULT_SIZE)
            .model(DEFAULT_MODEL)
            .modelId(DEFAULT_MODEL_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return fichier;
    }

    @Before
    public void initTest() {
        fichierRepository.deleteAll();
        fichier = createEntity();
    }

    @Test
    public void createFichier() throws Exception {
        int databaseSizeBeforeCreate = fichierRepository.findAll().size();

        // Create the Fichier
        FichierDTO fichierDTO = fichierMapper.toDto(fichier);
        restFichierMockMvc.perform(post("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
            .andExpect(status().isCreated());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeCreate + 1);
        Fichier testFichier = fichierList.get(fichierList.size() - 1);
        assertThat(testFichier.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testFichier.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFichier.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testFichier.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testFichier.getModelId()).isEqualTo(DEFAULT_MODEL_ID);
        assertThat(testFichier.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFichier.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testFichier.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);

        // Validate the Fichier in Elasticsearch
        verify(mockFichierSearchRepository, times(1)).save(testFichier);
    }

    @Test
    public void createFichierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichierRepository.findAll().size();

        // Create the Fichier with an existing ID
        fichier.setId("existing_id");
        FichierDTO fichierDTO = fichierMapper.toDto(fichier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichierMockMvc.perform(post("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeCreate);

        // Validate the Fichier in Elasticsearch
        verify(mockFichierSearchRepository, times(0)).save(fichier);
    }

    @Test
    public void getAllFichiers() throws Exception {
        // Initialize the database
        fichierRepository.save(fichier);

        // Get all the fichierList
        restFichierMockMvc.perform(get("/api/fichiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichier.getId())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].modelId").value(hasItem(DEFAULT_MODEL_ID.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    public void getFichier() throws Exception {
        // Initialize the database
        fichierRepository.save(fichier);

        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", fichier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichier.getId()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.modelId").value(DEFAULT_MODEL_ID.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingFichier() throws Exception {
        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFichier() throws Exception {
        // Initialize the database
        fichierRepository.save(fichier);

        int databaseSizeBeforeUpdate = fichierRepository.findAll().size();

        // Update the fichier
        Fichier updatedFichier = fichierRepository.findById(fichier.getId()).get();
        updatedFichier
            .path(UPDATED_PATH)
            .type(UPDATED_TYPE)
            .size(UPDATED_SIZE)
            .model(UPDATED_MODEL)
            .modelId(UPDATED_MODEL_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        FichierDTO fichierDTO = fichierMapper.toDto(updatedFichier);

        restFichierMockMvc.perform(put("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
            .andExpect(status().isOk());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeUpdate);
        Fichier testFichier = fichierList.get(fichierList.size() - 1);
        assertThat(testFichier.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testFichier.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFichier.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testFichier.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testFichier.getModelId()).isEqualTo(UPDATED_MODEL_ID);
        assertThat(testFichier.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFichier.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testFichier.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);

        // Validate the Fichier in Elasticsearch
        verify(mockFichierSearchRepository, times(1)).save(testFichier);
    }

    @Test
    public void updateNonExistingFichier() throws Exception {
        int databaseSizeBeforeUpdate = fichierRepository.findAll().size();

        // Create the Fichier
        FichierDTO fichierDTO = fichierMapper.toDto(fichier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichierMockMvc.perform(put("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Fichier in Elasticsearch
        verify(mockFichierSearchRepository, times(0)).save(fichier);
    }

    @Test
    public void deleteFichier() throws Exception {
        // Initialize the database
        fichierRepository.save(fichier);

        int databaseSizeBeforeDelete = fichierRepository.findAll().size();

        // Delete the fichier
        restFichierMockMvc.perform(delete("/api/fichiers/{id}", fichier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Fichier in Elasticsearch
        verify(mockFichierSearchRepository, times(1)).deleteById(fichier.getId());
    }

    @Test
    public void searchFichier() throws Exception {
        // Initialize the database
        fichierRepository.save(fichier);
        when(mockFichierSearchRepository.search(queryStringQuery("id:" + fichier.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fichier), PageRequest.of(0, 1), 1));
        // Search the fichier
        restFichierMockMvc.perform(get("/api/_search/fichiers?query=id:" + fichier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichier.getId())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].modelId").value(hasItem(DEFAULT_MODEL_ID)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fichier.class);
        Fichier fichier1 = new Fichier();
        fichier1.setId("id1");
        Fichier fichier2 = new Fichier();
        fichier2.setId(fichier1.getId());
        assertThat(fichier1).isEqualTo(fichier2);
        fichier2.setId("id2");
        assertThat(fichier1).isNotEqualTo(fichier2);
        fichier1.setId(null);
        assertThat(fichier1).isNotEqualTo(fichier2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichierDTO.class);
        FichierDTO fichierDTO1 = new FichierDTO();
        fichierDTO1.setId("id1");
        FichierDTO fichierDTO2 = new FichierDTO();
        assertThat(fichierDTO1).isNotEqualTo(fichierDTO2);
        fichierDTO2.setId(fichierDTO1.getId());
        assertThat(fichierDTO1).isEqualTo(fichierDTO2);
        fichierDTO2.setId("id2");
        assertThat(fichierDTO1).isNotEqualTo(fichierDTO2);
        fichierDTO1.setId(null);
        assertThat(fichierDTO1).isNotEqualTo(fichierDTO2);
    }
}
