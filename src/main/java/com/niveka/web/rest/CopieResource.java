package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Copie;
import com.niveka.service.CopieService;
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
 * REST controller for managing Copie.
 */
@RestController
@RequestMapping("/api")
public class CopieResource {

    private final Logger log = LoggerFactory.getLogger(CopieResource.class);

    private static final String ENTITY_NAME = "copie";

    private final CopieService copieService;

    public CopieResource(CopieService copieService) {
        this.copieService = copieService;
    }

    /**
     * POST  /copies : Create a new copie.
     *
     * @param copie the copie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new copie, or with status 400 (Bad Request) if the copie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/copies")
    @Timed
    public ResponseEntity<Copie> createCopie(@RequestBody Copie copie) throws URISyntaxException {
        log.debug("REST request to save Copie : {}", copie);
        if (copie.getId() != null) {
            throw new BadRequestAlertException("A new copie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Copie result = copieService.save(copie);
        return ResponseEntity.created(new URI("/api/copies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /copies : Updates an existing copie.
     *
     * @param copie the copie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated copie,
     * or with status 400 (Bad Request) if the copie is not valid,
     * or with status 500 (Internal Server Error) if the copie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/copies")
    @Timed
    public ResponseEntity<Copie> updateCopie(@RequestBody Copie copie) throws URISyntaxException {
        log.debug("REST request to update Copie : {}", copie);
        if (copie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Copie result = copieService.save(copie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, copie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /copies : get all the copies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of copies in body
     */
    @GetMapping("/copies")
    @Timed
    public ResponseEntity<List<Copie>> getAllCopies(Pageable pageable) {
        log.debug("REST request to get a page of Copies");
        Page<Copie> page = copieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/copies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /copies/:id : get the "id" copie.
     *
     * @param id the id of the copie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the copie, or with status 404 (Not Found)
     */
    @GetMapping("/copies/{id}")
    @Timed
    public ResponseEntity<Copie> getCopie(@PathVariable String id) {
        log.debug("REST request to get Copie : {}", id);
        Optional<Copie> copie = copieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(copie);
    }

    /**
     * DELETE  /copies/:id : delete the "id" copie.
     *
     * @param id the id of the copie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/copies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCopie(@PathVariable String id) {
        log.debug("REST request to delete Copie : {}", id);
        copieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/copies?query=:query : search for the copie corresponding
     * to the query.
     *
     * @param query the query of the copie search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/copies")
    @Timed
    public ResponseEntity<List<Copie>> searchCopies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Copies for query {}", query);
        Page<Copie> page = copieService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/copies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
