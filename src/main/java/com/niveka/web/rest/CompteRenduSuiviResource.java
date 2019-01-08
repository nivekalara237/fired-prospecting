package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.CompteRenduSuivi;
import com.niveka.service.CompteRenduSuiviService;
import com.niveka.web.rest.errors.BadRequestAlertException;
import com.niveka.web.rest.util.HeaderUtil;
import com.niveka.web.rest.util.PaginationUtil;
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
 * REST controller for managing CompteRenduSuivi.
 */
@RestController
@RequestMapping("/api")
public class CompteRenduSuiviResource {

    private final Logger log = LoggerFactory.getLogger(CompteRenduSuiviResource.class);

    private static final String ENTITY_NAME = "compteRenduSuivi";

    private final CompteRenduSuiviService compteRenduSuiviService;

    public CompteRenduSuiviResource(CompteRenduSuiviService compteRenduSuiviService) {
        this.compteRenduSuiviService = compteRenduSuiviService;
    }

    /**
     * POST  /compte-rendu-suivis : Create a new compteRenduSuivi.
     *
     * @param compteRenduSuivi the compteRenduSuivi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteRenduSuivi, or with status 400 (Bad Request) if the compteRenduSuivi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-rendu-suivis")
    @Timed
    public ResponseEntity<CompteRenduSuivi> createCompteRenduSuivi(@RequestBody CompteRenduSuivi compteRenduSuivi) throws URISyntaxException {
        log.debug("REST request to save CompteRenduSuivi : {}", compteRenduSuivi);
        if (compteRenduSuivi.getId() != null) {
            throw new BadRequestAlertException("A new compteRenduSuivi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompteRenduSuivi result = compteRenduSuiviService.save(compteRenduSuivi);
        return ResponseEntity.created(new URI("/api/compte-rendu-suivis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compte-rendu-suivis : Updates an existing compteRenduSuivi.
     *
     * @param compteRenduSuivi the compteRenduSuivi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteRenduSuivi,
     * or with status 400 (Bad Request) if the compteRenduSuivi is not valid,
     * or with status 500 (Internal Server Error) if the compteRenduSuivi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-rendu-suivis")
    @Timed
    public ResponseEntity<CompteRenduSuivi> updateCompteRenduSuivi(@RequestBody CompteRenduSuivi compteRenduSuivi) throws URISyntaxException {
        log.debug("REST request to update CompteRenduSuivi : {}", compteRenduSuivi);
        if (compteRenduSuivi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompteRenduSuivi result = compteRenduSuiviService.save(compteRenduSuivi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteRenduSuivi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compte-rendu-suivis : get all the compteRenduSuivis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of compteRenduSuivis in body
     */
    @GetMapping("/compte-rendu-suivis")
    @Timed
    public ResponseEntity<List<CompteRenduSuivi>> getAllCompteRenduSuivis(Pageable pageable) {
        log.debug("REST request to get a page of CompteRenduSuivis");
        Page<CompteRenduSuivi> page = compteRenduSuiviService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-rendu-suivis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /compte-rendu-suivis/:id : get the "id" compteRenduSuivi.
     *
     * @param id the id of the compteRenduSuivi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteRenduSuivi, or with status 404 (Not Found)
     */
    @GetMapping("/compte-rendu-suivis/{id}")
    @Timed
    public ResponseEntity<CompteRenduSuivi> getCompteRenduSuivi(@PathVariable String id) {
        log.debug("REST request to get CompteRenduSuivi : {}", id);
        Optional<CompteRenduSuivi> compteRenduSuivi = compteRenduSuiviService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteRenduSuivi);
    }

    /**
     * DELETE  /compte-rendu-suivis/:id : delete the "id" compteRenduSuivi.
     *
     * @param id the id of the compteRenduSuivi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-rendu-suivis/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteRenduSuivi(@PathVariable String id) {
        log.debug("REST request to delete CompteRenduSuivi : {}", id);
        compteRenduSuiviService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/compte-rendu-suivis?query=:query : search for the compteRenduSuivi corresponding
     * to the query.
     *
     * @param query the query of the compteRenduSuivi search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/compte-rendu-suivis")
    @Timed
    public ResponseEntity<List<CompteRenduSuivi>> searchCompteRenduSuivis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompteRenduSuivis for query {}", query);
        Page<CompteRenduSuivi> page = compteRenduSuiviService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/compte-rendu-suivis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
