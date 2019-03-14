package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Objet;
import com.niveka.domain.User;
import com.niveka.payload.UploadFileResponse;
import com.niveka.repository.UserRepository;
import com.niveka.security.SecurityUtils;
import com.niveka.service.FileStorageService;
import com.niveka.service.ObjetService;
import com.niveka.service.RapportService;
import com.niveka.service.dto.ObjetDTO;
import com.niveka.service.dto.RapportDTO;
import com.niveka.web.rest.errors.BadRequestAlertException;
import com.niveka.web.rest.util.HeaderUtil;
import com.niveka.web.rest.util.PaginationUtil;
import com.niveka.web.rest.util.Utils;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * REST controller for managing Rapport.
 */
@RestController
@RequestMapping("/api")
public class RapportResource {

    private final Logger log = LoggerFactory.getLogger(RapportResource.class);

    private static final String ENTITY_NAME = "rapport";

    @Autowired
    private Environment environment;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepository userRepository;

    private final RapportService rapportService;
    private final ObjetService objetService;

    public RapportResource(RapportService rapportService, ObjetService objetService) {
        this.rapportService = rapportService;
        this.objetService = objetService;
    }

    /**
     * POST  /rapports : Create a new rapport.
     *
     * @param rapportDTO the rapportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rapportDTO, or with status 400 (Bad Request) if the rapport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/rapports")
    @Timed
    public ResponseEntity<RapportDTO> createRapport(@RequestBody RapportDTO rapportDTO) throws URISyntaxException {
        log.debug("REST request to save Rapport : {}", rapportDTO);
        if (rapportDTO.getId() != null) {
            throw new BadRequestAlertException("A new rapport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (rapportDTO.getUserId() == null) {
            throw new BadRequestAlertException("check if you are connected yet.", ENTITY_NAME, "idexists");
        }
        Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
        //log.debug("USER = {}",user.get());
        rapportDTO.setUser(user.get());
        rapportDTO.setCreatedAt(Utils.currentJodaDateStr());
        rapportDTO.setUpdatedAt(Utils.currentJodaDateStr());
        RapportDTO result = rapportService.save(rapportDTO);
        return ResponseEntity.created(new URI("/api/rapports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    @PostMapping(value = "/rapports/{id}/files")
    @Timed
    public List<UploadFileResponse> saveFiles(@PathVariable String id, @RequestParam("files") MultipartFile[] multipartFile) throws URISyntaxException {
        log.debug("REST request to save RapportID : {}", id);
        RapportDTO rapportDTO = rapportService.findOne(id).get();
        if (id == null) {
            throw new BadRequestAlertException("A new rapport cannot already have an ID", ENTITY_NAME, "idnotexists");
        }

        ObjetDTO objetDTO = new ObjetDTO();
//        RapportDTO result = rapportService.save(rapportDTO);
//        return ResponseEntity.created(new URI("/api/rapports/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
//            .body(result);
        //UploadFileResponse k = this.uploadFile(multipartFile);
        //log.debug("KKKKKKKKKKKKKKKK: {}",k);
        //fileStorage.store(multipartFile);
        return Arrays.asList(multipartFile)
            .stream()
            .map(f -> uploadFile(f, id))
            .collect(Collectors.toList());
    }

    private UploadFileResponse uploadFile(MultipartFile file, String repportId) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .port(environment.getProperty("server.port"))
            //.scheme("https")
            .path("/files/uploads/")
            .path(fileName)
            .toUriString();
        UploadFileResponse uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri,
            file.getContentType(), file.getSize());

        Objet objet = new Objet();
        objet.setLien(uploadFileResponse.getFileDownloadUri());
        objet.setNom(uploadFileResponse.getFileName());
        objet.setType(uploadFileResponse.getFileType());
        objet.setTaille(uploadFileResponse.getSize());
        ObjetDTO objetDTO = new ObjetDTO();
        BeanUtils.copyProperties(objet, objetDTO);
        objetDTO.setRapportId(repportId);
        //objet.setRapport();
        objetService.save(objetDTO);
        return uploadFileResponse;
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
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rapportDTO.getId()))
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

    @GetMapping("/rapports/details/{id}")
    @Timed
    public @ResponseBody Map<String,Object> getDetails(@PathVariable String id){
        Map<String, Object> res= new HashMap<>();
        RapportDTO rapportDTO = rapportService.findOne(id).get();
        List<Objet> objets = objetService.findAllByRapport(id);
        List<ObjetDTO> objetDTOS = new ArrayList<>();
        if (objets!=null && objets.size()!=0){
            Consumer<Objet> consumer = new Consumer<Objet>() {
                @Override
                public void accept(Objet obj) {
                    ObjetDTO o = new ObjetDTO();
                    BeanUtils.copyProperties(obj,o);
                    objetDTOS.add(o);
                }
            };
            objets.forEach(consumer);
        }
        res.put("rapport",rapportDTO);
        res.put("files",objetDTOS);
        return res;
    }

    @GetMapping("/rapports/by_user/{userId}")
    @Timed
    public ResponseEntity<List<RapportDTO>> getAllRapportsByUser(@PathVariable String userId,Pageable pageable){
        log.debug("REST request to get Rapport by user : {}", userId);
        Page<RapportDTO> page = rapportService.findAllByUser(pageable,userId);
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

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

}
