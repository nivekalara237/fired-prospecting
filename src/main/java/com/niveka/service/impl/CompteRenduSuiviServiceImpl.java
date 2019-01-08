package com.niveka.service.impl;

import com.niveka.service.CompteRenduSuiviService;
import com.niveka.domain.CompteRenduSuivi;
import com.niveka.repository.CompteRenduSuiviRepository;
import com.niveka.repository.search.CompteRenduSuiviSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CompteRenduSuivi.
 */
@Service
public class CompteRenduSuiviServiceImpl implements CompteRenduSuiviService {

    private final Logger log = LoggerFactory.getLogger(CompteRenduSuiviServiceImpl.class);

    private final CompteRenduSuiviRepository compteRenduSuiviRepository;

    private final CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository;

    public CompteRenduSuiviServiceImpl(CompteRenduSuiviRepository compteRenduSuiviRepository, CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository) {
        this.compteRenduSuiviRepository = compteRenduSuiviRepository;
        this.compteRenduSuiviSearchRepository = compteRenduSuiviSearchRepository;
    }

    /**
     * Save a compteRenduSuivi.
     *
     * @param compteRenduSuivi the entity to save
     * @return the persisted entity
     */
    @Override
    public CompteRenduSuivi save(CompteRenduSuivi compteRenduSuivi) {
        log.debug("Request to save CompteRenduSuivi : {}", compteRenduSuivi);
        CompteRenduSuivi result = compteRenduSuiviRepository.save(compteRenduSuivi);
        compteRenduSuiviSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the compteRenduSuivis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<CompteRenduSuivi> findAll(Pageable pageable) {
        log.debug("Request to get all CompteRenduSuivis");
        return compteRenduSuiviRepository.findAll(pageable);
    }


    /**
     * Get one compteRenduSuivi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<CompteRenduSuivi> findOne(String id) {
        log.debug("Request to get CompteRenduSuivi : {}", id);
        return compteRenduSuiviRepository.findById(id);
    }

    /**
     * Delete the compteRenduSuivi by id.
     *
     * @param id the id of the entity
     */
    @Override
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
    @Override
    public Page<CompteRenduSuivi> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompteRenduSuivis for query {}", query);
        return compteRenduSuiviSearchRepository.search(queryStringQuery(query), pageable);    }
}
