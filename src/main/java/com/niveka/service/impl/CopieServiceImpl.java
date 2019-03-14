package com.niveka.service.impl;

import com.niveka.domain.Copie;
import com.niveka.repository.CopieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Copie.
 */
@Service
public class CopieServiceImpl{

    private final Logger log = LoggerFactory.getLogger(CopieServiceImpl.class);

    private final CopieRepository copieRepository;

   // private final CopieSearchRepository copieSearchRepository;

    public CopieServiceImpl(CopieRepository copieRepository/*, CopieSearchRepository copieSearchRepository*/) {
        this.copieRepository = copieRepository;
        //this.copieSearchRepository = copieSearchRepository;
    }

    /**
     * Save a copie.
     *
     * @param copie the entity to save
     * @return the persisted entity
     */
    //@Override
    public Copie save(Copie copie) {
        log.debug("Request to save Copie : {}", copie);
        Copie result = copieRepository.save(copie);
        //copieSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the copies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Copie> findAll(Pageable pageable) {
        log.debug("Request to get all Copies");
        return copieRepository.findAll(pageable);
    }


    /**
     * Get one copie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    //@Override
    public Optional<Copie> findOne(String id) {
        log.debug("Request to get Copie : {}", id);
        return copieRepository.findById(id);
    }

    /**
     * Delete the copie by id.
     *
     * @param id the id of the entity
     */
    //@Override
    public void delete(String id) {
        log.debug("Request to delete Copie : {}", id);
        copieRepository.deleteById(id);

        //copieSearchRepository.deleteById(id);
    }

    /**
     * Search for the copie corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Copie> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Copies for query {}", query);
        return null;
        //return copieSearchRepository.search(queryStringQuery(query), pageable);
    }
}
