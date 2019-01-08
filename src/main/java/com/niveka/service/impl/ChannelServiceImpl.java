package com.niveka.service.impl;

import com.niveka.service.ChannelService;
import com.niveka.domain.Channel;
import com.niveka.repository.ChannelRepository;
import com.niveka.repository.search.ChannelSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Channel.
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    private final Logger log = LoggerFactory.getLogger(ChannelServiceImpl.class);

    private final ChannelRepository channelRepository;

    private final ChannelSearchRepository channelSearchRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository, ChannelSearchRepository channelSearchRepository) {
        this.channelRepository = channelRepository;
        this.channelSearchRepository = channelSearchRepository;
    }

    /**
     * Save a channel.
     *
     * @param channel the entity to save
     * @return the persisted entity
     */
    @Override
    public Channel save(Channel channel) {
        log.debug("Request to save Channel : {}", channel);
        Channel result = channelRepository.save(channel);
        channelSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the channels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Channel> findAll(Pageable pageable) {
        log.debug("Request to get all Channels");
        return channelRepository.findAll(pageable);
    }

    /**
     * Get all the Channel with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Channel> findAllWithEagerRelationships(Pageable pageable) {
        return channelRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one channel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Channel> findOne(String id) {
        log.debug("Request to get Channel : {}", id);
        return channelRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the channel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Channel : {}", id);
        channelRepository.deleteById(id);
        channelSearchRepository.deleteById(id);
    }

    /**
     * Search for the channel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Channel> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Channels for query {}", query);
        return channelSearchRepository.search(queryStringQuery(query), pageable);    }
}
