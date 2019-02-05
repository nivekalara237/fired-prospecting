package com.niveka.web.rest;

import com.niveka.FireDApp;

import com.niveka.domain.Message;
import com.niveka.repository.MessageRepository;
import com.niveka.repository.search.MessageSearchRepository;
import com.niveka.service.MessageService;
import com.niveka.service.dto.MessageDTO;
import com.niveka.service.mapper.MessageMapper;
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
 * Test class for the MessageResource REST controller.
 *
 * @see MessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FireDApp.class)
public class MessageResourceIntTest {

    private static final String DEFAULT_CONTENU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VU = "AAAAAAAAAA";
    private static final String UPDATED_VU = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final Long DEFAULT_CHANNEL_ID = 1L;
    private static final Long UPDATED_CHANNEL_ID = 2L;

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_AT = "BBBBBBBBBB";

    private static final String DEFAULT_DELETED_AT = "AAAAAAAAAA";
    private static final String UPDATED_DELETED_AT = "BBBBBBBBBB";

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;

    /**
     * This repository is mocked in the com.niveka.repository.search test package.
     *
     * @see com.niveka.repository.search.MessageSearchRepositoryMockConfiguration
     */
    @Autowired
    private MessageSearchRepository mockMessageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restMessageMockMvc;

    private Message message;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageResource messageResource = new MessageResource(messageService);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
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
    public static Message createEntity() {
        Message message = new Message()
            .contenu(DEFAULT_CONTENU)
            .key(DEFAULT_KEY)
            .vu(DEFAULT_VU)
            .time(DEFAULT_TIME)
            .channelId(DEFAULT_CHANNEL_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return message;
    }

    @Before
    public void initTest() {
        messageRepository.deleteAll();
        message = createEntity();
    }

    @Test
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getContenu()).isEqualTo(DEFAULT_CONTENU);
        assertThat(testMessage.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMessage.getVu()).isEqualTo(DEFAULT_VU);
        assertThat(testMessage.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testMessage.getChannelId()).isEqualTo(DEFAULT_CHANNEL_ID);
        assertThat(testMessage.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMessage.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testMessage.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(1)).save(testMessage);
    }

    @Test
    public void createMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message with an existing ID
        message.setId("existing_id");
        MessageDTO messageDTO = messageMapper.toDto(message);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(0)).save(message);
    }

    @Test
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        // Get all the messageList
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].vu").value(hasItem(DEFAULT_VU.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].channelId").value(hasItem(DEFAULT_CHANNEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId()))
            .andExpect(jsonPath("$.contenu").value(DEFAULT_CONTENU.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.vu").value(DEFAULT_VU.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.channelId").value(DEFAULT_CHANNEL_ID.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMessage() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findById(message.getId()).get();
        updatedMessage
            .contenu(UPDATED_CONTENU)
            .key(UPDATED_KEY)
            .vu(UPDATED_VU)
            .time(UPDATED_TIME)
            .channelId(UPDATED_CHANNEL_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        MessageDTO messageDTO = messageMapper.toDto(updatedMessage);

        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getContenu()).isEqualTo(UPDATED_CONTENU);
        assertThat(testMessage.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMessage.getVu()).isEqualTo(UPDATED_VU);
        assertThat(testMessage.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMessage.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testMessage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testMessage.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(1)).save(testMessage);
    }

    @Test
    public void updateNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(0)).save(message);
    }

    @Test
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Get the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", message.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Message in Elasticsearch
        verify(mockMessageSearchRepository, times(1)).deleteById(message.getId());
    }

    @Test
    public void searchMessage() throws Exception {
        // Initialize the database
        messageRepository.save(message);
        when(mockMessageSearchRepository.search(queryStringQuery("id:" + message.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(message), PageRequest.of(0, 1), 1));
        // Search the message
        restMessageMockMvc.perform(get("/api/_search/messages?query=id:" + message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].vu").value(hasItem(DEFAULT_VU)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].channelId").value(hasItem(DEFAULT_CHANNEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = new Message();
        message1.setId("id1");
        Message message2 = new Message();
        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);
        message2.setId("id2");
        assertThat(message1).isNotEqualTo(message2);
        message1.setId(null);
        assertThat(message1).isNotEqualTo(message2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageDTO.class);
        MessageDTO messageDTO1 = new MessageDTO();
        messageDTO1.setId("id1");
        MessageDTO messageDTO2 = new MessageDTO();
        assertThat(messageDTO1).isNotEqualTo(messageDTO2);
        messageDTO2.setId(messageDTO1.getId());
        assertThat(messageDTO1).isEqualTo(messageDTO2);
        messageDTO2.setId("id2");
        assertThat(messageDTO1).isNotEqualTo(messageDTO2);
        messageDTO1.setId(null);
        assertThat(messageDTO1).isNotEqualTo(messageDTO2);
    }
}
