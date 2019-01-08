package com.niveka.service;

import com.niveka.domain.CompteRenduSuivi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CompteRenduSuivi.
 */
public interface CompteRenduSuiviService {

    /**
     * Save a compteRenduSuivi.
     *
     * @param compteRenduSuivi the entity to save
     * @return the persisted entity
     */
    CompteRenduSuivi save(CompteRenduSuivi compteRenduSuivi);

    /**
     * Get all the compteRenduSuivis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompteRenduSuivi> findAll(Pageable pageable);


    /**
     * Get the "id" compteRenduSuivi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompteRenduSuivi> findOne(String id);

    /**
     * Delete the "id" compteRenduSuivi.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the compteRenduSuivi corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompteRenduSuivi> search(String query, Pageable pageable);
}
