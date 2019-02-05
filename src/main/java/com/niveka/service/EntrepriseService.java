package com.niveka.service;

import com.niveka.domain.Entreprise;
import com.niveka.repository.EntrepriseRepository;
import com.niveka.service.dto.EntrepriseDTO;
import com.niveka.service.mapper.EntrepriseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Entreprise.
 */
@Service
public class EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseService.class);

    private final EntrepriseRepository entrepriseRepository;

    private final EntrepriseMapper entrepriseMapper;

    //@Autowired
    //private EntrepriseSearchRepository entrepriseSearchRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper/*, EntrepriseSearchRepository entrepriseSearchRepository*/) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        //this.entrepriseSearchRepository = entrepriseSearchRepository;
    }

    /**
     * Save a entreprise.
     *
     * @param entrepriseDTO the entity to save
     * @return the persisted entity
     */
    public EntrepriseDTO save(EntrepriseDTO entrepriseDTO) {
        log.debug("Request to save Entreprise : {}", entrepriseDTO);

        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDTO);
        entreprise = entrepriseRepository.save(entreprise);
        EntrepriseDTO result = entrepriseMapper.toDto(entreprise);
        //entrepriseSearchRepository.save(entreprise);
        return result;
    }

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<EntrepriseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll(pageable)
            .map(entrepriseMapper::toDto);
    }

    /**
     * Get all the Entreprise with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<EntrepriseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return entrepriseRepository.findAllWithEagerRelationships(pageable).map(entrepriseMapper::toDto);
    }


    /**
     * Get one entreprise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<EntrepriseDTO> findOne(String id) {
        log.debug("Request to get Entreprise : {}", id);
        return entrepriseRepository.findOneWithEagerRelationships(id)
            .map(entrepriseMapper::toDto);
    }

    /**
     * Delete the entreprise by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
        //entrepriseSearchRepository.deleteById(id);
    }

    /**
     * Search for the entreprise corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<EntrepriseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entreprises for query {}", query);
        //return entrepriseSearchRepository.search(queryStringQuery(query), pageable)
          //  .map(entrepriseMapper::toDto);

        return null;
    }
}
