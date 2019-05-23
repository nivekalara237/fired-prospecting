package com.niveka.service;

import com.niveka.domain.PieceJointe;
import com.niveka.repository.PieceJointeRepository;
import com.niveka.service.dto.PieceJointeDTO;
import com.niveka.service.mapper.PieceJointeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PieceJointe.
 */
@Service
public class PieceJointeService {

    private final Logger log = LoggerFactory.getLogger(PieceJointeService.class);

    private final PieceJointeRepository pieceJointeRepository;

    private final PieceJointeMapper pieceJointeMapper;

    //private final PieceJointeSearchRepository objetSearchRepository;

    public PieceJointeService(PieceJointeRepository pieceJointeRepository, PieceJointeMapper pieceJointeMapper/*, PieceJointeSearchRepository objetSearchRepository*/) {
        this.pieceJointeRepository = pieceJointeRepository;
        this.pieceJointeMapper = pieceJointeMapper;
        //this.objetSearchRepository = objetSearchRepository;
    }

    /**
     * Save a objet.
     *
     * @param pieceJointeDTO the entity to save
     * @return the persisted entity
     */
    public PieceJointeDTO save(PieceJointeDTO pieceJointeDTO) {
        log.debug("Request to save PieceJointe : {}", pieceJointeDTO);

        PieceJointe pieceJointe = pieceJointeMapper.toEntity(pieceJointeDTO);
        pieceJointe = pieceJointeRepository.save(pieceJointe);
        PieceJointeDTO result = pieceJointeMapper.toDto(pieceJointe);
        //objetSearchRepository.save(pieceJointe);
        return result;
    }

    /**
     * Get all the objets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<PieceJointeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Objets");
        return pieceJointeRepository.findAll(pageable)
            .map(pieceJointeMapper::toDto);
    }


    /**
     * Get one objet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<PieceJointeDTO> findOne(String id) {
        log.debug("Request to get PieceJointe : {}", id);
        return pieceJointeRepository.findById(id)
            .map(pieceJointeMapper::toDto);
    }

    /**
     * Get one objet by rapportId.
     *
     * @param id the id of the rapport
     * @return the list of entity
     */

    public List<PieceJointe> findAllByRapport(String id){
        log.debug("Request to get Objets bay Rapport : {}", id);
        return pieceJointeRepository.findAllByRapportId(id);
    }


    /**
     * Delete the objet by id.
     *
     * @param id the id of the entity
     */
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
    public Page<PieceJointeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Objets for query {}", query);
        //return objetSearchRepository.search(queryStringQuery(query), pageable)
            //.map(objetMapper::toDto);

        return null;
    }
}
