package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Suivi;
import com.niveka.domain.User;
import com.niveka.repository.UserRepository;
import com.niveka.service.ProspectService;
import com.niveka.service.SuiviService;
import com.niveka.service.dto.ProspectDTO;
import com.niveka.service.dto.SuiviDTO;
import com.niveka.web.rest.errors.BadRequestAlertException;
import com.niveka.web.rest.util.HeaderUtil;
import com.niveka.web.rest.util.PaginationUtil;
import com.niveka.web.rest.util.Utils;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Prospect.
 */
@RestController
@RequestMapping("/api")
public class ProspectResource {

    private final Logger log = LoggerFactory.getLogger(ProspectResource.class);

    private static final String ENTITY_NAME = "prospect";

    private final ProspectService prospectService;

    @Autowired
    private SuiviService suiviService;
    @Autowired
    private UserRepository userRepository;

    public ProspectResource(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    /**
     * POST  /prospects : Create a new prospect.
     *
     * @param prospectDTO the prospectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prospectDTO, or with status 400 (Bad Request) if the prospect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prospects")
    @Timed
    public ResponseEntity<ProspectDTO> createProspect(@RequestBody ProspectDTO prospectDTO) throws URISyntaxException {
        log.debug("REST request to save Prospect : {}", prospectDTO);
        if (prospectDTO.getId() != null) {
            throw new BadRequestAlertException("A new prospect cannot already have an ID", ENTITY_NAME, "exists");
        }

        if(prospectDTO.getUserId()==null){
            return ResponseEntity.badRequest()
                .header("Failure","Verifiez votre connection")
                .body(null);
        }

        Suivi suivi = new Suivi();

        //le
        suivi.setCreatedAt(Utils.currentJodaDateStr());
        suivi.setDateRdv(prospectDTO.getDateRdv());
        HashSet<User> users = new HashSet<>();
        users.add(userRepository.findById((prospectDTO.getUserId())).orElse(null));
        suivi.setUsers(users);
        suivi.setUpdatedAt(Utils.currentJodaDateStr());
        prospectDTO.setCreatedAt(Utils.currentJodaDateStr());
        suivi.setUserId(prospectDTO.getUserId());
        SuiviDTO suiviDTO = suiviService.save(suivi);
        //prospectDTO.setSuivi(suivi);
        prospectDTO.setSuiviId(suiviDTO.getId());
        ProspectDTO result = prospectService.save(prospectDTO);
        return ResponseEntity.created(new URI("/api/prospects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prospects : Updates an existing prospect.
     *
     * @param prospectDTO the prospectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prospectDTO,
     * or with status 400 (Bad Request) if the prospectDTO is not valid,
     * or with status 500 (Internal Server Error) if the prospectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prospects")
    @Timed
    public ResponseEntity<ProspectDTO> updateProspect(@RequestBody ProspectDTO prospectDTO) throws URISyntaxException {
        log.debug("REST request to update Prospect : {}", prospectDTO);
        if (prospectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProspectDTO result = prospectService.save(prospectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prospectDTO.getId().toString()))
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
    public ResponseEntity<List<ProspectDTO>> getAllProspects(Pageable pageable) {
        log.debug("REST request to get a page of Prospects");
        Page<ProspectDTO> page = prospectService.findAll(pageable);
        List<ProspectDTO> pages = new ArrayList<>();
        page.map(prospectDTO -> pages.add(prospectDTO.setCreatedAt(Utils.getDateToJoda(prospectDTO.getCreatedAt()))));
        //log.error("PROSPECT1", pages);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prospects");
        return ResponseEntity.ok().headers(headers).body(pages);
    }

    /**
     * GET  /prospects/:userId : get all the prospects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prospects in body
     */
    @GetMapping("/prospects/by_user/{userId}")
    @Timed
    public ResponseEntity<List<ProspectDTO>> getAllProspectsByUser(@PathVariable String userId, Pageable pageable) {
        log.debug("REST request to get a page of Prospects");
        Page<ProspectDTO> page = prospectService.findAllByUser(userId,pageable);
        List<ProspectDTO> pages = new ArrayList<>();
        page.map(prospectDTO -> {
            prospectDTO.setCreatedAt(Utils.getDateToJoda(prospectDTO.getCreatedAt()));
            if (prospectDTO.getSuiviId()!=null){
                Suivi suivi = new Suivi();
                SuiviDTO suiviDTO = suiviService.findOne(prospectDTO.getSuiviId()).get();
                BeanUtils.copyProperties(suiviDTO, suivi);
                prospectDTO.setSuivi(suivi);
            }
            pages.add(prospectDTO);
            return prospectDTO;
        });
        //log.error("PROSPECT1", pages);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prospects");
        return ResponseEntity.ok().headers(headers).body(pages);
    }

    /**
     * GET  /prospects/:id : get the "id" prospect.
     *
     * @param id the id of the prospectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prospectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prospects/{id}")
    @Timed
    public ResponseEntity<ProspectDTO> getProspect(@PathVariable String id) {
        log.debug("REST request to get Prospect : {}", id);
        Optional<ProspectDTO> prospectDTO = prospectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prospectDTO);
    }

    /**
     * DELETE  /prospects/:id : delete the "id" prospect.
     *
     * @param id the id of the prospectDTO to delete
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
    public ResponseEntity<List<ProspectDTO>> searchProspects(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Prospects for query {}", query);
        Page<ProspectDTO> page = prospectService.search(query, pageable);
        List<ProspectDTO> pages = new ArrayList<>();
        page.map(prospectDTO -> pages.add(prospectDTO.setCreatedAt(Utils.getDateToJoda(prospectDTO.getCreatedAt()))));
        log.error("PROSPECT_2", pages);
        //HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/prospects");
        return ResponseEntity.ok().body(page.getContent());//new ResponseEntity<>(pages, headers, HttpStatus.OK);
    }

}
