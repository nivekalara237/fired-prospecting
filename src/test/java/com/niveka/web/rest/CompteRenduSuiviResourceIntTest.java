package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.CompteRenduSuivi;
import com.niveka.repository.CompteRenduSuiviRepository;
import com.niveka.repository.search.CompteRenduSuiviSearchRepository;
import com.niveka.service.CompteRenduSuiviService;
import com.niveka.service.dto.CompteRenduSuiviDTO;
import com.niveka.service.mapper.CompteRenduSuiviMapper;
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
 * Test class for the CompteRenduSuiviResource REST controller.
 *
 * @see CompteRenduSuiviResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class CompteRenduSuiviResourceIntTest {

    private static final String DEFAULT_CONTENU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_PROCHAINE_RDV = "AAAAAAAAAA";
    private static final String UPDATED_DATE_PROCHAINE_RDV = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_AT = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_AT = "BBBBBBBBBB";

    @Autowired
    private CompteRenduSuiviRepository compteRenduSuiviRepository;

    @Autowired
    private CompteRenduSuiviMapper compteRenduSuiviMapper;

    @Autowired
    private CompteRenduSuiviService compteRenduSuiviService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.CompteRenduSuiviSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompteRenduSuiviSearchRepository mockCompteRenduSuiviSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCompteRenduSuiviMockMvc;

    private CompteRenduSuivi compteRenduSuivi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteRenduSuiviResource compteRenduSuiviResource = new CompteRenduSuiviResource(compteRenduSuiviService);
        this.restCompteRenduSuiviMockMvc = MockMvcBuilders.standaloneSetup(compteRenduSuiviResource)
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
    public static CompteRenduSuivi createEntity() {
        CompteRenduSuivi compteRenduSuivi = new CompteRenduSuivi()
            .contenu(DEFAULT_CONTENU)
            .dateProchaineRdv(DEFAULT_DATE_PROCHAINE_RDV)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return compteRenduSuivi;
    }

    @Before
    public void initTest() {
        compteRenduSuiviRepository.deleteAll();
        compteRenduSuivi = createEntity();
    }

    @Test
    public void createCompteRenduSuivi() throws Exception {
        int databaseSizeBeforeCreate = compteRenduSuiviRepository.findAll().size();

        // Create the CompteRenduSuivi
        CompteRenduSuiviDTO compteRenduSuiviDTO = compteRenduSuiviMapper.toDto(compteRenduSuivi);
        restCompteRenduSuiviMockMvc.perform(post("/api/compte-rendu-suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteRenduSuiviDTO)))
            .andExpect(status().isCreated());

        // Validate the CompteRenduSuivi in the database
        List<CompteRenduSuivi> compteRenduSuiviList = compteRenduSuiviRepository.findAll();
        assertThat(compteRenduSuiviList).hasSize(databaseSizeBeforeCreate + 1);
        CompteRenduSuivi testCompteRenduSuivi = compteRenduSuiviList.get(compteRenduSuiviList.size() - 1);
        assertThat(testCompteRenduSuivi.getContenu()).isEqualTo(DEFAULT_CONTENU);
        assertThat(testCompteRenduSuivi.getDateProchaineRdv()).isEqualTo(DEFAULT_DATE_PROCHAINE_RDV);
        assertThat(testCompteRenduSuivi.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCompteRenduSuivi.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testCompteRenduSuivi.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);

        // Validate the CompteRenduSuivi in Elasticsearch
        verify(mockCompteRenduSuiviSearchRepository, times(1)).save(testCompteRenduSuivi);
    }

    @Test
    public void createCompteRenduSuiviWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteRenduSuiviRepository.findAll().size();

        // Create the CompteRenduSuivi with an existing ID
        compteRenduSuivi.setId("existing_id");
        CompteRenduSuiviDTO compteRenduSuiviDTO = compteRenduSuiviMapper.toDto(compteRenduSuivi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteRenduSuiviMockMvc.perform(post("/api/compte-rendu-suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteRenduSuiviDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompteRenduSuivi in the database
        List<CompteRenduSuivi> compteRenduSuiviList = compteRenduSuiviRepository.findAll();
        assertThat(compteRenduSuiviList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompteRenduSuivi in Elasticsearch
        verify(mockCompteRenduSuiviSearchRepository, times(0)).save(compteRenduSuivi);
    }

    @Test
    public void getAllCompteRenduSuivis() throws Exception {
        // Initialize the database
        compteRenduSuiviRepository.save(compteRenduSuivi);

        // Get all the compteRenduSuiviList
        restCompteRenduSuiviMockMvc.perform(get("/api/compte-rendu-suivis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compteRenduSuivi.getId())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU.toString())))
            .andExpect(jsonPath("$.[*].dateProchaineRdv").value(hasItem(DEFAULT_DATE_PROCHAINE_RDV.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    public void getCompteRenduSuivi() throws Exception {
        // Initialize the database
        compteRenduSuiviRepository.save(compteRenduSuivi);

        // Get the compteRenduSuivi
        restCompteRenduSuiviMockMvc.perform(get("/api/compte-rendu-suivis/{id}", compteRenduSuivi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compteRenduSuivi.getId()))
            .andExpect(jsonPath("$.contenu").value(DEFAULT_CONTENU.toString()))
            .andExpect(jsonPath("$.dateProchaineRdv").value(DEFAULT_DATE_PROCHAINE_RDV.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingCompteRenduSuivi() throws Exception {
        // Get the compteRenduSuivi
        restCompteRenduSuiviMockMvc.perform(get("/api/compte-rendu-suivis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCompteRenduSuivi() throws Exception {
        // Initialize the database
        compteRenduSuiviRepository.save(compteRenduSuivi);

        int databaseSizeBeforeUpdate = compteRenduSuiviRepository.findAll().size();

        // Update the compteRenduSuivi
        CompteRenduSuivi updatedCompteRenduSuivi = compteRenduSuiviRepository.findById(compteRenduSuivi.getId()).get();
        updatedCompteRenduSuivi
            .contenu(UPDATED_CONTENU)
            .dateProchaineRdv(UPDATED_DATE_PROCHAINE_RDV)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        CompteRenduSuiviDTO compteRenduSuiviDTO = compteRenduSuiviMapper.toDto(updatedCompteRenduSuivi);

        restCompteRenduSuiviMockMvc.perform(put("/api/compte-rendu-suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteRenduSuiviDTO)))
            .andExpect(status().isOk());

        // Validate the CompteRenduSuivi in the database
        List<CompteRenduSuivi> compteRenduSuiviList = compteRenduSuiviRepository.findAll();
        assertThat(compteRenduSuiviList).hasSize(databaseSizeBeforeUpdate);
        CompteRenduSuivi testCompteRenduSuivi = compteRenduSuiviList.get(compteRenduSuiviList.size() - 1);
        assertThat(testCompteRenduSuivi.getContenu()).isEqualTo(UPDATED_CONTENU);
        assertThat(testCompteRenduSuivi.getDateProchaineRdv()).isEqualTo(UPDATED_DATE_PROCHAINE_RDV);
        assertThat(testCompteRenduSuivi.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCompteRenduSuivi.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCompteRenduSuivi.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);

        // Validate the CompteRenduSuivi in Elasticsearch
        verify(mockCompteRenduSuiviSearchRepository, times(1)).save(testCompteRenduSuivi);
    }

    @Test
    public void updateNonExistingCompteRenduSuivi() throws Exception {
        int databaseSizeBeforeUpdate = compteRenduSuiviRepository.findAll().size();

        // Create the CompteRenduSuivi
        CompteRenduSuiviDTO compteRenduSuiviDTO = compteRenduSuiviMapper.toDto(compteRenduSuivi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteRenduSuiviMockMvc.perform(put("/api/compte-rendu-suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteRenduSuiviDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompteRenduSuivi in the database
        List<CompteRenduSuivi> compteRenduSuiviList = compteRenduSuiviRepository.findAll();
        assertThat(compteRenduSuiviList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompteRenduSuivi in Elasticsearch
        verify(mockCompteRenduSuiviSearchRepository, times(0)).save(compteRenduSuivi);
    }

    @Test
    public void deleteCompteRenduSuivi() throws Exception {
        // Initialize the database
        compteRenduSuiviRepository.save(compteRenduSuivi);

        int databaseSizeBeforeDelete = compteRenduSuiviRepository.findAll().size();

        // Get the compteRenduSuivi
        restCompteRenduSuiviMockMvc.perform(delete("/api/compte-rendu-suivis/{id}", compteRenduSuivi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompteRenduSuivi> compteRenduSuiviList = compteRenduSuiviRepository.findAll();
        assertThat(compteRenduSuiviList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompteRenduSuivi in Elasticsearch
        verify(mockCompteRenduSuiviSearchRepository, times(1)).deleteById(compteRenduSuivi.getId());
    }

    @Test
    public void searchCompteRenduSuivi() throws Exception {
        // Initialize the database
        compteRenduSuiviRepository.save(compteRenduSuivi);
        when(mockCompteRenduSuiviSearchRepository.search(queryStringQuery("id:" + compteRenduSuivi.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(compteRenduSuivi), PageRequest.of(0, 1), 1));
        // Search the compteRenduSuivi
        restCompteRenduSuiviMockMvc.perform(get("/api/_search/compte-rendu-suivis?query=id:" + compteRenduSuivi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compteRenduSuivi.getId())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU)))
            .andExpect(jsonPath("$.[*].dateProchaineRdv").value(hasItem(DEFAULT_DATE_PROCHAINE_RDV)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteRenduSuivi.class);
        CompteRenduSuivi compteRenduSuivi1 = new CompteRenduSuivi();
        compteRenduSuivi1.setId("id1");
        CompteRenduSuivi compteRenduSuivi2 = new CompteRenduSuivi();
        compteRenduSuivi2.setId(compteRenduSuivi1.getId());
        assertThat(compteRenduSuivi1).isEqualTo(compteRenduSuivi2);
        compteRenduSuivi2.setId("id2");
        assertThat(compteRenduSuivi1).isNotEqualTo(compteRenduSuivi2);
        compteRenduSuivi1.setId(null);
        assertThat(compteRenduSuivi1).isNotEqualTo(compteRenduSuivi2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteRenduSuiviDTO.class);
        CompteRenduSuiviDTO compteRenduSuiviDTO1 = new CompteRenduSuiviDTO();
        compteRenduSuiviDTO1.setId("id1");
        CompteRenduSuiviDTO compteRenduSuiviDTO2 = new CompteRenduSuiviDTO();
        assertThat(compteRenduSuiviDTO1).isNotEqualTo(compteRenduSuiviDTO2);
        compteRenduSuiviDTO2.setId(compteRenduSuiviDTO1.getId());
        assertThat(compteRenduSuiviDTO1).isEqualTo(compteRenduSuiviDTO2);
        compteRenduSuiviDTO2.setId("id2");
        assertThat(compteRenduSuiviDTO1).isNotEqualTo(compteRenduSuiviDTO2);
        compteRenduSuiviDTO1.setId(null);
        assertThat(compteRenduSuiviDTO1).isNotEqualTo(compteRenduSuiviDTO2);
    }
}
