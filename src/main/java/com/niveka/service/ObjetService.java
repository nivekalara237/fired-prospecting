package com.niveka.service;

import com.niveka.domain.Objet;
import com.niveka.repository.ObjetRepository;
import com.niveka.service.dto.ObjetDTO;
import com.niveka.service.mapper.ObjetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Objet.
 */
@Service
public class ObjetService {

    private final Logger log = LoggerFactory.getLogger(ObjetService.class);

    private final ObjetRepository objetRepository;

    private final ObjetMapper objetMapper;

    //private final ObjetSearchRepository objetSearchRepository;

    public ObjetService(ObjetRepository objetRepository, ObjetMapper objetMapper/*, ObjetSearchRepository objetSearchRepository*/) {
        this.objetRepository = objetRepository;
        this.objetMapper = objetMapper;
        //this.objetSearchRepository = objetSearchRepository;
    }

    /**
     * Save a objet.
     *
     * @param objetDTO the entity to save
     * @return the persisted entity
     */
    public ObjetDTO save(ObjetDTO objetDTO) {
        log.debug("Request to save Objet : {}", objetDTO);

        Objet objet = objetMapper.toEntity(objetDTO);
        objet = objetRepository.save(objet);
        ObjetDTO result = objetMapper.toDto(objet);
        //objetSearchRepository.save(objet);
        return result;
    }

    /**
     * Get all the objets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<ObjetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Objets");
        return objetRepository.findAll(pageable)
            .map(objetMapper::toDto);
    }


    /**
     * Get one objet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<ObjetDTO> findOne(String id) {
        log.debug("Request to get Objet : {}", id);
        return objetRepository.findById(id)
            .map(objetMapper::toDto);
    }

    /**
     * Get one objet by rapportId.
     *
     * @param id the id of the rapport
     * @return the list of entity
     */

    public List<Objet> findAllByRapport(String id){
        log.debug("Request to get Objets bay Rapport : {}", id);
        return objetRepository.findAllByRapportId(id);
    }


    /**
     * Delete the objet by id.
     *
     * @param id the id of the entity
     */
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
    public Page<ObjetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Objets for query {}", query);
        //return objetSearchRepository.search(queryStringQuery(query), pageable)
            //.map(objetMapper::toDto);

        return null;
    }
}
