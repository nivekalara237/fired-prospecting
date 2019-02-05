package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Suivi;
import com.niveka.repository.SuiviRepository;
import com.niveka.repository.search.SuiviSearchRepository;
import com.niveka.service.SuiviService;
import com.niveka.service.dto.SuiviDTO;
import com.niveka.service.mapper.SuiviMapper;
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
 * Test class for the SuiviResource REST controller.
 *
 * @see SuiviResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class SuiviResourceIntTest {

    private static final String DEFAULT_DATE_RDV = "AAAAAAAAAA";
    private static final String UPDATED_DATE_RDV = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_AT = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_AT = "BBBBBBBBBB";

    @Autowired
    private SuiviRepository suiviRepository;

    @Autowired
    private SuiviMapper suiviMapper;

    @Autowired
    private SuiviService suiviService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.SuiviSearchRepositoryMockConfiguration
     */
    @Autowired
    private SuiviSearchRepository mockSuiviSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSuiviMockMvc;

    private Suivi suivi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuiviResource suiviResource = new SuiviResource(suiviService);
        this.restSuiviMockMvc = MockMvcBuilders.standaloneSetup(suiviResource)
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
    public static Suivi createEntity() {
        Suivi suivi = new Suivi()
            .dateRdv(DEFAULT_DATE_RDV)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return suivi;
    }

    @Before
    public void initTest() {
        suiviRepository.deleteAll();
        suivi = createEntity();
    }

    @Test
    public void createSuivi() throws Exception {
        int databaseSizeBeforeCreate = suiviRepository.findAll().size();

        // Create the Suivi
        SuiviDTO suiviDTO = suiviMapper.toDto(suivi);
        restSuiviMockMvc.perform(post("/api/suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suiviDTO)))
            .andExpect(status().isCreated());

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll();
        assertThat(suiviList).hasSize(databaseSizeBeforeCreate + 1);
        Suivi testSuivi = suiviList.get(suiviList.size() - 1);
        assertThat(testSuivi.getDateRdv()).isEqualTo(DEFAULT_DATE_RDV);
        assertThat(testSuivi.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSuivi.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testSuivi.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);

        // Validate the Suivi in Elasticsearch
        verify(mockSuiviSearchRepository, times(1)).save(testSuivi);
    }

    @Test
    public void createSuiviWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suiviRepository.findAll().size();

        // Create the Suivi with an existing ID
        suivi.setId("existing_id");
        SuiviDTO suiviDTO = suiviMapper.toDto(suivi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuiviMockMvc.perform(post("/api/suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suiviDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll();
        assertThat(suiviList).hasSize(databaseSizeBeforeCreate);

        // Validate the Suivi in Elasticsearch
        verify(mockSuiviSearchRepository, times(0)).save(suivi);
    }

    @Test
    public void getAllSuivis() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi);

        // Get all the suiviList
        restSuiviMockMvc.perform(get("/api/suivis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suivi.getId())))
            .andExpect(jsonPath("$.[*].dateRdv").value(hasItem(DEFAULT_DATE_RDV.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    public void getSuivi() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi);

        // Get the suivi
        restSuiviMockMvc.perform(get("/api/suivis/{id}", suivi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suivi.getId()))
            .andExpect(jsonPath("$.dateRdv").value(DEFAULT_DATE_RDV.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingSuivi() throws Exception {
        // Get the suivi
        restSuiviMockMvc.perform(get("/api/suivis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSuivi() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi);

        int databaseSizeBeforeUpdate = suiviRepository.findAll().size();

        // Update the suivi
        Suivi updatedSuivi = suiviRepository.findById(suivi.getId()).get();
        updatedSuivi
            .dateRdv(UPDATED_DATE_RDV)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        SuiviDTO suiviDTO = suiviMapper.toDto(updatedSuivi);

        restSuiviMockMvc.perform(put("/api/suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suiviDTO)))
            .andExpect(status().isOk());

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);
        Suivi testSuivi = suiviList.get(suiviList.size() - 1);
        assertThat(testSuivi.getDateRdv()).isEqualTo(UPDATED_DATE_RDV);
        assertThat(testSuivi.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSuivi.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testSuivi.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);

        // Validate the Suivi in Elasticsearch
        verify(mockSuiviSearchRepository, times(1)).save(testSuivi);
    }

    @Test
    public void updateNonExistingSuivi() throws Exception {
        int databaseSizeBeforeUpdate = suiviRepository.findAll().size();

        // Create the Suivi
        SuiviDTO suiviDTO = suiviMapper.toDto(suivi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuiviMockMvc.perform(put("/api/suivis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suiviDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suivi in the database
        List<Suivi> suiviList = suiviRepository.findAll();
        assertThat(suiviList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Suivi in Elasticsearch
        verify(mockSuiviSearchRepository, times(0)).save(suivi);
    }

    @Test
    public void deleteSuivi() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi);

        int databaseSizeBeforeDelete = suiviRepository.findAll().size();

        // Get the suivi
        restSuiviMockMvc.perform(delete("/api/suivis/{id}", suivi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Suivi> suiviList = suiviRepository.findAll();
        assertThat(suiviList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Suivi in Elasticsearch
        verify(mockSuiviSearchRepository, times(1)).deleteById(suivi.getId());
    }

    @Test
    public void searchSuivi() throws Exception {
        // Initialize the database
        suiviRepository.save(suivi);
        when(mockSuiviSearchRepository.search(queryStringQuery("id:" + suivi.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(suivi), PageRequest.of(0, 1), 1));
        // Search the suivi
        restSuiviMockMvc.perform(get("/api/_search/suivis?query=id:" + suivi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suivi.getId())))
            .andExpect(jsonPath("$.[*].dateRdv").value(hasItem(DEFAULT_DATE_RDV)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suivi.class);
        Suivi suivi1 = new Suivi();
        suivi1.setId("id1");
        Suivi suivi2 = new Suivi();
        suivi2.setId(suivi1.getId());
        assertThat(suivi1).isEqualTo(suivi2);
        suivi2.setId("id2");
        assertThat(suivi1).isNotEqualTo(suivi2);
        suivi1.setId(null);
        assertThat(suivi1).isNotEqualTo(suivi2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuiviDTO.class);
        SuiviDTO suiviDTO1 = new SuiviDTO();
        suiviDTO1.setId("id1");
        SuiviDTO suiviDTO2 = new SuiviDTO();
        assertThat(suiviDTO1).isNotEqualTo(suiviDTO2);
        suiviDTO2.setId(suiviDTO1.getId());
        assertThat(suiviDTO1).isEqualTo(suiviDTO2);
        suiviDTO2.setId("id2");
        assertThat(suiviDTO1).isNotEqualTo(suiviDTO2);
        suiviDTO1.setId(null);
        assertThat(suiviDTO1).isNotEqualTo(suiviDTO2);
    }
}
