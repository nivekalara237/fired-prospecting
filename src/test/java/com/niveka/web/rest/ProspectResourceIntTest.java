package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Prospect;
import com.niveka.repository.ProspectRepository;
import com.niveka.repository.search.ProspectSearchRepository;
import com.niveka.service.ProspectService;
import com.niveka.service.dto.ProspectDTO;
import com.niveka.service.mapper.ProspectMapper;
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
 * Test class for the ProspectResource REST controller.
 *
 * @see ProspectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class ProspectResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_DATE_RDV = "AAAAAAAAAA";
    private static final String UPDATED_DATE_RDV = "BBBBBBBBBB";

    private static final String DEFAULT_COMPTE_RENDU = "AAAAAAAAAA";
    private static final String UPDATED_COMPTE_RENDU = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALISATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_AT = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_AT = "BBBBBBBBBB";

    @Autowired
    private ProspectRepository prospectRepository;

    @Autowired
    private ProspectMapper prospectMapper;

    @Autowired
    private ProspectService prospectService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.ProspectSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProspectSearchRepository mockProspectSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProspectMockMvc;

    private Prospect prospect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProspectResource prospectResource = new ProspectResource(prospectService);
        this.restProspectMockMvc = MockMvcBuilders.standaloneSetup(prospectResource)
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
    public static Prospect createEntity() {
        Prospect prospect = new Prospect()
            .nom(DEFAULT_NOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .type(DEFAULT_TYPE)
            .dateRdv(DEFAULT_DATE_RDV)
            .compteRendu(DEFAULT_COMPTE_RENDU)
            .localisation(DEFAULT_LOCALISATION)
            .position(DEFAULT_POSITION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return prospect;
    }

    @Before
    public void initTest() {
        prospectRepository.deleteAll();
        prospect = createEntity();
    }

    @Test
    public void createProspect() throws Exception {
        int databaseSizeBeforeCreate = prospectRepository.findAll().size();

        // Create the Prospect
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);
        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isCreated());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeCreate + 1);
        Prospect testProspect = prospectList.get(prospectList.size() - 1);
        assertThat(testProspect.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProspect.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProspect.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testProspect.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProspect.getDateRdv()).isEqualTo(DEFAULT_DATE_RDV);
        assertThat(testProspect.getCompteRendu()).isEqualTo(DEFAULT_COMPTE_RENDU);
        assertThat(testProspect.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testProspect.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testProspect.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProspect.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testProspect.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(1)).save(testProspect);
    }

    @Test
    public void createProspectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prospectRepository.findAll().size();

        // Create the Prospect with an existing ID
        prospect.setId("existing_id");
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProspectMockMvc.perform(post("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeCreate);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(0)).save(prospect);
    }

    @Test
    public void getAllProspects() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        // Get all the prospectList
        restProspectMockMvc.perform(get("/api/prospects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospect.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateRdv").value(hasItem(DEFAULT_DATE_RDV.toString())))
            .andExpect(jsonPath("$.[*].compteRendu").value(hasItem(DEFAULT_COMPTE_RENDU.toString())))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    public void getProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        // Get the prospect
        restProspectMockMvc.perform(get("/api/prospects/{id}", prospect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prospect.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.dateRdv").value(DEFAULT_DATE_RDV.toString()))
            .andExpect(jsonPath("$.compteRendu").value(DEFAULT_COMPTE_RENDU.toString()))
            .andExpect(jsonPath("$.localisation").value(DEFAULT_LOCALISATION.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingProspect() throws Exception {
        // Get the prospect
        restProspectMockMvc.perform(get("/api/prospects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        int databaseSizeBeforeUpdate = prospectRepository.findAll().size();

        // Update the prospect
        Prospect updatedProspect = prospectRepository.findById(prospect.getId()).get();
        updatedProspect
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .type(UPDATED_TYPE)
            .dateRdv(UPDATED_DATE_RDV)
            .compteRendu(UPDATED_COMPTE_RENDU)
            .localisation(UPDATED_LOCALISATION)
            .position(UPDATED_POSITION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        ProspectDTO prospectDTO = prospectMapper.toDto(updatedProspect);

        restProspectMockMvc.perform(put("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isOk());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeUpdate);
        Prospect testProspect = prospectList.get(prospectList.size() - 1);
        assertThat(testProspect.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProspect.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProspect.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testProspect.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProspect.getDateRdv()).isEqualTo(UPDATED_DATE_RDV);
        assertThat(testProspect.getCompteRendu()).isEqualTo(UPDATED_COMPTE_RENDU);
        assertThat(testProspect.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testProspect.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testProspect.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProspect.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testProspect.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(1)).save(testProspect);
    }

    @Test
    public void updateNonExistingProspect() throws Exception {
        int databaseSizeBeforeUpdate = prospectRepository.findAll().size();

        // Create the Prospect
        ProspectDTO prospectDTO = prospectMapper.toDto(prospect);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProspectMockMvc.perform(put("/api/prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(0)).save(prospect);
    }

    @Test
    public void deleteProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);

        int databaseSizeBeforeDelete = prospectRepository.findAll().size();

        // Get the prospect
        restProspectMockMvc.perform(delete("/api/prospects/{id}", prospect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prospect> prospectList = prospectRepository.findAll();
        assertThat(prospectList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Prospect in Elasticsearch
        verify(mockProspectSearchRepository, times(1)).deleteById(prospect.getId());
    }

    @Test
    public void searchProspect() throws Exception {
        // Initialize the database
        prospectRepository.save(prospect);
        when(mockProspectSearchRepository.search(queryStringQuery("id:" + prospect.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prospect), PageRequest.of(0, 1), 1));
        // Search the prospect
        restProspectMockMvc.perform(get("/api/_search/prospects?query=id:" + prospect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospect.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateRdv").value(hasItem(DEFAULT_DATE_RDV)))
            .andExpect(jsonPath("$.[*].compteRendu").value(hasItem(DEFAULT_COMPTE_RENDU)))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prospect.class);
        Prospect prospect1 = new Prospect();
        prospect1.setId("id1");
        Prospect prospect2 = new Prospect();
        prospect2.setId(prospect1.getId());
        assertThat(prospect1).isEqualTo(prospect2);
        prospect2.setId("id2");
        assertThat(prospect1).isNotEqualTo(prospect2);
        prospect1.setId(null);
        assertThat(prospect1).isNotEqualTo(prospect2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProspectDTO.class);
        ProspectDTO prospectDTO1 = new ProspectDTO();
        prospectDTO1.setId("id1");
        ProspectDTO prospectDTO2 = new ProspectDTO();
        assertThat(prospectDTO1).isNotEqualTo(prospectDTO2);
        prospectDTO2.setId(prospectDTO1.getId());
        assertThat(prospectDTO1).isEqualTo(prospectDTO2);
        prospectDTO2.setId("id2");
        assertThat(prospectDTO1).isNotEqualTo(prospectDTO2);
        prospectDTO1.setId(null);
        assertThat(prospectDTO1).isNotEqualTo(prospectDTO2);
    }
}
