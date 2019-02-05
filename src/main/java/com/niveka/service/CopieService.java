package com.niveka.service;

import com.niveka.domain.Copie;
import com.niveka.repository.CopieRepository;
import com.niveka.service.dto.CopieDTO;
import com.niveka.service.mapper.CopieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Copie.
 */
@Service
public class CopieService {

    private final Logger log = LoggerFactory.getLogger(CopieService.class);

    private final CopieRepository copieRepository;

    private final CopieMapper copieMapper;

    //private final CopieSearchRepository copieSearchRepository;

    public CopieService(CopieRepository copieRepository, CopieMapper copieMapper/*, CopieSearchRepository copieSearchRepository*/) {
        this.copieRepository = copieRepository;
        this.copieMapper = copieMapper;
        //this.copieSearchRepository = copieSearchRepository;
    }

    /**
     * Save a copie.
     *
     * @param copieDTO the entity to save
     * @return the persisted entity
     */
    public CopieDTO save(CopieDTO copieDTO) {
        log.debug("Request to save Copie : {}", copieDTO);

        Copie copie = copieMapper.toEntity(copieDTO);
        copie = copieRepository.save(copie);
        CopieDTO result = copieMapper.toDto(copie);
        //copieSearchRepository.save(copie);
        return result;
    }

    /**
     * Get all the copies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CopieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Copies");
        return copieRepository.findAll(pageable)
            .map(copieMapper::toDto);
    }


    /**
     * Get one copie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CopieDTO> findOne(String id) {
        log.debug("Request to get Copie : {}", id);
        return copieRepository.findById(id)
            .map(copieMapper::toDto);
    }

    /**
     * Delete the copie by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Copie : {}", id);
        copieRepository.deleteById(id);
        //copieSearchRepository.deleteById(id);
    }

    /**
     * Search for the copie corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CopieDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Copies for query {}", query);
        return  null;
//        return copieSearchRepository.search(queryStringQuery(query), pageable)
//            .map(copieMapper::toDto);
    }
}
