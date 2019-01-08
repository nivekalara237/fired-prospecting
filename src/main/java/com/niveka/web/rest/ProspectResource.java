package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Prospect;
import com.niveka.service.ProspectService;
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
 * REST controller for managing Prospect.
 */
@RestController
@RequestMapping("/api")
public class ProspectResource {

    private final Logger log = LoggerFactory.getLogger(ProspectResource.class);

    private static final String ENTITY_NAME = "prospect";

    private final ProspectService prospectService;

    public ProspectResource(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    /**
     * POST  /prospects : Create a new prospect.
     *
     * @param prospect the prospect to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prospect, or with status 400 (Bad Request) if the prospect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prospects")
    @Timed
    public ResponseEntity<Prospect> createProspect(@RequestBody Prospect prospect) throws URISyntaxException {
        log.debug("REST request to save Prospect : {}", prospect);
        if (prospect.getId() != null) {
            throw new BadRequestAlertException("A new prospect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prospect result = prospectService.save(prospect);
        return ResponseEntity.created(new URI("/api/prospects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prospects : Updates an existing prospect.
     *
     * @param prospect the prospect to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prospect,
     * or with status 400 (Bad Request) if the prospect is not valid,
     * or with status 500 (Internal Server Error) if the prospect couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prospects")
    @Timed
    public ResponseEntity<Prospect> updateProspect(@RequestBody Prospect prospect) throws URISyntaxException {
        log.debug("REST request to update Prospect : {}", prospect);
        if (prospect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prospect result = prospectService.save(prospect);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prospect.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prospects : get all the prospects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prospects in body
     */
    @GetMapping("/prospects")
    @Timed
    public ResponseEntity<List<Prospect>> getAllProspects(Pageable pageable) {
        log.debug("REST request to get a page of Prospects");
        Page<Prospect> page = prospectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prospects");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /prospects/:id : get the "id" prospect.
     *
     * @param id the id of the prospect to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prospect, or with status 404 (Not Found)
     */
    @GetMapping("/prospects/{id}")
    @Timed
    public ResponseEntity<Prospect> getProspect(@PathVariable String id) {
        log.debug("REST request to get Prospect : {}", id);
        Optional<Prospect> prospect = prospectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prospect);
    }

    /**
     * DELETE  /prospects/:id : delete the "id" prospect.
     *
     * @param id the id of the prospect to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prospects/{id}")
    @Timed
    public ResponseEntity<Void> deleteProspect(@PathVariable String id) {
        log.debug("REST request to delete Prospect : {}", id);
        prospectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/prospects?query=:query : search for the prospect corresponding
     * to the query.
     *
     * @param query the query of the prospect search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/prospects")
    @Timed
    public ResponseEntity<List<Prospect>> searchProspects(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Prospects for query {}", query);
        Page<Prospect> page = prospectService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/prospects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
