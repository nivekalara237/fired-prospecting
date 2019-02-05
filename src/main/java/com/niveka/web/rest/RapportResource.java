package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.service.RapportService;
import com.niveka.service.dto.RapportDTO;
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
 * REST controller for managing Rapport.
 */
@RestController
@RequestMapping("/api")
public class RapportResource {

    private final Logger log = LoggerFactory.getLogger(RapportResource.class);

    private static final String ENTITY_NAME = "rapport";

    private final RapportService rapportService;

    public RapportResource(RapportService rapportService) {
        this.rapportService = rapportService;
    }

    /**
     * POST  /rapports : Create a new rapport.
     *
     * @param rapportDTO the rapportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rapportDTO, or with status 400 (Bad Request) if the rapport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rapports")
    @Timed
    public ResponseEntity<RapportDTO> createRapport(@RequestBody RapportDTO rapportDTO) throws URISyntaxException {
        log.debug("REST request to save Rapport : {}", rapportDTO);
        if (rapportDTO.getId() != null) {
            throw new BadRequestAlertException("A new rapport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RapportDTO result = rapportService.save(rapportDTO);
        return ResponseEntity.created(new URI("/api/rapports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rapports : Updates an existing rapport.
     *
     * @param rapportDTO the rapportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rapportDTO,
     * or with status 400 (Bad Request) if the rapportDTO is not valid,
     * or with status 500 (Internal Server Error) if the rapportDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rapports")
    @Timed
    public ResponseEntity<RapportDTO> updateRapport(@RequestBody RapportDTO rapportDTO) throws URISyntaxException {
        log.debug("REST request to update Rapport : {}", rapportDTO);
        if (rapportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RapportDTO result = rapportService.save(rapportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rapportDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rapports : get all the rapports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rapports in body
     */
    @GetMapping("/rapports")
    @Timed
    public ResponseEntity<List<RapportDTO>> getAllRapports(Pageable pageable) {
        log.debug("REST request to get a page of Rapports");
        Page<RapportDTO> page = rapportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rapports");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rapports/:id : get the "id" rapport.
     *
     * @param id the id of the rapportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rapportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rapports/{id}")
    @Timed
    public ResponseEntity<RapportDTO> getRapport(@PathVariable String id) {
        log.debug("REST request to get Rapport : {}", id);
        Optional<RapportDTO> rapportDTO = rapportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rapportDTO);
    }

    /**
     * DELETE  /rapports/:id : delete the "id" rapport.
     *
     * @param id the id of the rapportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rapports/{id}")
    @Timed
    public ResponseEntity<Void> deleteRapport(@PathVariable String id) {
        log.debug("REST request to delete Rapport : {}", id);
        rapportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/rapports?query=:query : search for the rapport corresponding
     * to the query.
     *
     * @param query the query of the rapport search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/rapports")
    @Timed
    public ResponseEntity<List<RapportDTO>> searchRapports(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Rapports for query {}", query);
        Page<RapportDTO> page = rapportService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rapports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
