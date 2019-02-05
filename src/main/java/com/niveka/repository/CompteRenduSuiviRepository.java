package com.niveka.repository;

import com.niveka.domain.CompteRenduSuivi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


/**
 * Spring Data MongoDB repository for the CompteRenduSuivi entity.
 */
public interface CompteRenduSuiviRepository extends MongoRepository<CompteRenduSuivi, String> {

    @Query("{prospectId: ?0}")
    public List<CompteRenduSuivi> findByProspectExists(String prospectId);
}
