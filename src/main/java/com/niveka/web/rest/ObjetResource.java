package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Objet;
import com.niveka.service.ObjetService;
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
 * REST controller for managing Objet.
 */
@RestController
@RequestMapping("/api")
public class ObjetResource {

    private final Logger log = LoggerFactory.getLogger(ObjetResource.class);

    private static final String ENTITY_NAME = "objet";

    private final ObjetService objetService;

    public ObjetResource(ObjetService objetService) {
        this.objetService = objetService;
    }

    /**
     * POST  /objets : Create a new objet.
     *
     * @param objet the objet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new objet, or with status 400 (Bad Request) if the objet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/objets")
    @Timed
    public ResponseEntity<Objet> createObjet(@RequestBody Objet objet) throws URISyntaxException {
        log.debug("REST request to save Objet : {}", objet);
        if (objet.getId() != null) {
            throw new BadRequestAlertException("A new objet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Objet result = objetService.save(objet);
        return ResponseEntity.created(new URI("/api/objets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /objets : Updates an existing objet.
     *
     * @param objet the objet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated objet,
     * or with status 400 (Bad Request) if the objet is not valid,
     * or with status 500 (Internal Server Error) if the objet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/objets")
    @Timed
    public ResponseEntity<Objet> updateObjet(@RequestBody Objet objet) throws URISyntaxException {
        log.debug("REST request to update Objet : {}", objet);
        if (objet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Objet result = objetService.save(objet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, objet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /objets : get all the objets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of objets in body
     */
    @GetMapping("/objets")
    @Timed
    public ResponseEntity<List<Objet>> getAllObjets(Pageable pageable) {
        log.debug("REST request to get a page of Objets");
        Page<Objet> page = objetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/objets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /objets/:id : get the "id" objet.
     *
     * @param id the id of the objet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the objet, or with status 404 (Not Found)
     */
    @GetMapping("/objets/{id}")
    @Timed
    public ResponseEntity<Objet> getObjet(@PathVariable String id) {
        log.debug("REST request to get Objet : {}", id);
        Optional<Objet> objet = objetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(objet);
    }

    /**
     * DELETE  /objets/:id : delete the "id" objet.
     *
     * @param id the id of the objet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/objets/{id}")
    @Timed
    public ResponseEntity<Void> deleteObjet(@PathVariable String id) {
        log.debug("REST request to delete Objet : {}", id);
        objetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/objets?query=:query : search for the objet corresponding
     * to the query.
     *
     * @param query the query of the objet search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/objets")
    @Timed
    public ResponseEntity<List<Objet>> searchObjets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Objets for query {}", query);
        Page<Objet> page = objetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/objets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
