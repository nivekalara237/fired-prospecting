package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Copie;
import com.niveka.repository.CopieRepository;
import com.niveka.repository.search.CopieSearchRepository;
import com.niveka.service.CopieService;
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
 * Test class for the CopieResource REST controller.
 *
 * @see CopieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class CopieResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    @Autowired
    private CopieRepository copieRepository;

    @Autowired
    private CopieService copieService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.CopieSearchRepositoryMockConfiguration
     */
    @Autowired
    private CopieSearchRepository mockCopieSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCopieMockMvc;

    private Copie copie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CopieResource copieResource = new CopieResource(copieService);
        this.restCopieMockMvc = MockMvcBuilders.standaloneSetup(copieResource)
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
    public static Copie createEntity() {
        Copie copie = new Copie()
            .email(DEFAULT_EMAIL)
            .createdAt(DEFAULT_CREATED_AT);
        return copie;
    }

    @Before
    public void initTest() {
        copieRepository.deleteAll();
        copie = createEntity();
    }

    @Test
    public void createCopie() throws Exception {
        int databaseSizeBeforeCreate = copieRepository.findAll().size();

        // Create the Copie
        restCopieMockMvc.perform(post("/api/copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(copie)))
            .andExpect(status().isCreated());

        // Validate the Copie in the database
        List<Copie> copieList = copieRepository.findAll();
        assertThat(copieList).hasSize(databaseSizeBeforeCreate + 1);
        Copie testCopie = copieList.get(copieList.size() - 1);
        assertThat(testCopie.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCopie.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);

        // Validate the Copie in Elasticsearch
        verify(mockCopieSearchRepository, times(1)).save(testCopie);
    }

    @Test
    public void createCopieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = copieRepository.findAll().size();

        // Create the Copie with an existing ID
        copie.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCopieMockMvc.perform(post("/api/copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(copie)))
            .andExpect(status().isBadRequest());

        // Validate the Copie in the database
        List<Copie> copieList = copieRepository.findAll();
        assertThat(copieList).hasSize(databaseSizeBeforeCreate);

        // Validate the Copie in Elasticsearch
        verify(mockCopieSearchRepository, times(0)).save(copie);
    }

    @Test
    public void getAllCopies() throws Exception {
        // Initialize the database
        copieRepository.save(copie);

        // Get all the copieList
        restCopieMockMvc.perform(get("/api/copies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(copie.getId())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    public void getCopie() throws Exception {
        // Initialize the database
        copieRepository.save(copie);

        // Get the copie
        restCopieMockMvc.perform(get("/api/copies/{id}", copie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(copie.getId()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    public void getNonExistingCopie() throws Exception {
        // Get the copie
        restCopieMockMvc.perform(get("/api/copies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCopie() throws Exception {
        // Initialize the database
        copieService.save(copie);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCopieSearchRepository);

        int databaseSizeBeforeUpdate = copieRepository.findAll().size();

        // Update the copie
        Copie updatedCopie = copieRepository.findById(copie.getId()).get();
        updatedCopie
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT);

        restCopieMockMvc.perform(put("/api/copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCopie)))
            .andExpect(status().isOk());

        // Validate the Copie in the database
        List<Copie> copieList = copieRepository.findAll();
        assertThat(copieList).hasSize(databaseSizeBeforeUpdate);
        Copie testCopie = copieList.get(copieList.size() - 1);
        assertThat(testCopie.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCopie.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);

        // Validate the Copie in Elasticsearch
        verify(mockCopieSearchRepository, times(1)).save(testCopie);
    }

    @Test
    public void updateNonExistingCopie() throws Exception {
        int databaseSizeBeforeUpdate = copieRepository.findAll().size();

        // Create the Copie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCopieMockMvc.perform(put("/api/copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(copie)))
            .andExpect(status().isBadRequest());

        // Validate the Copie in the database
        List<Copie> copieList = copieRepository.findAll();
        assertThat(copieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Copie in Elasticsearch
        verify(mockCopieSearchRepository, times(0)).save(copie);
    }

    @Test
    public void deleteCopie() throws Exception {
        // Initialize the database
        copieService.save(copie);

        int databaseSizeBeforeDelete = copieRepository.findAll().size();

        // Get the copie
        restCopieMockMvc.perform(delete("/api/copies/{id}", copie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Copie> copieList = copieRepository.findAll();
        assertThat(copieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Copie in Elasticsearch
        verify(mockCopieSearchRepository, times(1)).deleteById(copie.getId());
    }

    @Test
    public void searchCopie() throws Exception {
        // Initialize the database
        copieService.save(copie);
        when(mockCopieSearchRepository.search(queryStringQuery("id:" + copie.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(copie), PageRequest.of(0, 1), 1));
        // Search the copie
        restCopieMockMvc.perform(get("/api/_search/copies?query=id:" + copie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(copie.getId())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Copie.class);
        Copie copie1 = new Copie();
        copie1.setId("id1");
        Copie copie2 = new Copie();
        copie2.setId(copie1.getId());
        assertThat(copie1).isEqualTo(copie2);
        copie2.setId("id2");
        assertThat(copie1).isNotEqualTo(copie2);
        copie1.setId(null);
        assertThat(copie1).isNotEqualTo(copie2);
    }
}
