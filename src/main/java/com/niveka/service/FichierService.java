package com.niveka.service;

import com.niveka.domain.Fichier;
import com.niveka.repository.FichierRepository;
import com.niveka.repository.search.FichierSearchRepository;
import com.niveka.service.dto.FichierDTO;
import com.niveka.service.mapper.FichierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Fichier.
 */
@Service
public class FichierService {

    private final Logger log = LoggerFactory.getLogger(FichierService.class);

    private final FichierRepository fichierRepository;

    private final FichierMapper fichierMapper;

    private final FichierSearchRepository fichierSearchRepository;

    public FichierService(FichierRepository fichierRepository, FichierMapper fichierMapper, FichierSearchRepository fichierSearchRepository) {
        this.fichierRepository = fichierRepository;
        this.fichierMapper = fichierMapper;
        this.fichierSearchRepository = fichierSearchRepository;
    }

    /**
     * Save a fichier.
     *
     * @param fichierDTO the entity to save
     * @return the persisted entity
     */
    public FichierDTO save(FichierDTO fichierDTO) {
        log.debug("Request to save Fichier : {}", fichierDTO);
        Fichier fichier = fichierMapper.toEntity(fichierDTO);
        fichier = fichierRepository.save(fichier);
        FichierDTO result = fichierMapper.toDto(fichier);
        fichierSearchRepository.save(fichier);
        return result;
    }

    /**
     * Get all the fichiers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<FichierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fichiers");
        return fichierRepository.findAll(pageable)
            .map(fichierMapper::toDto);
    }

    /**
     * Get all the fichiers by Model.
     *
     * @param modelId the model ID information
     * @param typModel the type of model. please look Utils.TYPE_MODEL_* enum
     * @return the list of entities
     */
    public List<FichierDTO> findAll(String modelId, String typModel) {
        log.debug("Request to get all Fichiers by model");
        List<FichierDTO> dtos = new ArrayList<>();
        fichierRepository.findByModelAndModelId(typModel,modelId).forEach(fichier -> {
            FichierDTO dto = new FichierDTO();
            BeanUtils.copyProperties(fichier,dto);
            dtos.add(dto);
        });
        return dtos;
            //.map(fichierMapper::toDto);
    }


    /**
     * Get one fichier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<FichierDTO> findOne(String id) {
        log.debug("Request to get Fichier : {}", id);
        return fichierRepository.findById(id)
            .map(fichierMapper::toDto);
    }

    /**
     * Delete the fichier by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Fichier : {}", id);
        fichierRepository.deleteById(id);
        fichierSearchRepository.deleteById(id);
    }

    /**
     * Search for the fichier corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<FichierDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fichiers for query {}", query);
        return fichierSearchRepository.search(queryStringQuery(query), pageable)
            .map(fichierMapper::toDto);
    }
}
