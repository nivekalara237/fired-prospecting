package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.service.SuiviService;
import com.niveka.service.dto.SuiviDTO;
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
     * @param suiviDTO the suiviDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suiviDTO, or with status 400 (Bad Request) if the suivi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suivis")
    @Timed
    public ResponseEntity<SuiviDTO> createSuivi(@RequestBody SuiviDTO suiviDTO) throws URISyntaxException {
        log.debug("REST request to save Suivi : {}", suiviDTO);
        if (suiviDTO.getId() != null) {
            throw new BadRequestAlertException("A new suivi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuiviDTO result = suiviService.save(suiviDTO);
        return ResponseEntity.created(new URI("/api/suivis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suivis : Updates an existing suivi.
     *
     * @param suiviDTO the suiviDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suiviDTO,
     * or with status 400 (Bad Request) if the suiviDTO is not valid,
     * or with status 500 (Internal Server Error) if the suiviDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suivis")
    @Timed
    public ResponseEntity<SuiviDTO> updateSuivi(@RequestBody SuiviDTO suiviDTO) throws URISyntaxException {
        log.debug("REST request to update Suivi : {}", suiviDTO);
        if (suiviDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SuiviDTO result = suiviService.save(suiviDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suiviDTO.getId().toString()))
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
    public ResponseEntity<List<SuiviDTO>> getAllSuivis(Pageable pageable) {
        log.debug("REST request to get a page of Suivis");
        Page<SuiviDTO> page = suiviService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suivis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /suivis/:id : get the "id" suivi.
     *
     * @param id the id of the suiviDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suiviDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suivis/{id}")
    @Timed
    public ResponseEntity<SuiviDTO> getSuivi(@PathVariable String id) {
        log.debug("REST request to get Suivi : {}", id);
        Optional<SuiviDTO> suiviDTO = suiviService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suiviDTO);
    }

    /**
     * DELETE  /suivis/:id : delete the "id" suivi.
     *
     * @param id the id of the suiviDTO to delete
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
    public ResponseEntity<List<SuiviDTO>> searchSuivis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Suivis for query {}", query);
        Page<SuiviDTO> page = suiviService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/suivis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
