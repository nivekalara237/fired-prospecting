package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Channel;
import com.niveka.repository.ChannelRepository;
import com.niveka.repository.search.ChannelSearchRepository;
import com.niveka.service.ChannelService;
import com.niveka.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import java.util.ArrayList;
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
 * Test class for the ChannelResource REST controller.
 *
 * @see ChannelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class ChannelResourceIntTest {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_ENTREPRISE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ENTREPRISE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_AT = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_AT = "BBBBBBBBBB";

    @Autowired
    private ChannelRepository channelRepository;

    @Mock
    private ChannelRepository channelRepositoryMock;

    @Mock
    private ChannelService channelServiceMock;

    @Autowired
    private ChannelService channelService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.ChannelSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChannelSearchRepository mockChannelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restChannelMockMvc;

    private Channel channel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChannelResource channelResource = new ChannelResource(channelService);
        this.restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
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
    public static Channel createEntity() {
        Channel channel = new Channel()
            .designation(DEFAULT_DESIGNATION)
            .entrepriseId(DEFAULT_ENTREPRISE_ID)
            .code(DEFAULT_CODE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return channel;
    }

    @Before
    public void initTest() {
        channelRepository.deleteAll();
        channel = createEntity();
    }

    @Test
    public void createChannel() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channel)))
            .andExpect(status().isCreated());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate + 1);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testChannel.getEntrepriseId()).isEqualTo(DEFAULT_ENTREPRISE_ID);
        assertThat(testChannel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testChannel.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testChannel.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testChannel.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(1)).save(testChannel);
    }

    @Test
    public void createChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel with an existing ID
        channel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channel)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(0)).save(channel);
    }

    @Test
    public void getAllChannels() throws Exception {
        // Initialize the database
        channelRepository.save(channel);

        // Get all the channelList
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].entrepriseId").value(hasItem(DEFAULT_ENTREPRISE_ID.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllChannelsWithEagerRelationshipsIsEnabled() throws Exception {
        ChannelResource channelResource = new ChannelResource(channelServiceMock);
        when(channelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChannelMockMvc.perform(get("/api/channels?eagerload=true"))
        .andExpect(status().isOk());

        verify(channelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllChannelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ChannelResource channelResource = new ChannelResource(channelServiceMock);
            when(channelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChannelMockMvc.perform(get("/api/channels?eagerload=true"))
        .andExpect(status().isOk());

            verify(channelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getChannel() throws Exception {
        // Initialize the database
        channelRepository.save(channel);

        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", channel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(channel.getId()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.entrepriseId").value(DEFAULT_ENTREPRISE_ID.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingChannel() throws Exception {
        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateChannel() throws Exception {
        // Initialize the database
        channelService.save(channel);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockChannelSearchRepository);

        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Update the channel
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        updatedChannel
            .designation(UPDATED_DESIGNATION)
            .entrepriseId(UPDATED_ENTREPRISE_ID)
            .code(UPDATED_CODE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChannel)))
            .andExpect(status().isOk());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testChannel.getEntrepriseId()).isEqualTo(UPDATED_ENTREPRISE_ID);
        assertThat(testChannel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testChannel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testChannel.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testChannel.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(1)).save(testChannel);
    }

    @Test
    public void updateNonExistingChannel() throws Exception {
        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Create the Channel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channel)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(0)).save(channel);
    }

    @Test
    public void deleteChannel() throws Exception {
        // Initialize the database
        channelService.save(channel);

        int databaseSizeBeforeDelete = channelRepository.findAll().size();

        // Get the channel
        restChannelMockMvc.perform(delete("/api/channels/{id}", channel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Channel in Elasticsearch
        verify(mockChannelSearchRepository, times(1)).deleteById(channel.getId());
    }

    @Test
    public void searchChannel() throws Exception {
        // Initialize the database
        channelService.save(channel);
        when(mockChannelSearchRepository.search(queryStringQuery("id:" + channel.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(channel), PageRequest.of(0, 1), 1));
        // Search the channel
        restChannelMockMvc.perform(get("/api/_search/channels?query=id:" + channel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].entrepriseId").value(hasItem(DEFAULT_ENTREPRISE_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Channel.class);
        Channel channel1 = new Channel();
        channel1.setId("id1");
        Channel channel2 = new Channel();
        channel2.setId(channel1.getId());
        assertThat(channel1).isEqualTo(channel2);
        channel2.setId("id2");
        assertThat(channel1).isNotEqualTo(channel2);
        channel1.setId(null);
        assertThat(channel1).isNotEqualTo(channel2);
    }
}
