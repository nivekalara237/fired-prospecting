package com.niveka.repository;

import com.niveka.domain.Suivi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Suivi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuiviRepository extends MongoRepository<Suivi, String> {
    //Optional<Suivi> findBy
}
