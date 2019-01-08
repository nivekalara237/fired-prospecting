package com.niveka.service;

import com.niveka.domain.Channel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Channel.
 */
public interface ChannelService {

    /**
     * Save a channel.
     *
     * @param channel the entity to save
     * @return the persisted entity
     */
    Channel save(Channel channel);

    /**
     * Get all the channels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Channel> findAll(Pageable pageable);

    /**
     * Get all the Channel with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Channel> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" channel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Channel> findOne(String id);

    /**
     * Delete the "id" channel.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the channel corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Channel> search(String query, Pageable pageable);
}
