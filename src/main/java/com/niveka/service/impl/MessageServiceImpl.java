package com.niveka.service.impl;

import com.niveka.domain.Message;
import com.niveka.repository.MessageRepository;
import com.niveka.web.rest.util.Utils;
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
public class MessageServiceImpl{

    private final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final MessageRepository messageRepository;

    //private final MessageSearchRepository messageSearchRepository;

    public MessageServiceImpl(MessageRepository messageRepository/*, MessageSearchRepository messageSearchRepository*/) {
        this.messageRepository = messageRepository;
        //this.messageSearchRepository = messageSearchRepository;
    }

    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    //@Override
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);
        message.setUpdatedAt(Utils.currentJodaDateStr());
        Message result = messageRepository.save(message);
        //messageSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Message> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        return messageRepository.findAll(pageable);
    }


    /**
     * Get one message by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    //@Override
    public Optional<Message> findOne(String id) {
        log.debug("Request to get Message : {}", id);
        return messageRepository.findById(id);
    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity
     */
    //@Override
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
    //@Override
    public Page<Message> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Messages for query {}", query);
        return null;
        //return messageSearchRepository.search(queryStringQuery(query), pageable);
    }
}
