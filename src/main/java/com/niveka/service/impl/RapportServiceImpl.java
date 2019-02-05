package com.niveka.service.impl;

import com.niveka.domain.Rapport;
import com.niveka.repository.RapportRepository;
import com.niveka.web.rest.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Rapport.
 */
@Service
public class RapportServiceImpl {

    private final Logger log = LoggerFactory.getLogger(RapportServiceImpl.class);

    private final RapportRepository rapportRepository;

    //private final RapportSearchRepository rapportSearchRepository;

    public RapportServiceImpl(RapportRepository rapportRepository/*, RapportSearchRepository rapportSearchRepository*/) {
        this.rapportRepository = rapportRepository;
        //this.rapportSearchRepository = rapportSearchRepository;
    }

    /**
     * Save a rapport.
     *
     * @param rapport the entity to save
     * @return the persisted entity
     */
    //@Override
    public Rapport save(Rapport rapport) {
        log.debug("Request to save Rapport : {}", rapport);
        rapport.setUpdatedAt(Utils.currentJodaDateStr());
        Rapport result = rapportRepository.save(rapport);
        //rapportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the rapports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Rapport> findAll(Pageable pageable) {
        log.debug("Request to get all Rapports");
        return rapportRepository.findAll(pageable);
    }


    /**
     * Get one rapport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    //@Override
    public Optional<Rapport> findOne(String id) {
        log.debug("Request to get Rapport : {}", id);
        return rapportRepository.findById(id);
    }

    /**
     * Delete the rapport by id.
     *
     * @param id the id of the entity
     */
    //@Override
    public void delete(String id) {
        log.debug("Request to delete Rapport : {}", id);
        rapportRepository.deleteById(id);
        //rapportSearchRepository.deleteById(id);
    }

    /**
     * Search for the rapport corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Rapport> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Rapports for query {}", query);
        return null;
        //return rapportSearchRepository.search(queryStringQuery(query), pageable);
    }
}
