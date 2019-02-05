package com.niveka.service;

import com.niveka.domain.Message;
import com.niveka.repository.MessageRepository;
import com.niveka.service.dto.MessageDTO;
import com.niveka.service.mapper.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Message.
 */
@Service
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    //private final MessageSearchRepository messageSearchRepository;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper/*, MessageSearchRepository messageSearchRepository*/) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        //this.messageSearchRepository = messageSearchRepository;
    }

    /**
     * Save a message.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    public MessageDTO save(MessageDTO messageDTO) {
        log.debug("Request to save Message : {}", messageDTO);

        Message message = messageMapper.toEntity(messageDTO);
        message = messageRepository.save(message);
        MessageDTO result = messageMapper.toDto(message);
        //messageSearchRepository.save(message);
        return result;
    }

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<MessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        return messageRepository.findAll(pageable)
            .map(messageMapper::toDto);
    }


    /**
     * Get one message by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<MessageDTO> findOne(String id) {
        log.debug("Request to get Message : {}", id);
        return messageRepository.findById(id)
            .map(messageMapper::toDto);
    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.deleteById(id);
        //messageSearchRepository.deleteById(id);
    }

    /**
     * Search for the message corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<MessageDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Messages for query {}", query);
        //return messageSearchRepository.search(queryStringQuery(query), pageable)
          //  .map(messageMapper::toDto);

        return null;
    }
}
