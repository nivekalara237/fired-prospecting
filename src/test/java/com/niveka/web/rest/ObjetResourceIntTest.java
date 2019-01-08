package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Objet;
import com.niveka.repository.ObjetRepository;
import com.niveka.repository.search.ObjetSearchRepository;
import com.niveka.service.ObjetService;
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
 * Test class for the ObjetResource REST controller.
 *
 * @see ObjetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class ObjetResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    private static final String DEFAULT_ENCODE = "AAAAAAAAAA";
    private static final String UPDATED_ENCODE = "BBBBBBBBBB";

    @Autowired
    private ObjetRepository objetRepository;

    @Autowired
    private ObjetService objetService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.ObjetSearchRepositoryMockConfiguration
     */
    @Autowired
    private ObjetSearchRepository mockObjetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restObjetMockMvc;

    private Objet objet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObjetResource objetResource = new ObjetResource(objetService);
        this.restObjetMockMvc = MockMvcBuilders.standaloneSetup(objetResource)
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
    public static Objet createEntity() {
        Objet objet = new Objet()
            .nom(DEFAULT_NOM)
            .lien(DEFAULT_LIEN)
            .encode(DEFAULT_ENCODE);
        return objet;
    }

    @Before
    public void initTest() {
        objetRepository.deleteAll();
        objet = createEntity();
    }

    @Test
    public void createObjet() throws Exception {
        int databaseSizeBeforeCreate = objetRepository.findAll().size();

        // Create the Objet
        restObjetMockMvc.perform(post("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objet)))
            .andExpect(status().isCreated());

        // Validate the Objet in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeCreate + 1);
        Objet testObjet = objetList.get(objetList.size() - 1);
        assertThat(testObjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testObjet.getLien()).isEqualTo(DEFAULT_LIEN);
        assertThat(testObjet.getEncode()).isEqualTo(DEFAULT_ENCODE);

        // Validate the Objet in Elasticsearch
        verify(mockObjetSearchRepository, times(1)).save(testObjet);
    }

    @Test
    public void createObjetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = objetRepository.findAll().size();

        // Create the Objet with an existing ID
        objet.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjetMockMvc.perform(post("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objet)))
            .andExpect(status().isBadRequest());

        // Validate the Objet in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeCreate);

        // Validate the Objet in Elasticsearch
        verify(mockObjetSearchRepository, times(0)).save(objet);
    }

    @Test
    public void getAllObjets() throws Exception {
        // Initialize the database
        objetRepository.save(objet);

        // Get all the objetList
        restObjetMockMvc.perform(get("/api/objets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objet.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())))
            .andExpect(jsonPath("$.[*].encode").value(hasItem(DEFAULT_ENCODE.toString())));
    }
    
    @Test
    public void getObjet() throws Exception {
        // Initialize the database
        objetRepository.save(objet);

        // Get the objet
        restObjetMockMvc.perform(get("/api/objets/{id}", objet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(objet.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN.toString()))
            .andExpect(jsonPath("$.encode").value(DEFAULT_ENCODE.toString()));
    }

    @Test
    public void getNonExistingObjet() throws Exception {
        // Get the objet
        restObjetMockMvc.perform(get("/api/objets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateObjet() throws Exception {
        // Initialize the database
        objetService.save(objet);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockObjetSearchRepository);

        int databaseSizeBeforeUpdate = objetRepository.findAll().size();

        // Update the objet
        Objet updatedObjet = objetRepository.findById(objet.getId()).get();
        updatedObjet
            .nom(UPDATED_NOM)
            .lien(UPDATED_LIEN)
            .encode(UPDATED_ENCODE);

        restObjetMockMvc.perform(put("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedObjet)))
            .andExpect(status().isOk());

        // Validate the Objet in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeUpdate);
        Objet testObjet = objetList.get(objetList.size() - 1);
        assertThat(testObjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testObjet.getLien()).isEqualTo(UPDATED_LIEN);
        assertThat(testObjet.getEncode()).isEqualTo(UPDATED_ENCODE);

        // Validate the Objet in Elasticsearch
        verify(mockObjetSearchRepository, times(1)).save(testObjet);
    }

    @Test
    public void updateNonExistingObjet() throws Exception {
        int databaseSizeBeforeUpdate = objetRepository.findAll().size();

        // Create the Objet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjetMockMvc.perform(put("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objet)))
            .andExpect(status().isBadRequest());

        // Validate the Objet in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Objet in Elasticsearch
        verify(mockObjetSearchRepository, times(0)).save(objet);
    }

    @Test
    public void deleteObjet() throws Exception {
        // Initialize the database
        objetService.save(objet);

        int databaseSizeBeforeDelete = objetRepository.findAll().size();

        // Get the objet
        restObjetMockMvc.perform(delete("/api/objets/{id}", objet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Objet in Elasticsearch
        verify(mockObjetSearchRepository, times(1)).deleteById(objet.getId());
    }

    @Test
    public void searchObjet() throws Exception {
        // Initialize the database
        objetService.save(objet);
        when(mockObjetSearchRepository.search(queryStringQuery("id:" + objet.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(objet), PageRequest.of(0, 1), 1));
        // Search the objet
        restObjetMockMvc.perform(get("/api/_search/objets?query=id:" + objet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objet.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN)))
            .andExpect(jsonPath("$.[*].encode").value(hasItem(DEFAULT_ENCODE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Objet.class);
        Objet objet1 = new Objet();
        objet1.setId("id1");
        Objet objet2 = new Objet();
        objet2.setId(objet1.getId());
        assertThat(objet1).isEqualTo(objet2);
        objet2.setId("id2");
        assertThat(objet1).isNotEqualTo(objet2);
        objet1.setId(null);
        assertThat(objet1).isNotEqualTo(objet2);
    }
}
