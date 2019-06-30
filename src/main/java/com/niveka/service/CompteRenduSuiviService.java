package com.niveka.service;

import com.niveka.domain.CompteRenduSuivi;
import com.niveka.repository.CompteRenduSuiviRepository;
import com.niveka.repository.ProspectRepository;
import com.niveka.repository.search.CompteRenduSuiviSearchRepository;
import com.niveka.service.dto.CompteRenduSuiviDTO;
import com.niveka.service.mapper.CompteRenduSuiviMapper;
import com.niveka.web.rest.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CompteRenduSuivi.
 */
@Service
public class CompteRenduSuiviService {

    private final Logger log = LoggerFactory.getLogger(CompteRenduSuiviService.class);

    private final CompteRenduSuiviRepository compteRenduSuiviRepository;

    private final CompteRenduSuiviMapper compteRenduSuiviMapper;
    @Autowired
    private ProspectRepository prospectRepository;

    private final CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository;

    public CompteRenduSuiviService(CompteRenduSuiviRepository compteRenduSuiviRepository, CompteRenduSuiviMapper compteRenduSuiviMapper, CompteRenduSuiviSearchRepository compteRenduSuiviSearchRepository) {
        this.compteRenduSuiviRepository = compteRenduSuiviRepository;
        this.compteRenduSuiviMapper = compteRenduSuiviMapper;

        this.compteRenduSuiviSearchRepository = compteRenduSuiviSearchRepository;
    }

    /**
     * Save a compteRenduSuivi.
     *
     * @param compteRenduSuiviDTO the entity to save
     * @return the persisted entity
     */
    public CompteRenduSuiviDTO save(CompteRenduSuiviDTO compteRenduSuiviDTO) {
        log.debug("Request to save CompteRenduSuivi : {}", compteRenduSuiviDTO);
        String d = compteRenduSuiviDTO.getDateProchaineRdv().trim()+":00";
        long datePRdvLong = Utils.getDateStringYYYYMMDDHHIISS(d);
        compteRenduSuiviDTO.setDateProchaineRdvLong(datePRdvLong);
        CompteRenduSuivi compteRenduSuivi = compteRenduSuiviMapper.toEntity(compteRenduSuiviDTO);
        log.debug("-----------------: {}", compteRenduSuivi);
        //compteRenduSuivi.setProspect(prospectRepository.findById(compteRenduSuiviDTO.getProspectId()).get());
        compteRenduSuivi = compteRenduSuiviRepository.save(compteRenduSuivi);
        CompteRenduSuiviDTO result = compteRenduSuiviMapper.toDto(compteRenduSuivi);
        compteRenduSuiviSearchRepository.save(compteRenduSuivi);
        return result;
    }

    /**
     * Get all the compteRenduSuivis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CompteRenduSuiviDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompteRenduSuivis");
        return compteRenduSuiviRepository.findAll(pageable)
            .map(compteRenduSuiviMapper::toDto);
    }

    /**
     * Get all the compteRenduSuivis where not honored.
     *
     * @param honore the pagination information
     * @return the list of entities
     */
    public List<CompteRenduSuivi> findAll(boolean honore,boolean isFirstAlarm) {
        log.debug("Request to get all CompteRenduSuivis");
        return compteRenduSuiviRepository.findByRdvHonoreAndRdvHonoreExistsAndFirstAlarmAndFirstAlarmExists(honore,isFirstAlarm);
    }


    /**
     * Get one compteRenduSuivi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CompteRenduSuiviDTO> findOne(String id) {
        log.debug("Request to get CompteRenduSuivi : {}", id);
        return compteRenduSuiviRepository.findById(id)
            .map(compteRenduSuiviMapper::toDto);
    }

    /**
     * Delete the compteRenduSuivi by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete CompteRenduSuivi : {}", id);
        compteRenduSuiviRepository.deleteById(id);
        compteRenduSuiviSearchRepository.deleteById(id);
    }

    /**
     * Search for the compteRenduSuivi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<CompteRenduSuiviDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompteRenduSuivis for query {}", query);
        return compteRenduSuiviSearchRepository.search(queryStringQuery(query), pageable)
            .map(compteRenduSuiviMapper::toDto);
    }

    /**
     * Get all the compteRenduSuivis by prospectID.
     *
     * @param prospectId the pagination information
     * @return the list of entities
     */
    public List<CompteRenduSuivi> findByProspect(String prospectId) {
        log.debug("Request to get all CompteRenduSuivis");
        return compteRenduSuiviRepository.findByProspectExists(prospectId);
            //.map(compteRenduSuiviMapper::toDto);
    }

    /**
     * Get all the compteRenduSuivis by prospectID.
     *
     * @param id the id of the user who have savet save
     * @param dateString the date of information
     * @return the list of entities
     */
    public List<CompteRenduSuiviDTO> findByDate(String id,String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        Date date = sdf.parse(dateString);
        Date startDate =new Date(date.getYear(),date.getMonth(),date.getDay(),0,0,1);
        Date endDate =new Date(date.getYear(),date.getMonth(),date.getDay(),23,59,59);
        log.debug("DATE : {} - {}",startDate.toString(),endDate.toString());
        List<CompteRenduSuivi> list = compteRenduSuiviRepository.findByUserIdAndDateProchaineRdvLongBetween(
            id,
            endDate.getTime(),
            startDate.getTime()
        );
        List<CompteRenduSuiviDTO> dtoList = new ArrayList<>();
        list.forEach(compteRenduSuivi -> dtoList.add(compteRenduSuiviMapper.toDto(compteRenduSuivi)));
        return dtoList;
    }

    /**
     * Get all the compteRenduSuivis by month and year.
     *
     * @param userId the ID of User who have save
     * @param month the month of cal
     * @param year the year of cal
     * @return the list of entities
     */
    public List<CompteRenduSuiviDTO> getCalendar(String userId,int year,int month) {
        //log.debug("Request to get all CompteRenduSuivis");
        String m = (month<10?("0"+month):String.valueOf(month));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        long startDate = calendar.getTimeInMillis();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long endDate = calendar.getTimeInMillis();


        /*calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
        String _endDate = (lastDayOfMonth<10?("0"+lastDayOfMonth):lastDayOfMonth)+"/"+m+"/"+year+" 23:59:59";
        long endDate = Utils.getDateStringYYYYMMDDHHIISS(_endDate);*/


        //log.debug("LES DATE SONT: {} ---- {} ",startDate, endDate);
        List<CompteRenduSuiviDTO> dtos = new ArrayList<>();
        compteRenduSuiviRepository
            //.findByUserIdAndDateProchaineRdvLongBetween(userId,1559612100000L,1560572100000L)
            .findByUserIdAndDateProchaineRdvLongBetween(userId,startDate,endDate)
            .forEach(compteRenduSuivi -> dtos.add(compteRenduSuiviMapper.toDto(compteRenduSuivi)));
        //log.debug("RESULTAT: {}",dtos);
        return dtos;
    }
}
