package com.niveka.repository;

import com.niveka.domain.CompteRenduSuivi;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CompteRenduSuivi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRenduSuiviRepository extends MongoRepository<CompteRenduSuivi, String> {

}
