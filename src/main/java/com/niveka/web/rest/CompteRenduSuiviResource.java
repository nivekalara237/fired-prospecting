package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.CompteRenduSuivi;
import com.niveka.repository.FichierRepository;
import com.niveka.service.CompteRenduSuiviService;
import com.niveka.service.FichierService;
import com.niveka.service.dto.CompteRenduSuiviDTO;
import com.niveka.service.dto.FichierDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * REST controller for managing CompteRenduSuivi.
 */
@RestController
@RequestMapping("/api")
public class CompteRenduSuiviResource {

    private final Logger log = LoggerFactory.getLogger(CompteRenduSuiviResource.class);

    private static final String ENTITY_NAME = "compteRenduSuivi";

    @Autowired
    private FichierRepository fichierRepository;

    private final CompteRenduSuiviService compteRenduSuiviService;

    @Autowired
    private FichierService fichierService;

    public CompteRenduSuiviResource(CompteRenduSuiviService compteRenduSuiviService) {
        this.compteRenduSuiviService = compteRenduSuiviService;
    }

    /**
     * POST  /compte-rendu-suivis : Create a new compteRenduSuivi.
     *
     * @param compteRenduSuiviDTO the compteRenduSuiviDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteRenduSuiviDTO, or with status 400 (Bad Request) if the compteRenduSuivi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-rendu-suivis")
    @Timed
    public ResponseEntity<CompteRenduSuiviDTO> createCompteRenduSuivi(@RequestBody CompteRenduSuiviDTO compteRenduSuiviDTO) throws URISyntaxException {
        log.debug("REST request to save CompteRenduSuivi : {}", compteRenduSuiviDTO);
        if (compteRenduSuiviDTO.getId() != null) {
            throw new BadRequestAlertException("A new compteRenduSuivi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        compteRenduSuiviDTO.setCreatedAt(Utils.currentJodaDateStr());
        compteRenduSuiviDTO.setUpdatedAt(Utils.currentJodaDateStr());

        CompteRenduSuiviDTO result = compteRenduSuiviService.save(compteRenduSuiviDTO);
        /*return ResponseEntity.created(new URI("/api/prospects/" + result.getProspectId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getProspectId()))
            .body(result);*/
        Optional<CompteRenduSuiviDTO> optional = Optional.ofNullable(result);
        return ResponseUtil.wrapOrNotFound(optional);
    }

    /**
     * PUT  /compte-rendu-suivis : Updates an existing compteRenduSuivi.
     *
     * @param compteRenduSuiviDTO the compteRenduSuiviDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteRenduSuiviDTO,
     * or with status 400 (Bad Request) if the compteRenduSuiviDTO is not valid,
     * or with status 500 (Internal Server Error) if the compteRenduSuiviDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-rendu-suivis")
    @Timed
    public ResponseEntity<CompteRenduSuiviDTO> updateCompteRenduSuivi(@RequestBody CompteRenduSuiviDTO compteRenduSuiviDTO) throws URISyntaxException {
        log.debug("REST request to update CompteRenduSuivi : {}", compteRenduSuiviDTO);
        if (compteRenduSuiviDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompteRenduSuiviDTO result = compteRenduSuiviService.save(compteRenduSuiviDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteRenduSuiviDTO.getId()))
            .body(result);
    }


    /**
     * PUT  /compte-rendu-suivis/validate/:id : marking specific compteRenduSuivi as validate.
     *
     * @param id the compteRenduSuiviDTO ID to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteRenduSuiviDTO,
     * or with status 400 (Bad Request) if the compteRenduSuiviDTO is not valid,
     * or with status 500 (Internal Server Error) if the compteRenduSuiviDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-rendu-suivis/validated/{id}")
    @Timed
    public ResponseEntity<CompteRenduSuiviDTO> validate(@PathVariable String id) throws URISyntaxException {
        log.debug("REST request to update CompteRenduSuivi : {}", id);
        Optional<CompteRenduSuiviDTO> compteRenduSuiviDTO = compteRenduSuiviService.findOne(id);
        if (!compteRenduSuiviDTO.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompteRenduSuiviDTO crs = compteRenduSuiviDTO.get();
        crs.setRdvHonore(true);
        CompteRenduSuiviDTO result = compteRenduSuiviService.save(crs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteRenduSuiviDTO.get().getId()))
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
    public ResponseEntity<List<CompteRenduSuiviDTO>> getAllCompteRenduSuivis(Pageable pageable) {
        log.debug("REST request to get a page of CompteRenduSuivis");
        Page<CompteRenduSuiviDTO> page = compteRenduSuiviService.findAll(pageable);
        List<CompteRenduSuiviDTO> pages = new ArrayList<>();
        page.map(s -> pages.add(s.setCreated(Utils.getDateToJoda(s.getCreatedAt()))));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-rendu-suivis");
        return ResponseEntity.ok().headers(headers).body(pages);
    }

    @GetMapping("/compte-rendu-suivis/by-prospect/{prospect}")
    @Timed
    public ResponseEntity<List<CompteRenduSuiviDTO>> getAllCompteRenduSuivisByProspect(@PathVariable String prospect) {
        log.debug("REST request to get a page of CompteRenduSuivis");
        List<CompteRenduSuivi> page = compteRenduSuiviService.findByProspect(prospect);
        List<CompteRenduSuiviDTO> dtoList = new ArrayList<>();
        log.debug("REST request PAGE{}",page);
        List<CompteRenduSuiviDTO> pagesDTO = new ArrayList<>();
        Consumer<CompteRenduSuivi> consumer = new Consumer<CompteRenduSuivi>() {
            @Override
            public void accept(CompteRenduSuivi compteRenduSuivi) {
                CompteRenduSuiviDTO dto = new CompteRenduSuiviDTO();
                compteRenduSuivi.setCreatedAt(String.valueOf(Utils.getJodaToLong(compteRenduSuivi.getCreatedAt())));
                BeanUtils.copyProperties(compteRenduSuivi,dto);
                dto.setFichiers(fichierRepository.findByModelAndModelId(Utils.TYPES_MODELS_FICHIER.COMPTE_RENDU_SUIVI,dto.getId()));
                pagesDTO.add(dto);
            }
        };
        page.forEach(consumer);
        //page.map(s -> pages.add(s.setCreated(Utils.getDateToJoda(s.getCreatedAt()))));
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-rendu-suivis");
        return ResponseEntity.ok().body(pagesDTO);
    }

    /**
     * GET  /compte-rendu-suivis/:id : get the "id" compteRenduSuivi.
     *
     * @param id the id of the compteRenduSuiviDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteRenduSuiviDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compte-rendu-suivis/{id}")
    @Timed
    public ResponseEntity<CompteRenduSuiviDTO> getCompteRenduSuivi(@PathVariable String id) {
        log.debug("REST request to get CompteRenduSuivi : {}", id);
        Optional<CompteRenduSuiviDTO> compteRenduSuiviDTO = compteRenduSuiviService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteRenduSuiviDTO);
    }

    /**
     * GET  /compte-rendu-suivis/:id : get the "id" compteRenduSuivi.
     *
     * @param dateString the date
     * @param id the id of the CommId(UserID) who have save
     * @return the ResponseEntity with status 200 (OK) and with body the compteRenduSuiviDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compte-rendu-suivis/by-date/{id}/{dateString}")
    @Timed
    public ResponseEntity<List<CompteRenduSuiviDTO>> getCompteRenduByDate(@PathVariable String id,@PathVariable String dateString) {
        log.debug("REST request to get CompteRenduSuivi by date : {} - {}", id, dateString);
        try {
            List<CompteRenduSuiviDTO> compteRenduSuivis = compteRenduSuiviService.findByDate(id,dateString);
            return ResponseEntity.ok().body(compteRenduSuivis);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET  /compte-rendu-suivis/:id/fichiers : get the fichier for "id" compteRenduSuivi.
     *
     * @param id the id of the compteRenduSuiviDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the List<FichierDTO>, or with status 404 (Not Found)
     */
    @GetMapping("/compte-rendu-suivis/{id}/fichiers")
    @Timed
    public ResponseEntity<List<FichierDTO>> getFichiersForCompteRenduSuivi(@PathVariable String id) {
        log.debug("REST request to get CompteRenduSuivi : {}", id);
        //Optional<CompteRenduSuiviDTO> compteRenduSuiviDTO = compteRenduSuiviService.findOne(id);
        List<FichierDTO> fichierDTOS = fichierService.findAll(id,Utils.TYPES_MODELS_FICHIER.COMPTE_RENDU_SUIVI);
        return ResponseEntity.ok().body(fichierDTOS);
    }

    /**
     * GET  /compte-rendu-suivis/:id/:year/:month : get the fichier for "id" compteRenduSuivi.
     *
     * @param id the id of the user who have savet save
     * @return the ResponseEntity with status 200 (OK) and with body the List<FichierDTO>, or with status 404 (Not Found)
     */
    @GetMapping("/compte-rendu-suivis/by-month/{id}/{year}/{month}")
    @Timed
    public ResponseEntity<List<CompteRenduSuiviDTO>> getCalendar(@PathVariable String id,@PathVariable int year,@PathVariable int month) {
        log.debug("REST request to get Calendar : {}-{}-{}", id,year,month);
        //Optional<CompteRenduSuiviDTO> compteRenduSuiviDTO = compteRenduSuiviService.findOne(id);
        List<CompteRenduSuiviDTO> compteRenduSuivis = compteRenduSuiviService.getCalendar(id,year,month);
        return ResponseEntity.ok().body(compteRenduSuivis);
    }

    /**
     * DELETE  /compte-rendu-suivis/:id : delete the "id" compteRenduSuivi.
     *
     * @param id the id of the compteRenduSuiviDTO to delete
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
    public ResponseEntity<List<CompteRenduSuiviDTO>> searchCompteRenduSuivis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompteRenduSuivis for query {}", query);
        Page<CompteRenduSuiviDTO> page = compteRenduSuiviService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/compte-rendu-suivis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
