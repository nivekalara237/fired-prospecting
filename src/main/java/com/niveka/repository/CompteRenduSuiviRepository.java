package com.niveka.repository;

import com.niveka.domain.CompteRenduSuivi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;


/**
 * Spring Data MongoDB repository for the CompteRenduSuivi entity.
 */
public interface CompteRenduSuiviRepository extends MongoRepository<CompteRenduSuivi, String> {

    @Query("{prospect_id: ?0}")
    public List<CompteRenduSuivi> findByProspectExists(String prospectId);

    @Query("{'dateProchaineRdv':{$gte: ?0, $lte: ?1}}")
    public List<CompteRenduSuivi> findByDateProchaineRdv(Date date0, Date date1);

    @Query("{rdv_honore: ?0,first_alarm: ?1}")
    public List<CompteRenduSuivi> findByRdvHonoreAndRdvHonoreExistsAndFirstAlarmAndFirstAlarmExists(boolean honore,boolean firstAlarm);

    //@Query("{'prospect_id' : ?0, date_prochaine_rdv_long : {$gt : ?1, $lt : ?2}}")
    public List<CompteRenduSuivi> findByUserIdAndDateProchaineRdvLongBetween(String userId,long end,long start);
    //public List<CompteRenduSuivi> findByProspectIdAndDateProchaineRdvLongBetween(String prospectId, long start,long end);
}
