package com.niveka.service.impl;

import com.niveka.domain.Suivi;
import com.niveka.repository.SuiviRepository;
import com.niveka.web.rest.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Suivi.
 */
@Service
public class SuiviServiceImpl{

    private final Logger log = LoggerFactory.getLogger(SuiviServiceImpl.class);

    private final SuiviRepository suiviRepository;

    //private final SuiviSearchRepository suiviSearchRepository;

    public SuiviServiceImpl(SuiviRepository suiviRepository/*, SuiviSearchRepository suiviSearchRepository*/) {
        this.suiviRepository = suiviRepository;
        //this.suiviSearchRepository = suiviSearchRepository;
    }

    /**
     * Save a suivi.
     *
     * @param suivi the entity to save
     * @return the persisted entity
     */
    //@Override
    public Suivi save(Suivi suivi) {
        log.debug("Request to save Suivi : {}", suivi);
        suivi.setUpdatedAt(Utils.currentJodaDateStr());
        Suivi result = suiviRepository.save(suivi);
        //suiviSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the suivis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Suivi> findAll(Pageable pageable) {
        log.debug("Request to get all Suivis");
        return suiviRepository.findAll(pageable);
    }


    /**
     * Get one suivi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    //@Override
    public Optional<Suivi> findOne(String id) {
        log.debug("Request to get Suivi : {}", id);
        return suiviRepository.findById(id);
    }

    /**
     * Delete the suivi by id.
     *
     * @param id the id of the entity
     */
    //@Override
    public void delete(String id) {
        log.debug("Request to delete Suivi : {}", id);
        suiviRepository.deleteById(id);
        //suiviSearchRepository.deleteById(id);
    }

    /**
     * Search for the suivi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Suivi> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Suivis for query {}", query);
        return null;
        //return suiviSearchRepository.search(queryStringQuery(query), pageable);
    }
}
