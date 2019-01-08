package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Entreprise;
import com.niveka.service.EntrepriseService;
import com.niveka.web.rest.errors.BadRequestAlertException;
import com.niveka.web.rest.util.HeaderUtil;
import com.niveka.web.rest.util.PaginationUtil;
import com.niveka.web.rest.util.Utils;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Entreprise.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    private final EntrepriseService entrepriseService;

    public EntrepriseResource(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    /**
     * POST  /entreprises : Create a new entreprise.
     *
     * @param entreprise the entreprise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entreprise, or with status 400 (Bad Request) if the entreprise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entreprises")
    @Timed
    public ResponseEntity<Entreprise> createEntreprise(@RequestBody Entreprise entreprise) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entreprise);
        if (entreprise.getId() != null) {
            throw new BadRequestAlertException("A new entreprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        entreprise.setCreatedAt(Utils.currentJodaDateStr());
        entreprise.setUpdatedAt(Utils.currentJodaDateStr());
        Entreprise result = entrepriseService.save(entreprise);
        return ResponseEntity.created(new URI("/api/entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entreprises : Updates an existing entreprise.
     *
     * @param entreprise the entreprise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entreprise,
     * or with status 400 (Bad Request) if the entreprise is not valid,
     * or with status 500 (Internal Server Error) if the entreprise couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entreprises")
    @Timed
    public ResponseEntity<Entreprise> updateEntreprise(@RequestBody Entreprise entreprise) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}", entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        entreprise.setUpdatedAt(Utils.currentJodaDateStr());
        Entreprise result = entrepriseService.save(entreprise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entreprise.getId().toString()))
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
    public ResponseEntity<List<Entreprise>> getAllEntreprises(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Entreprises");
        Page<Entreprise> page;
        if (eagerload) {
            page = entrepriseService.findAllWithEagerRelationships(pageable);
        } else {
            page = entrepriseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/entreprises?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /entreprises/:id : get the "id" entreprise.
     *
     * @param id the id of the entreprise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entreprise, or with status 404 (Not Found)
     */
    @GetMapping("/entreprises/{id}")
    @Timed
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable String id) {
        log.debug("REST request to get Entreprise : {}", id);
        Optional<Entreprise> entreprise = entrepriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entreprise);
    }

    /**
     * DELETE  /entreprises/:id : delete the "id" entreprise.
     *
     * @param id the id of the entreprise to delete
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
    public ResponseEntity<List<Entreprise>> searchEntreprises(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Entreprises for query {}", query);
        Page<Entreprise> page = entrepriseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/entreprises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
