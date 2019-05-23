package com.niveka.service.impl;

import com.niveka.domain.PieceJointe;
import com.niveka.repository.PieceJointeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing PieceJointe.
 */
@Service
public class ObjetServiceImpl{

    private final Logger log = LoggerFactory.getLogger(ObjetServiceImpl.class);

    private final PieceJointeRepository pieceJointeRepository;

    //private final PieceJointeSearchRepository objetSearchRepository;

    public ObjetServiceImpl(PieceJointeRepository pieceJointeRepository/*, PieceJointeSearchRepository objetSearchRepository*/) {
        this.pieceJointeRepository = pieceJointeRepository;
        //this.objetSearchRepository = objetSearchRepository;
    }

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointe the entity to save
     * @return the persisted entity
     */
    //@Override
    public PieceJointe save(PieceJointe pieceJointe) {
        log.debug("Request to save PieceJointe : {}", pieceJointe);
        PieceJointe result = pieceJointeRepository.save(pieceJointe);
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
    public Page<PieceJointe> findAll(Pageable pageable) {
        log.debug("Request to get all Objets");
        return pieceJointeRepository.findAll(pageable);
    }


    /**
     * Get one objet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    //@Override
    public Optional<PieceJointe> findOne(String id) {
        log.debug("Request to get PieceJointe : {}", id);
        return pieceJointeRepository.findById(id);
    }

    /**
     * Delete the objet by id.
     *
     * @param id the id of the entity
     */
    //@Override
    public void delete(String id) {
        log.debug("Request to delete PieceJointe : {}", id);
        pieceJointeRepository.deleteById(id);
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
    public Page<PieceJointe> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Objets for query {}", query);
        return null;
        //return objetSearchRepository.search(queryStringQuery(query), pageable);
    }
}
