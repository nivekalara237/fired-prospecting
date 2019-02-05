package com.niveka.service;

import com.niveka.domain.Suivi;
import com.niveka.repository.SuiviRepository;
import com.niveka.service.dto.SuiviDTO;
import com.niveka.service.mapper.SuiviMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Suivi.
 */
@Service
public class SuiviService {

    private final Logger log = LoggerFactory.getLogger(SuiviService.class);

    private final SuiviRepository suiviRepository;

    private final SuiviMapper suiviMapper;

    //private final SuiviSearchRepository suiviSearchRepository;

    public SuiviService(SuiviRepository suiviRepository, SuiviMapper suiviMapper/*, SuiviSearchRepository suiviSearchRepository*/) {
        this.suiviRepository = suiviRepository;
        this.suiviMapper = suiviMapper;
        //this.suiviSearchRepository = suiviSearchRepository;
    }

    /**
     * Save a suivi.
     *
     * @param suiviDTO the entity to save
     * @return the persisted entity
     */
    public SuiviDTO save(SuiviDTO suiviDTO) {
        log.debug("Request to save Suivi : {}", suiviDTO);

        Suivi suivi = suiviMapper.toEntity(suiviDTO);
        suivi = suiviRepository.save(suivi);
        SuiviDTO result = suiviMapper.toDto(suivi);
        //suiviSearchRepository.save(suivi);
        return result;
    }


    public SuiviDTO save(Suivi suivi) {
        log.debug("Request to save Suivi : {}", suivi);

        suivi = suiviRepository.save(suivi);
        SuiviDTO result = suiviMapper.toDto(suivi);
        //suiviSearchRepository.save(suivi);
        return result;
    }

    /**
     * Get all the suivis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<SuiviDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Suivis");
        return suiviRepository.findAll(pageable)
            .map(suiviMapper::toDto);
    }


    /**
     * Get one suivi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<SuiviDTO> findOne(String id) {
        log.debug("Request to get Suivi : {}", id);
        return suiviRepository.findById(id)
            .map(suiviMapper::toDto);
    }

    /**
     * Delete the suivi by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Suivi : {}", id);
        suiviRepository.deleteById(id);
        //suiviSearchRepository.deleteById(id);
    }

    /**
     * Search for the suivi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<SuiviDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Suivis for query {}", query);
        /*return suiviSearchRepository.search(queryStringQuery(query), pageable)
            .map(suiviMapper::toDto);*/
        return null;
    }
}
