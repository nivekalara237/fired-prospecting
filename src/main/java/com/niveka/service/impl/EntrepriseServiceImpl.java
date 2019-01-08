package com.niveka.service.impl;

import com.niveka.service.EntrepriseService;
import com.niveka.domain.Entreprise;
import com.niveka.repository.EntrepriseRepository;
import com.niveka.repository.search.EntrepriseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Entreprise.
 */
@Service
public class EntrepriseServiceImpl implements EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    private final EntrepriseRepository entrepriseRepository;

    private final EntrepriseSearchRepository entrepriseSearchRepository;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, EntrepriseSearchRepository entrepriseSearchRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseSearchRepository = entrepriseSearchRepository;
    }

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save
     * @return the persisted entity
     */
    @Override
    public Entreprise save(Entreprise entreprise) {
        log.debug("Request to save Entreprise : {}", entreprise);

        Entreprise result = entrepriseRepository.save(entreprise);
        entrepriseSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Entreprise> findAll(Pageable pageable) {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll(pageable);
    }

    /**
     * Get all the Entreprise with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Entreprise> findAllWithEagerRelationships(Pageable pageable) {
        return entrepriseRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     * Get one entreprise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Entreprise> findOne(String id) {
        log.debug("Request to get Entreprise : {}", id);
        return entrepriseRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the entreprise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
        entrepriseSearchRepository.deleteById(id);
    }

    /**
     * Search for the entreprise corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Entreprise> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entreprises for query {}", query);
        return entrepriseSearchRepository.search(queryStringQuery(query), pageable);    }
}
