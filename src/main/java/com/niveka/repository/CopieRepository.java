package com.niveka.repository;

import com.niveka.domain.Copie;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Copie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CopieRepository extends MongoRepository<Copie, String> {

}
