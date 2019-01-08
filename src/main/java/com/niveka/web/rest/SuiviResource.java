package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Suivi;
import com.niveka.service.SuiviService;
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
 * REST controller for managing Suivi.
 */
@RestController
@RequestMapping("/api")
public class SuiviResource {

    private final Logger log = LoggerFactory.getLogger(SuiviResource.class);

    private static final String ENTITY_NAME = "suivi";

    private final SuiviService suiviService;

    public SuiviResource(SuiviService suiviService) {
        this.suiviService = suiviService;
    }

    /**
     * POST  /suivis : Create a new suivi.
     *
     * @param suivi the suivi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suivi, or with status 400 (Bad Request) if the suivi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suivis")
    @Timed
    public ResponseEntity<Suivi> createSuivi(@RequestBody Suivi suivi) throws URISyntaxException {
        log.debug("REST request to save Suivi : {}", suivi);
        if (suivi.getId() != null) {
            throw new BadRequestAlertException("A new suivi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Suivi result = suiviService.save(suivi);
        return ResponseEntity.created(new URI("/api/suivis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suivis : Updates an existing suivi.
     *
     * @param suivi the suivi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suivi,
     * or with status 400 (Bad Request) if the suivi is not valid,
     * or with status 500 (Internal Server Error) if the suivi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suivis")
    @Timed
    public ResponseEntity<Suivi> updateSuivi(@RequestBody Suivi suivi) throws URISyntaxException {
        log.debug("REST request to update Suivi : {}", suivi);
        if (suivi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Suivi result = suiviService.save(suivi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suivi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suivis : get all the suivis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of suivis in body
     */
    @GetMapping("/suivis")
    @Timed
    public ResponseEntity<List<Suivi>> getAllSuivis(Pageable pageable) {
        log.debug("REST request to get a page of Suivis");
        Page<Suivi> page = suiviService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suivis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /suivis/:id : get the "id" suivi.
     *
     * @param id the id of the suivi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suivi, or with status 404 (Not Found)
     */
    @GetMapping("/suivis/{id}")
    @Timed
    public ResponseEntity<Suivi> getSuivi(@PathVariable String id) {
        log.debug("REST request to get Suivi : {}", id);
        Optional<Suivi> suivi = suiviService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suivi);
    }

    /**
     * DELETE  /suivis/:id : delete the "id" suivi.
     *
     * @param id the id of the suivi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suivis/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuivi(@PathVariable String id) {
        log.debug("REST request to delete Suivi : {}", id);
        suiviService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/suivis?query=:query : search for the suivi corresponding
     * to the query.
     *
     * @param query the query of the suivi search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/suivis")
    @Timed
    public ResponseEntity<List<Suivi>> searchSuivis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Suivis for query {}", query);
        Page<Suivi> page = suiviService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/suivis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
