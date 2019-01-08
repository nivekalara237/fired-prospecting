package com.niveka.service;

import com.niveka.domain.Objet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Objet.
 */
public interface ObjetService {

    /**
     * Save a objet.
     *
     * @param objet the entity to save
     * @return the persisted entity
     */
    Objet save(Objet objet);

    /**
     * Get all the objets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Objet> findAll(Pageable pageable);


    /**
     * Get the "id" objet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Objet> findOne(String id);

    /**
     * Delete the "id" objet.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the objet corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Objet> search(String query, Pageable pageable);
}
