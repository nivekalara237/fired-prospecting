package com.niveka.service;

import com.niveka.domain.Rapport;
import com.niveka.repository.RapportRepository;
import com.niveka.service.dto.RapportDTO;
import com.niveka.service.mapper.RapportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Rapport.
 */
@Service
public class RapportService {

    private final Logger log = LoggerFactory.getLogger(RapportService.class);

    private final RapportRepository rapportRepository;

    private final RapportMapper rapportMapper;

    //private final RapportSearchRepository rapportSearchRepository;

    public RapportService(RapportRepository rapportRepository, RapportMapper rapportMapper/*, RapportSearchRepository rapportSearchRepository*/) {
        this.rapportRepository = rapportRepository;
        this.rapportMapper = rapportMapper;
        //this.rapportSearchRepository = rapportSearchRepository;
    }

    /**
     * Save a rapport.
     *
     * @param rapportDTO the entity to save
     * @return the persisted entity
     */
    public RapportDTO save(RapportDTO rapportDTO) {
        log.debug("Request to save Rapport : {}", rapportDTO);

        Rapport rapport = rapportMapper.toEntity(rapportDTO);
        rapport = rapportRepository.save(rapport);
        RapportDTO result = rapportMapper.toDto(rapport);
        //rapportSearchRepository.save(rapport);
        return result;
    }

    /**
     * Get all the rapports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<RapportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rapports");
        return rapportRepository.findAll(pageable)
            .map(rapportMapper::toDto);
    }


    /**
     * Get one rapport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<RapportDTO> findOne(String id) {
        log.debug("Request to get Rapport : {}", id);
        return rapportRepository.findById(id)
            .map(rapportMapper::toDto);
    }

    /**
     * Delete the rapport by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Rapport : {}", id);
        rapportRepository.deleteById(id);
        //rapportSearchRepository.deleteById(id);
    }

    /**
     * Search for the rapport corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<RapportDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Rapports for query {}", query);
        /*return rapportSearchRepository.search(queryStringQuery(query), pageable)
            .map(rapportMapper::toDto);*/
        return null;
    }
}
