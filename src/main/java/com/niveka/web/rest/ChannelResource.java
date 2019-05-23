package com.niveka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niveka.domain.Entreprise;
import com.niveka.domain.Message;
import com.niveka.domain.User;
import com.niveka.domain.ZChannel;
import com.niveka.repository.MessageRepository;
import com.niveka.repository.UserRepository;
import com.niveka.service.EntrepriseService;
import com.niveka.service.UserService;
import com.niveka.service.ZChannelService;
import com.niveka.service.dto.ZChannelDTO;
import com.niveka.web.rest.errors.BadRequestAlertException;
import com.niveka.web.rest.util.HeaderUtil;
import com.niveka.web.rest.util.PaginationUtil;
import com.niveka.web.rest.util.Utils;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ZChannel.
 */
@RestController
@RequestMapping("/api")
public class ChannelResource {

    private final Logger log = LoggerFactory.getLogger(ChannelResource.class);

    private static final String ENTITY_NAME = "channel";

    private final ZChannelService ZChannelService;

    @Autowired
    private EntrepriseService entrepriseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public ChannelResource(ZChannelService ZChannelService) {
        this.ZChannelService = ZChannelService;
    }

    /**
     * POST  /channels : Create a new channel.
     *
     * @param channelDTO the channelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new channelDTO, or with status 400 (Bad Request) if the channel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/channels")
    @Timed
    public ResponseEntity<ZChannelDTO> createChannel(@RequestBody ZChannelDTO channelDTO) throws URISyntaxException {
        //log.debug("----------@@@@@@@@@-----------------@@@@@@@@@@@@@@------------------- : {}", channelDTO);
        //log.debug("----------xxxxxxxxx-----------------xxxxxxxxxxxxxx------------------ : {}", channelDTO.getEntreprise());
        if (channelDTO.getId() != null) {
            throw new BadRequestAlertException("A new channel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        channelDTO.setCreatedAt(Utils.currentJodaDateStr());
        channelDTO.setUpdatedAt(Utils.currentJodaDateStr());
        ZChannelDTO result = ZChannelService.save(channelDTO);
        /*return ResponseEntity.created(new URI("/api/channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);*/
        return null;
    }

    /**
     * PUT  /channels : Updates an existing channel.
     *
     * @param channelDTO the channelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated channelDTO,
     * or with status 400 (Bad Request) if the channelDTO is not valid,
     * or with status 500 (Internal Server Error) if the channelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/channels")
    @Timed
    public ResponseEntity<ZChannelDTO> updateChannel(@RequestBody ZChannelDTO channelDTO) throws URISyntaxException {
        log.debug("REST request to update ZChannel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        channelDTO.setUpdatedAt(Utils.currentJodaDateStr());
        ZChannelDTO result = ZChannelService.save(channelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, channelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /channels : get all the channels.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of channels in body
     */
    @GetMapping("/channels")
    @Timed
    public ResponseEntity<List<ZChannelDTO>> getAllChannels(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Channels");
        Page<ZChannelDTO> page;
        if (eagerload) {
            page = ZChannelService.findAllWithEagerRelationships(pageable);
        } else {
            page = ZChannelService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/channels?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/channels/by_entreprise/{userID}")
    @Timed
    public ResponseEntity<List<ZChannel>> channelsPerEntreprise(@PathVariable String userID){

        User user = userService.findOne(userID);
        Entreprise userEntreprise = user.getEntreprise();
        if (userEntreprise==null){
            throw new BadRequestAlertException("Aucun forum correspondant a votre entreprise trouvé", ENTITY_NAME, "idnull");
        }

        List<ZChannel> zChannels = ZChannelService.findAllChannelForEntreprise(userEntreprise.getId());
        List<ChannelWithLastMessage> channelWithLastMessages = new ArrayList<>();

        for (ZChannel zc: zChannels
             ) {
            Message lastest = messageRepository.findTopByChannel_Id(zc.getId());
            ChannelWithLastMessage clm = new ChannelWithLastMessage(zc,lastest);
            channelWithLastMessages.add(clm);
        }

        //log.debug("USER_ENTREPRISE_FORUM: {}",channelWithLastMessages);
        return ResponseEntity.ok().body(zChannels);
    }

    /**
     * GET  /channels/:id : get the "id" channel.
     *
     * @param id the id of the channelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the channelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/channels/{id}")
    @Timed
    public ResponseEntity<ZChannelDTO> getChannel(@PathVariable String id) {
        log.debug("REST request to get ZChannel : {}", id);
        Optional<ZChannelDTO> channelDTO = ZChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(channelDTO);
    }

    /**
     * DELETE  /channels/:id : delete the "id" channel.
     *
     * @param id the id of the channelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/channels/{id}")
    @Timed
    public ResponseEntity<Void> deleteChannel(@PathVariable String id) {
        log.debug("REST request to delete ZChannel : {}", id);
        ZChannelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/channels?query=:query : search for the channel corresponding
     * to the query.
     *
     * @param query the query of the channel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/channels")
    @Timed
    public ResponseEntity<List<ZChannelDTO>> searchChannels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Channels for query {}", query);
        Page<ZChannelDTO> page = ZChannelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/channels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    public class ChannelWithLastMessage{
        public ZChannel zChannel;
        private Message lastMessage;

        public ChannelWithLastMessage(ZChannel zChannel, Message lastMessage) {
            this.zChannel = zChannel;
            this.lastMessage = lastMessage;
        }
    }

}
