package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Rapport;
import com.niveka.repository.RapportRepository;
import com.niveka.repository.search.RapportSearchRepository;
import com.niveka.service.RapportService;
import com.niveka.service.dto.RapportDTO;
import com.niveka.service.mapper.RapportMapper;
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
 * Test class for the RapportResource REST controller.
 *
 * @see RapportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class RapportResourceIntTest {

    private static final String DEFAULT_OBJET = "AAAAAAAAAA";
    private static final String UPDATED_OBJET = "BBBBBBBBBB";

    private static final String DEFAULT_COPIES = "AAAAAAAAAA";
    private static final String UPDATED_COPIES = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_AT = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_AT = "BBBBBBBBBB";

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private RapportMapper rapportMapper;

    @Autowired
    private RapportService rapportService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.RapportSearchRepositoryMockConfiguration
     */
    @Autowired
    private RapportSearchRepository mockRapportSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restRapportMockMvc;

    private Rapport rapport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RapportResource rapportResource = new RapportResource(rapportService, objetService);
        this.restRapportMockMvc = MockMvcBuilders.standaloneSetup(rapportResource)
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
    public static Rapport createEntity() {
        Rapport rapport = new Rapport()
            //.objets(DEFAULT_OBJET)
            //.copies(DEFAULT_COPIES)
            .contenu(DEFAULT_CONTENU)
            .type(DEFAULT_TYPE)
            .position(DEFAULT_POSITION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return rapport;
    }

    @Before
    public void initTest() {
        rapportRepository.deleteAll();
        rapport = createEntity();
    }

    @Test
    public void createRapport() throws Exception {
        int databaseSizeBeforeCreate = rapportRepository.findAll().size();

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);
        restRapportMockMvc.perform(post("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapportDTO)))
            .andExpect(status().isCreated());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate + 1);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        //assertThat(testRapport.getObjet()).isEqualTo(DEFAULT_OBJET);
        assertThat(testRapport.getCopies()).isEqualTo(DEFAULT_COPIES);
        assertThat(testRapport.getContenu()).isEqualTo(DEFAULT_CONTENU);
        assertThat(testRapport.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRapport.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testRapport.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRapport.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRapport.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);

        // Validate the Rapport in Elasticsearch
        verify(mockRapportSearchRepository, times(1)).save(testRapport);
    }

    @Test
    public void createRapportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rapportRepository.findAll().size();

        // Create the Rapport with an existing ID
        rapport.setId("existing_id");
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRapportMockMvc.perform(post("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate);

        // Validate the Rapport in Elasticsearch
        verify(mockRapportSearchRepository, times(0)).save(rapport);
    }

    @Test
    public void getAllRapports() throws Exception {
        // Initialize the database
        rapportRepository.save(rapport);

        // Get all the rapportList
        restRapportMockMvc.perform(get("/api/rapports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rapport.getId())))
            .andExpect(jsonPath("$.[*].objet").value(hasItem(DEFAULT_OBJET.toString())))
            .andExpect(jsonPath("$.[*].copies").value(hasItem(DEFAULT_COPIES.toString())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }

    @Test
    public void getRapport() throws Exception {
        // Initialize the database
        rapportRepository.save(rapport);

        // Get the rapport
        restRapportMockMvc.perform(get("/api/rapports/{id}", rapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rapport.getId()))
            .andExpect(jsonPath("$.objet").value(DEFAULT_OBJET.toString()))
            .andExpect(jsonPath("$.copies").value(DEFAULT_COPIES.toString()))
            .andExpect(jsonPath("$.contenu").value(DEFAULT_CONTENU.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingRapport() throws Exception {
        // Get the rapport
        restRapportMockMvc.perform(get("/api/rapports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRapport() throws Exception {
        // Initialize the database
        rapportRepository.save(rapport);

        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Update the rapport
        Rapport updatedRapport = rapportRepository.findById(rapport.getId()).get();
        updatedRapport
            //.objet(UPDATED_OBJET)
            //.copies(UPDATED_COPIES)
            .contenu(UPDATED_CONTENU)
            .type(UPDATED_TYPE)
            .position(UPDATED_POSITION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        RapportDTO rapportDTO = rapportMapper.toDto(updatedRapport);

        restRapportMockMvc.perform(put("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapportDTO)))
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        //assertThat(testRapport.getObjet()).isEqualTo(UPDATED_OBJET);
        assertThat(testRapport.getCopies()).isEqualTo(UPDATED_COPIES);
        assertThat(testRapport.getContenu()).isEqualTo(UPDATED_CONTENU);
        assertThat(testRapport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRapport.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testRapport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRapport.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRapport.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);

        // Validate the Rapport in Elasticsearch
        verify(mockRapportSearchRepository, times(1)).save(testRapport);
    }

    @Test
    public void updateNonExistingRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportMockMvc.perform(put("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Rapport in Elasticsearch
        verify(mockRapportSearchRepository, times(0)).save(rapport);
    }

    @Test
    public void deleteRapport() throws Exception {
        // Initialize the database
        rapportRepository.save(rapport);

        int databaseSizeBeforeDelete = rapportRepository.findAll().size();

        // Get the rapport
        restRapportMockMvc.perform(delete("/api/rapports/{id}", rapport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Rapport in Elasticsearch
        verify(mockRapportSearchRepository, times(1)).deleteById(rapport.getId());
    }

    @Test
    public void searchRapport() throws Exception {
        // Initialize the database
        rapportRepository.save(rapport);
        when(mockRapportSearchRepository.search(queryStringQuery("id:" + rapport.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rapport), PageRequest.of(0, 1), 1));
        // Search the rapport
        restRapportMockMvc.perform(get("/api/_search/rapports?query=id:" + rapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rapport.getId())))
            .andExpect(jsonPath("$.[*].objet").value(hasItem(DEFAULT_OBJET)))
            .andExpect(jsonPath("$.[*].copies").value(hasItem(DEFAULT_COPIES)))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rapport.class);
        Rapport rapport1 = new Rapport();
        rapport1.setId("id1");
        Rapport rapport2 = new Rapport();
        rapport2.setId(rapport1.getId());
        assertThat(rapport1).isEqualTo(rapport2);
        rapport2.setId("id2");
        assertThat(rapport1).isNotEqualTo(rapport2);
        rapport1.setId(null);
        assertThat(rapport1).isNotEqualTo(rapport2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RapportDTO.class);
        RapportDTO rapportDTO1 = new RapportDTO();
        rapportDTO1.setId("id1");
        RapportDTO rapportDTO2 = new RapportDTO();
        assertThat(rapportDTO1).isNotEqualTo(rapportDTO2);
        rapportDTO2.setId(rapportDTO1.getId());
        assertThat(rapportDTO1).isEqualTo(rapportDTO2);
        rapportDTO2.setId("id2");
        assertThat(rapportDTO1).isNotEqualTo(rapportDTO2);
        rapportDTO1.setId(null);
        assertThat(rapportDTO1).isNotEqualTo(rapportDTO2);
    }
}
