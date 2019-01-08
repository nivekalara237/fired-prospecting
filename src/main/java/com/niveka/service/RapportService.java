package com.niveka.service;

import com.niveka.domain.Rapport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Rapport.
 */
public interface RapportService {

    /**
     * Save a rapport.
     *
     * @param rapport the entity to save
     * @return the persisted entity
     */
    Rapport save(Rapport rapport);

    /**
     * Get all the rapports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Rapport> findAll(Pageable pageable);


    /**
     * Get the "id" rapport.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Rapport> findOne(String id);

    /**
     * Delete the "id" rapport.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the rapport corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Rapport> search(String query, Pageable pageable);
}
