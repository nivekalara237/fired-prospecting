package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.PieceJointe;
import com.niveka.repository.PieceJointeRepository;
import com.niveka.repository.search.PieceJointeSearchRepository;
import com.niveka.service.PieceJointeService;
import com.niveka.service.dto.PieceJointeDTO;
import com.niveka.service.mapper.PieceJointeMapper;
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
 * Test class for the PieceJointeResource REST controller.
 *
 * @see PieceJointeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class PieceJointeResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    private static final String DEFAULT_ENCODE = "AAAAAAAAAA";
    private static final String UPDATED_ENCODE = "BBBBBBBBBB";

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    @Autowired
    private PieceJointeMapper pieceJointeMapper;

    @Autowired
    private PieceJointeService pieceJointeService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.ObjetSearchRepositoryMockConfiguration
     */
    @Autowired
    private PieceJointeSearchRepository mockPieceJointeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restObjetMockMvc;

    private PieceJointe pieceJointe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PieceJointeResource objetResource = new PieceJointeResource(pieceJointeService);
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
    public static PieceJointe createEntity() {
        PieceJointe pieceJointe = new PieceJointe()
            .nom(DEFAULT_NOM)
            .lien(DEFAULT_LIEN)
            .encode(DEFAULT_ENCODE);
        return pieceJointe;
    }

    @Before
    public void initTest() {
        pieceJointeRepository.deleteAll();
        pieceJointe = createEntity();
    }

    @Test
    public void createObjet() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);
        restObjetMockMvc.perform(post("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate + 1);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPieceJointe.getLien()).isEqualTo(DEFAULT_LIEN);
        assertThat(testPieceJointe.getEncode()).isEqualTo(DEFAULT_ENCODE);

        // Validate the PieceJointe in Elasticsearch
        verify(mockPieceJointeSearchRepository, times(1)).save(testPieceJointe);
    }

    @Test
    public void createObjetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe with an existing ID
        pieceJointe.setId("existing_id");
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjetMockMvc.perform(post("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PieceJointe in Elasticsearch
        verify(mockPieceJointeSearchRepository, times(0)).save(pieceJointe);
    }

    @Test
    public void getAllObjets() throws Exception {
        // Initialize the database
        pieceJointeRepository.save(pieceJointe);

        // Get all the objetList
        restObjetMockMvc.perform(get("/api/objets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())))
            .andExpect(jsonPath("$.[*].encode").value(hasItem(DEFAULT_ENCODE.toString())));
    }

    @Test
    public void getObjet() throws Exception {
        // Initialize the database
        pieceJointeRepository.save(pieceJointe);

        // Get the pieceJointe
        restObjetMockMvc.perform(get("/api/objets/{id}", pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pieceJointe.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN.toString()))
            .andExpect(jsonPath("$.encode").value(DEFAULT_ENCODE.toString()));
    }

    @Test
    public void getNonExistingObjet() throws Exception {
        // Get the pieceJointe
        restObjetMockMvc.perform(get("/api/objets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateObjet() throws Exception {
        // Initialize the database
        pieceJointeRepository.save(pieceJointe);

        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Update the pieceJointe
        PieceJointe updatedPieceJointe = pieceJointeRepository.findById(pieceJointe.getId()).get();
        updatedPieceJointe
            .nom(UPDATED_NOM)
            .lien(UPDATED_LIEN)
            .encode(UPDATED_ENCODE);
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(updatedPieceJointe);

        restObjetMockMvc.perform(put("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isOk());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPieceJointe.getLien()).isEqualTo(UPDATED_LIEN);
        assertThat(testPieceJointe.getEncode()).isEqualTo(UPDATED_ENCODE);

        // Validate the PieceJointe in Elasticsearch
        verify(mockPieceJointeSearchRepository, times(1)).save(testPieceJointe);
    }

    @Test
    public void updateNonExistingObjet() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjetMockMvc.perform(put("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PieceJointe in Elasticsearch
        verify(mockPieceJointeSearchRepository, times(0)).save(pieceJointe);
    }

    @Test
    public void deleteObjet() throws Exception {
        // Initialize the database
        pieceJointeRepository.save(pieceJointe);

        int databaseSizeBeforeDelete = pieceJointeRepository.findAll().size();

        // Get the pieceJointe
        restObjetMockMvc.perform(delete("/api/objets/{id}", pieceJointe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PieceJointe in Elasticsearch
        verify(mockPieceJointeSearchRepository, times(1)).deleteById(pieceJointe.getId());
    }

    @Test
    public void searchObjet() throws Exception {
        // Initialize the database
        pieceJointeRepository.save(pieceJointe);
        when(mockPieceJointeSearchRepository.search(queryStringQuery("id:" + pieceJointe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pieceJointe), PageRequest.of(0, 1), 1));
        // Search the pieceJointe
        restObjetMockMvc.perform(get("/api/_search/objets?query=id:" + pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN)))
            .andExpect(jsonPath("$.[*].encode").value(hasItem(DEFAULT_ENCODE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointe.class);
        PieceJointe pieceJointe1 = new PieceJointe();
        pieceJointe1.setId("id1");
        PieceJointe pieceJointe2 = new PieceJointe();
        pieceJointe2.setId(pieceJointe1.getId());
        assertThat(pieceJointe1).isEqualTo(pieceJointe2);
        pieceJointe2.setId("id2");
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
        pieceJointe1.setId(null);
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointeDTO.class);
        PieceJointeDTO pieceJointeDTO1 = new PieceJointeDTO();
        pieceJointeDTO1.setId("id1");
        PieceJointeDTO pieceJointeDTO2 = new PieceJointeDTO();
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
        pieceJointeDTO2.setId(pieceJointeDTO1.getId());
        assertThat(pieceJointeDTO1).isEqualTo(pieceJointeDTO2);
        pieceJointeDTO2.setId("id2");
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
        pieceJointeDTO1.setId(null);
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
    }
}
