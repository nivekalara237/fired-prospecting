package com.niveka.repository;

import com.niveka.domain.Rapport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Rapport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RapportRepository extends MongoRepository<Rapport, String> {
    Page<Rapport> findAllByUserId(Pageable pageable, String userId);
}
