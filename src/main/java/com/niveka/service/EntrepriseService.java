package com.niveka.service;

import com.niveka.domain.Entreprise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Entreprise.
 */
public interface EntrepriseService {

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save
     * @return the persisted entity
     */
    Entreprise save(Entreprise entreprise);

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Entreprise> findAll(Pageable pageable);

    /**
     * Get all the Entreprise with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Entreprise> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" entreprise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Entreprise> findOne(String id);

    /**
     * Delete the "id" entreprise.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the entreprise corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Entreprise> search(String query, Pageable pageable);
}
