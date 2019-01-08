package com.niveka.service;

import com.niveka.domain.Suivi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Suivi.
 */
public interface SuiviService {

    /**
     * Save a suivi.
     *
     * @param suivi the entity to save
     * @return the persisted entity
     */
    Suivi save(Suivi suivi);

    /**
     * Get all the suivis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Suivi> findAll(Pageable pageable);


    /**
     * Get the "id" suivi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Suivi> findOne(String id);

    /**
     * Delete the "id" suivi.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the suivi corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Suivi> search(String query, Pageable pageable);
}
