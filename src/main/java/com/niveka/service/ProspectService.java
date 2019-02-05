package com.niveka.service;

import com.niveka.domain.Prospect;
import com.niveka.repository.ProspectRepository;
import com.niveka.repository.search.ProspectSearchRepository;
import com.niveka.service.dto.ProspectDTO;
import com.niveka.service.mapper.ProspectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Prospect.
 */
@Service
public class ProspectService {

    private final Logger log = LoggerFactory.getLogger(ProspectService.class);

    private final ProspectRepository prospectRepository;

    private final ProspectMapper prospectMapper;

    private final ProspectSearchRepository prospectSearchRepository;

    public ProspectService(ProspectRepository prospectRepository, ProspectMapper prospectMapper, ProspectSearchRepository prospectSearchRepository) {
        this.prospectRepository = prospectRepository;
        this.prospectMapper = prospectMapper;
        this.prospectSearchRepository = prospectSearchRepository;
    }

    /**
     * Save a prospect.
     *
     * @param prospectDTO the entity to save
     * @return the persisted entity
     */
    public ProspectDTO save(ProspectDTO prospectDTO) {
        log.debug("Request to save Prospect : {}", prospectDTO);

        Prospect prospect = prospectMapper.toEntity(prospectDTO);

        prospect = prospectRepository.save(prospect);
        ProspectDTO result = prospectMapper.toDto(prospect);
        prospectSearchRepository.save(prospect);
        return result;
    }

    /**
     * Get all the prospects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<ProspectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prospects");
        return prospectRepository.findAll(pageable)
            .map(prospectMapper::toDto);
    }


    /**
     * Get one prospect by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<ProspectDTO> findOne(String id) {
        log.debug("Request to get Prospect : {}", id);
        return prospectRepository.findById(id)
            .map(prospectMapper::toDto);
    }

    /**
     * Delete the prospect by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Prospect : {}", id);
        prospectRepository.deleteById(id);
        prospectSearchRepository.deleteById(id);
    }

    /**
     * Search for the prospect corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<ProspectDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Prospects for query {}", query);
        return prospectSearchRepository.search(queryStringQuery(query), pageable)
            .map(prospectMapper::toDto);

        //return null;
    }
}
