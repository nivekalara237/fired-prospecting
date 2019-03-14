package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.User;
import com.niveka.service.EntrepriseService;
import com.niveka.service.UserService;
import com.niveka.service.dto.EntrepriseDTO;
import com.niveka.web.rest.errors.BadRequestAlertException;
import com.niveka.web.rest.util.HeaderUtil;
import com.niveka.web.rest.util.PaginationUtil;
import com.niveka.web.rest.util.Utils;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entreprise.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    private final EntrepriseService entrepriseService;

    @Autowired
    private UserService userService;

    public EntrepriseResource(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    /**
     * POST  /entreprises : Create a new entreprise.
     *
     * @param entrepriseDTO the entrepriseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrepriseDTO, or with status 400 (Bad Request) if the entreprise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entreprises")
    @Timed
    public ResponseEntity<EntrepriseDTO> createEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entrepriseDTO);
        if (entrepriseDTO.getId() != null) {
            throw new BadRequestAlertException("A new entreprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        entrepriseDTO.setCreatedAt(Utils.currentJodaDateStr());
        entrepriseDTO.setUpdatedAt(Utils.currentJodaDateStr());
        EntrepriseDTO result = entrepriseService.save(entrepriseDTO);
        return ResponseEntity.created(new URI("/api/entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entreprises : Updates an existing entreprise.
     *
     * @param entrepriseDTO the entrepriseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrepriseDTO,
     * or with status 400 (Bad Request) if the entrepriseDTO is not valid,
     * or with status 500 (Internal Server Error) if the entrepriseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entreprises")
    @Timed
    public ResponseEntity<EntrepriseDTO> updateEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}", entrepriseDTO);
        if (entrepriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        entrepriseDTO.setUpdatedAt(Utils.currentJodaDateStr());
        EntrepriseDTO result = entrepriseService.save(entrepriseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entrepriseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entreprises : get all the entreprises.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of entreprises in body
     */
    @GetMapping("/entreprises")
    @Timed
    public ResponseEntity<List<EntrepriseDTO>> getAllEntreprises(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Entreprises");
        Page<EntrepriseDTO> page;
        if (eagerload) {
            page = entrepriseService.findAllWithEagerRelationships(pageable);
        } else {
            page = entrepriseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/entreprises?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("entreprises/all/{id}")
    @Timed
    public ResponseEntity<List<EntrepriseDTO>> getEnterpriseOfUserConnected(@PathVariable String id){
        User user = userService.findOne(id);
        log.debug("USERRRRRRRRR: {}",user);
        if (user==null){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        List<EntrepriseDTO> entreprises = new ArrayList<>();
        entreprises.add(user.getEntreprise()==null?null:user.getEntreprise().toDTO());
        return ResponseEntity.ok().body(entreprises);
    }

    @GetMapping("/entreprises/range-users")
    @Timed
    public ResponseEntity<List<String>> getRangeU(){
        List<String> ranges = new ArrayList<>();
        ranges.add("1-5");
        ranges.add("6-10");
        ranges.add("11-25");
        ranges.add("26-50");
        ranges.add("51-100");
        ranges.add("101-250");
        ranges.add("250-X");
        ranges.add("CUSTOM");
        log.debug("RANGE : {}",ranges);
        return ResponseEntity.ok().body(ranges);
    }

    /**
     * GET  /entreprises/:id : get the "id" entreprise.
     *
     * @param id the id of the entrepriseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrepriseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entreprises/{id}")
    @Timed
    public ResponseEntity<EntrepriseDTO> getEntreprise(@PathVariable String id) {
        log.debug("REST request to get Entreprise : {}", id);
        Optional<EntrepriseDTO> entrepriseDTO = entrepriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entrepriseDTO);
    }

    /**
     * DELETE  /entreprises/:id : delete the "id" entreprise.
     *
     * @param id the id of the entrepriseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entreprises/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntreprise(@PathVariable String id) {
        log.debug("REST request to delete Entreprise : {}", id);
        entrepriseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/entreprises?query=:query : search for the entreprise corresponding
     * to the query.
     *
     * @param query the query of the entreprise search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/entreprises")
    @Timed
    public ResponseEntity<List<EntrepriseDTO>> searchEntreprises(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Entreprises for query {}", query);
        Page<EntrepriseDTO> page = entrepriseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/entreprises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
