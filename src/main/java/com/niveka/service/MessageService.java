package com.niveka.service;

import com.niveka.domain.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Message.
 */
public interface MessageService {

    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    Message save(Message message);

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Message> findAll(Pageable pageable);


    /**
     * Get the "id" message.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Message> findOne(String id);

    /**
     * Delete the "id" message.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the message corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Message> search(String query, Pageable pageable);
}
