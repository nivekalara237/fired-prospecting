package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.payload.UploadFileResponse;
import com.niveka.repository.UserRepository;
import com.niveka.service.FichierService;
import com.niveka.service.FileStorageService;
import com.niveka.service.dto.FichierDTO;
import com.niveka.web.rest.errors.BadRequestAlertException;
import com.niveka.web.rest.util.HeaderUtil;
import com.niveka.web.rest.util.PaginationUtil;
import com.niveka.web.rest.util.Utils;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Fichier.
 */
@RestController
@RequestMapping("/api")
public class FichierResource {

    private final Logger log = LoggerFactory.getLogger(FichierResource.class);

    private static final String ENTITY_NAME = "fichier";

    private final FichierService fichierService;

    @Autowired
    private Environment environment;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepository userRepository;

    public FichierResource(FichierService fichierService) {
        this.fichierService = fichierService;
    }

    /**
     * POST  /fichiers : Create a new fichier.
     *
     * @param fichierDTO the fichierDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fichierDTO, or with status 400 (Bad Request) if the fichier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichiers")
    public ResponseEntity<FichierDTO> createFichier(@RequestBody FichierDTO fichierDTO) throws URISyntaxException {
        log.debug("REST request to save Fichier : {}", fichierDTO);
        if (fichierDTO.getId() != null) {
            throw new BadRequestAlertException("A new fichier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichierDTO result = fichierService.save(fichierDTO);
        return ResponseEntity.created(new URI("/api/fichiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PostMapping(value = "/files/{model}/{modelId}")
    @Timed
    public List<UploadFileResponse> saveFiles(@PathVariable String model,@PathVariable String modelId, @RequestParam("files") MultipartFile[] multipartFile) throws URISyntaxException {
        log.debug("REST request to save RapportID : {} ", multipartFile.length);
        if (modelId == null) {
            throw new BadRequestAlertException("A new fichier cannot already have an ID", ENTITY_NAME, "idnotexists");
        }
        FichierDTO fichierDTO = new FichierDTO();
        fichierDTO.setModel(model);
        fichierDTO.setModelId(modelId);
        return Arrays.stream(multipartFile)
            .map(f -> uploadFile(f, fichierDTO))
            .collect(Collectors.toList());
    }

    private UploadFileResponse uploadFile(MultipartFile file, FichierDTO fichier) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .port(environment.getProperty("server.port"))
            .path("/files/uploads/")
            .path(fileName)
            .toUriString();
        UploadFileResponse uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri,
            file.getContentType(), file.getSize());
        fichier.setPath(uploadFileResponse.getFileDownloadUri());
        fichier.setType(uploadFileResponse.getFileType());
        fichier.setSize(uploadFileResponse.getSize());
        fichier.setCreatedAt(Utils.currentJodaDateStr());
        fichier.setUpdatedAt(Utils.currentJodaDateStr());
        fichierService.save(fichier);

        log.debug("Fichier = {}",fichier);
        return uploadFileResponse;
    }

    /**
     * PUT  /fichiers : Updates an existing fichier.
     *
     * @param fichierDTO the fichierDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fichierDTO,
     * or with status 400 (Bad Request) if the fichierDTO is not valid,
     * or with status 500 (Internal Server Error) if the fichierDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fichiers")
    public ResponseEntity<FichierDTO> updateFichier(@RequestBody FichierDTO fichierDTO) throws URISyntaxException {
        log.debug("REST request to update Fichier : {}", fichierDTO);
        if (fichierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichierDTO result = fichierService.save(fichierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fichierDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fichiers : get all the fichiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fichiers in body
     */
    @GetMapping("/fichiers")
    public ResponseEntity<List<FichierDTO>> getAllFichiers(Pageable pageable) {
        log.debug("REST request to get a page of Fichiers");
        Page<FichierDTO> page = fichierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fichiers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /fichiers/:id : get the "id" fichier.
     *
     * @param id the id of the fichierDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichierDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fichiers/{id}")
    public ResponseEntity<FichierDTO> getFichier(@PathVariable String id) {
        log.debug("REST request to get Fichier : {}", id);
        Optional<FichierDTO> fichierDTO = fichierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichierDTO);
    }

    /**
     * DELETE  /fichiers/:id : delete the "id" fichier.
     *
     * @param id the id of the fichierDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fichiers/{id}")
    public ResponseEntity<Void> deleteFichier(@PathVariable String id) {
        log.debug("REST request to delete Fichier : {}", id);
        fichierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/fichiers?query=:query : search for the fichier corresponding
     * to the query.
     *
     * @param query the query of the fichier search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fichiers")
    public ResponseEntity<List<FichierDTO>> searchFichiers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fichiers for query {}", query);
        Page<FichierDTO> page = fichierService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fichiers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
