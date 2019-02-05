package com.niveka.service;

import com.niveka.domain.CompteRenduSuivi;
import com.niveka.repository.CompteRenduSuiviRepository;
import com.niveka.repository.ProspectRepository;
import com.niveka.repository.search.CompteRenduSuiviSearchRepository;
import com.niveka.service.dto.CompteRenduSuiviDTO;
import com.niveka.service.mapper.CompteRenduSuiviMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CompteRenduSuivi.
 */
@Service
public class CompteRenduSuiviService {

    private final Logger log = LoggerFactory.getLogger(CompteRenduSuiviService.class);

    private final CompteRenduSuiviRepository compteRenduSuiviRepository;

    private final CompteRenduSuiviMapper compteRenduSuiviMapper;
    @Autowired
    private ProspectRepository prospectRepository;

    private final CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository;

    public CompteRenduSuiviService(CompteRenduSuiviRepository compteRenduSuiviRepository, CompteRenduSuiviMapper compteRenduSuiviMapper, CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository) {
        this.compteRenduSuiviRepository = compteRenduSuiviRepository;
        this.compteRenduSuiviMapper = compteRenduSuiviMapper;
        this.compteRenduSuiviSearchRepository = compteRenduSuiviSearchRepository;
    }

    /**
     * Save a compteRenduSuivi.
     *
     * @param compteRenduSuiviDTO the entity to save
     * @return the persisted entity
     */
    public CompteRenduSuiviDTO save(CompteRenduSuiviDTO compteRenduSuiviDTO) {
        log.debug("Request to save CompteRenduSuivi : {}", compteRenduSuiviDTO);

        CompteRenduSuivi compteRenduSuivi = compteRenduSuiviMapper.toEntity(compteRenduSuiviDTO);
        log.debug("-----------------: {}", compteRenduSuivi);
        //compteRenduSuivi.setProspect(prospectRepository.findById(compteRenduSuiviDTO.getProspectId()).get());
        compteRenduSuivi = compteRenduSuiviRepository.save(compteRenduSuivi);
        CompteRenduSuiviDTO result = compteRenduSuiviMapper.toDto(compteRenduSuivi);
        compteRenduSuiviSearchRepository.save(compteRenduSuivi);
        return result;
    }

    /**
     * Get all the compteRenduSuivis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CompteRenduSuiviDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompteRenduSuivis");
        return compteRenduSuiviRepository.findAll(pageable)
            .map(compteRenduSuiviMapper::toDto);
    }


    /**
     * Get one compteRenduSuivi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CompteRenduSuiviDTO> findOne(String id) {
        log.debug("Request to get CompteRenduSuivi : {}", id);
        return compteRenduSuiviRepository.findById(id)
            .map(compteRenduSuiviMapper::toDto);
    }

    /**
     * Delete the compteRenduSuivi by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete CompteRenduSuivi : {}", id);
        compteRenduSuiviRepository.deleteById(id);
        compteRenduSuiviSearchRepository.deleteById(id);
    }

    /**
     * Search for the compteRenduSuivi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CompteRenduSuiviDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompteRenduSuivis for query {}", query);
        return compteRenduSuiviSearchRepository.search(queryStringQuery(query), pageable)
            .map(compteRenduSuiviMapper::toDto);

    }

    /**
     * Get all the compteRenduSuivis by prospectID.
     *
     * @param prospectId the pagination information
     * @return the list of entities
     */
    public List<CompteRenduSuivi> findByProspect(String prospectId) {
        log.debug("Request to get all CompteRenduSuivis");
        return compteRenduSuiviRepository.findByProspectExists(prospectId);
            //.map(compteRenduSuiviMapper::toDto);
    }
}
