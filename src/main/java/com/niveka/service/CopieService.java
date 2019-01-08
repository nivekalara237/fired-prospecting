package com.niveka.service;

import com.niveka.domain.Copie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Copie.
 */
public interface CopieService {

    /**
     * Save a copie.
     *
     * @param copie the entity to save
     * @return the persisted entity
     */
    Copie save(Copie copie);

    /**
     * Get all the copies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Copie> findAll(Pageable pageable);


    /**
     * Get the "id" copie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Copie> findOne(String id);

    /**
     * Delete the "id" copie.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the copie corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Copie> search(String query, Pageable pageable);
}
