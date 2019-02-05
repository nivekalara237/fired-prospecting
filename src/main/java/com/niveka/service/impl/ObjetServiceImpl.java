package com.niveka.service.impl;

import com.niveka.domain.Objet;
import com.niveka.repository.ObjetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Objet.
 */
@Service
public class ObjetServiceImpl{

    private final Logger log = LoggerFactory.getLogger(ObjetServiceImpl.class);

    private final ObjetRepository objetRepository;

    //private final ObjetSearchRepository objetSearchRepository;

    public ObjetServiceImpl(ObjetRepository objetRepository/*, ObjetSearchRepository objetSearchRepository*/) {
        this.objetRepository = objetRepository;
        //this.objetSearchRepository = objetSearchRepository;
    }

    /**
     * Save a objet.
     *
     * @param objet the entity to save
     * @return the persisted entity
     */
    //@Override
    public Objet save(Objet objet) {
        log.debug("Request to save Objet : {}", objet);
        Objet result = objetRepository.save(objet);
        //objetSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the objets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Objet> findAll(Pageable pageable) {
        log.debug("Request to get all Objets");
        return objetRepository.findAll(pageable);
    }


    /**
     * Get one objet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    //@Override
    public Optional<Objet> findOne(String id) {
        log.debug("Request to get Objet : {}", id);
        return objetRepository.findById(id);
    }

    /**
     * Delete the objet by id.
     *
     * @param id the id of the entity
     */
    //@Override
    public void delete(String id) {
        log.debug("Request to delete Objet : {}", id);
        objetRepository.deleteById(id);
        //objetSearchRepository.deleteById(id);
    }

    /**
     * Search for the objet corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    //@Override
    public Page<Objet> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Objets for query {}", query);
        return null;
        //return objetSearchRepository.search(queryStringQuery(query), pageable);
    }
}
