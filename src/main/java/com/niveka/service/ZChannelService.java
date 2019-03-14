package com.niveka.service;

import com.niveka.domain.ZChannel;
import com.niveka.repository.ZChannelRepository;
import com.niveka.repository.search.ZChannelSearchRepository;
import com.niveka.service.dto.ZChannelDTO;
import com.niveka.service.mapper.ChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing ZChannel.
 */
@Service
public class ZChannelService {

    private final Logger log = LoggerFactory.getLogger(ZChannelService.class);

    private final ZChannelRepository ZChannelRepository;

    private final ChannelMapper channelMapper;

    private final ZChannelSearchRepository ZChannelSearchRepository;
    @Resource
    private MongoTemplate mongoTemplate;

    public ZChannelService(ZChannelRepository ZChannelRepository, ChannelMapper channelMapper, ZChannelSearchRepository ZChannelSearchRepository) {
        this.ZChannelRepository = ZChannelRepository;
        this.channelMapper = channelMapper;
        this.ZChannelSearchRepository = ZChannelSearchRepository;
    }

    /**
     * Save a channel.
     *
     * @param channelDTO the entity to save
     * @return the persisted entity
     */
    public ZChannelDTO save(ZChannelDTO channelDTO) {
        log.debug("Request to save ZChannel : {}", channelDTO);

        ZChannel channel = channelMapper.toEntity(channelDTO);
        log.debug("######################### : {}", channel.getEntreprise());
        channel = ZChannelRepository.save(channel);
        ZChannelDTO result = channelMapper.toDto(channel);
        ZChannelSearchRepository.save(channel);
        return result;
    }

    /**
     * Get all the channels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<ZChannelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Channels");
        return ZChannelRepository.findAll(pageable)
            .map(channelMapper::toDto);
    }

    /**
     * Get all the ZChannel with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ZChannelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ZChannelRepository.findAllWithEagerRelationships(pageable).map(channelMapper::toDto);
    }


    /**
     * Get one channel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<ZChannelDTO> findOne(String id) {
        log.debug("Request to get ZChannel : {}", id);
        return ZChannelRepository.findOneWithEagerRelationships(id)
            .map(channelMapper::toDto);
    }

    /**
     * Delete the channel by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZChannel : {}", id);
        ZChannelRepository.deleteById(id);
        ZChannelSearchRepository.deleteById(id);
    }

    /**
     * Search for the channel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<ZChannelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Channels for query {}", query);
        return ZChannelSearchRepository.search(queryStringQuery(query), pageable)
            .map(channelMapper::toDto);
    }

    public List<ZChannel> findAllChannelForEntreprise(String eId) {
        return ZChannelRepository.findByEntreprise_Id(eId);
    }
}
